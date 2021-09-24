package ru.doublebyte.releasesstream.github;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RepositoryTest {

    @Test
    public void getReleasesAtomUrl() throws Exception {
        String url = "https://github.com/n-at/arbirter";
        String releasesUrl = "https://github.com/n-at/arbirter/releases.atom";

        Repository repository = new Repository();
        repository.setName("n-at/arbirter");
        repository.setDescription("Alternative renderer of BIRT engine reports");
        repository.setUrl(url);

        assertEquals(releasesUrl, repository.getReleasesAtomUrl());
    }

}