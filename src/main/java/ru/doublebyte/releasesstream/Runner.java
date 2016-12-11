package ru.doublebyte.releasesstream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import ru.doublebyte.releasesstream.db.Release;
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
        List<Release> releases = new ArrayList<>();

        Release release1 = new Release();
        release1.setRepositoryName("n-at/arbirter");
        release1.setRepositoryUrl("https://github.com/n-at/arbirter");
        release1.setRepositoryDescription("Description text text text text text");
        release1.setTitle("v.1.0.0-RELEASE");
        release1.setDate(LocalDateTime.now());
        release1.setAuthor("John Doe");
        release1.setContent("djad kjhsjfgsjfg sf jhsf jsgfj sgfjgsj fsgf gsj");

        releases.add(release1);

        mailMessageSender.sendReleases(releases);
    }
}
