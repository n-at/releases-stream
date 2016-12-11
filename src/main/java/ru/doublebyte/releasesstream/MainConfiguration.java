package ru.doublebyte.releasesstream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.doublebyte.releasesstream.db.ReleaseRepository;
import ru.doublebyte.releasesstream.feed.AtomReader;
import ru.doublebyte.releasesstream.github.StarredRepositories;

@Configuration
@EnableScheduling
public class MainConfiguration {

    @Value("${releases-stream.github-token}")
    private String githubAccessToken;

    @Value("${releases-stream.github-user}")
    private String githubUserName;

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
    public StarredRepositories starredRepositories() {
        return new StarredRepositories(githubUserName, githubAccessToken);
    }

    @Bean
    public AtomReader atomReader() {
        return new AtomReader();
    }

    @Bean
    public Runner runner() {
        return new Runner(atomReader());
    }

}
