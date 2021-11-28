package ru.doublebyte.releasesstream.github;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StarredRepositoriesTest {

    @Test
    public void buildUrl() throws Exception {
        String url = "https://api.github.com/users/n-at/starred?page=2";

        StarredRepositories starredRepositories = new StarredRepositories("n-at", "test");

        assertEquals(url, starredRepositories.buildUrl(2));
    }

}