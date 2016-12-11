package ru.doublebyte.releasesstream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.doublebyte.releasesstream.github.StarredRepositories;

@Configuration
@EnableScheduling
public class MainConfiguration {

    @Value("${releases-stream.github-token}")
    private String githubAccessToken;

    @Value("${releases-stream.github-user}")
    private String githubUserName;

    private JavaMailSender javaMailSender;

    ///////////////////////////////////////////////////////////////////////////

    @Autowired
    public MainConfiguration(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    ///////////////////////////////////////////////////////////////////////////

    @Bean
    public StarredRepositories starredRepositories() {
        return new StarredRepositories(githubUserName, githubAccessToken);
    }

    @Bean
    public Runner runner() {
        return new Runner(starredRepositories());
    }

}