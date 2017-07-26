package ru.doublebyte.releasesstream.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import ru.doublebyte.releasesstream.db.Release;
import ru.doublebyte.releasesstream.db.ReleaseRepository;
import ru.doublebyte.releasesstream.feed.AtomReader;
import ru.doublebyte.releasesstream.feed.FeedEntry;
import ru.doublebyte.releasesstream.github.Repository;
import ru.doublebyte.releasesstream.github.StarredRepositories;
import ru.doublebyte.releasesstream.mail.MailMessageSender;

import java.util.ArrayList;
import java.util.List;

/**
 * Scheduled task executor
 */
public class Runner {

    private static final Logger logger = LoggerFactory.getLogger(Runner.class);

    private final StarredRepositories starredRepositories;
    private final AtomReader atomReader;
    private final MailMessageSender mailMessageSender;
    private final ReleaseRepository releaseRepository;

    public Runner(
            StarredRepositories starredRepositories,
            AtomReader atomReader,
            MailMessageSender mailMessageSender,
            ReleaseRepository releaseRepository
    ) {
        this.starredRepositories = starredRepositories;
        this.atomReader = atomReader;
        this.mailMessageSender = mailMessageSender;
        this.releaseRepository = releaseRepository;
    }

    ///////////////////////////////////////////////////////////////////////////

    @Scheduled(cron = "${releases-stream.refresh-cron}")
    public void run() {
        fetchRepositoriesAndReleases();
    }

    ///////////////////////////////////////////////////////////////////////////

    /**
     * Do all work:
     * - get starred repositories
     * - fetch releases
     * - filter new releases (not exist in db)
     * - send email
     * - save releases in db
     */
    private void fetchRepositoriesAndReleases() {
        logger.info("fetching releases info...");

        List<Release> releases = new ArrayList<>();

        List<Repository> repositories = starredRepositories.getRepositories();

        repositories.forEach(repository -> {
            List<FeedEntry> feedEntries = atomReader.read(repository.getReleasesAtomUrl());

            feedEntries.forEach(feedEntry -> {
                Release release = new Release(repository, feedEntry);

                if (!releaseRepository.exists(release.getId())) {
                    releases.add(release);
                }
            });
        });

        if (releases.size() == 0) {
            logger.info("no new releases");
            return;
        }

        try {
            logger.info("sending {} releases...", releases.size());
            mailMessageSender.sendReleases(releases);
        } catch (Exception e) {
            logger.error("send error", e);
            return;
        }

        try {
            logger.info("saving {} releases...", releases.size());
            releaseRepository.save(releases);
        } catch (Exception e) {
            logger.error("releases save error", e);
        }
    }
}
