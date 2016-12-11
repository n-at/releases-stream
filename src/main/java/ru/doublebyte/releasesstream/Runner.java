package ru.doublebyte.releasesstream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import ru.doublebyte.releasesstream.db.Release;
import ru.doublebyte.releasesstream.feed.AtomReader;
import ru.doublebyte.releasesstream.feed.FeedEntry;
import ru.doublebyte.releasesstream.github.Repository;
import ru.doublebyte.releasesstream.mail.MailMessageSender;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Runner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(Runner.class);

    private final MailMessageSender mailMessageSender;

    public Runner(MailMessageSender mailMessageSender) {
        this.mailMessageSender = mailMessageSender;
    }

    @Override
    public void run(String... strings) throws Exception {
        AtomReader reader = new AtomReader();
        List<FeedEntry> feedEntries = reader.read("https://github.com/docker/docker/releases.atom");
        List<Release> releases = new ArrayList<>();

        Repository repository = new Repository();
        repository.setName("docker/docker");
        repository.setUrl("https://github.com/docker/docker");
        repository.setDescription("Docker - the open-source application container engine");

        feedEntries.forEach(it -> {
            releases.add(new Release(repository, it));
        });

        mailMessageSender.sendReleases(releases);
    }
}
