package ru.doublebyte.releasesstream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import ru.doublebyte.releasesstream.feed.AtomReader;
import ru.doublebyte.releasesstream.github.StarredRepositories;

public class Runner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(Runner.class);

    private final AtomReader atomReader;

    public Runner(AtomReader atomReader) {
        this.atomReader = atomReader;
    }

    @Override
    public void run(String... strings) throws Exception {
        atomReader.read("https://github.com/docker/docker/releases.atom").forEach(it -> logger.info(it.toString()));
    }
}
