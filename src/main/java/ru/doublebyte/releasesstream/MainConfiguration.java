package ru.doublebyte.releasesstream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import ru.doublebyte.releasesstream.db.ReleaseRepository;
import ru.doublebyte.releasesstream.feed.AtomReader;
import ru.doublebyte.releasesstream.github.StarredRepositories;
import ru.doublebyte.releasesstream.mail.MailRenderer;
import ru.doublebyte.releasesstream.mail.MailMessageSender;
import ru.doublebyte.releasesstream.runner.Runner;

@Configuration
@EnableScheduling
public class MainConfiguration {

    @Value("${releases-stream.github-token}")
    private String githubAccessToken;

    @Value("${releases-stream.github-user}")
    private String githubUserName;

    @Value("${releases-stream.mail-from}")
    private String mailFrom;

    @Value("${releases-stream.mail-to}")
    private String mailTo;

    @Value("${releases-stream.mail-subject}")
    private String mailSubject;

    private final JavaMailSender javaMailSender;
    private final ReleaseRepository releaseRepository;

    ///////////////////////////////////////////////////////////////////////////

    @Autowired
    public MainConfiguration(
            JavaMailSender javaMailSender,
            ReleaseRepository releaseRepository
    ) {
        this.javaMailSender = javaMailSender;
        this.releaseRepository = releaseRepository;
    }

    ///////////////////////////////////////////////////////////////////////////

    @Bean
    public TaskScheduler taskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    @Bean
    public StarredRepositories starredRepositories() {
        return new StarredRepositories(githubUserName, githubAccessToken);
    }

    @Bean
    public AtomReader atomReader() {
        return new AtomReader();
    }

    @Bean
    public MailRenderer mailRenderer() {
        return new MailRenderer();
    }

    @Bean
    public MailMessageSender mailMessageSender() {
        return new MailMessageSender(javaMailSender, mailRenderer(), mailFrom, mailTo, mailSubject);
    }

    @Bean
    public Runner runner() {
        return new Runner(starredRepositories(), atomReader(), mailMessageSender(), releaseRepository);
    }

}
