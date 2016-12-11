package ru.doublebyte.releasesstream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import ru.doublebyte.releasesstream.github.StarredRepositories;

public class Runner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(Runner.class);

    private final StarredRepositories starredRepositories;

    public Runner(StarredRepositories starredRepositories) {
        this.starredRepositories = starredRepositories;
    }

    @Override
    public void run(String... strings) throws Exception {
        starredRepositories.getRepositories().forEach(it -> logger.info(it.toString()));
    }
}
