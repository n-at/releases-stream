package ru.doublebyte.releasesstream.github;

import org.junit.Test;

import static org.junit.Assert.*;

public class StarredRepositoriesTest {

    @Test
    public void buildUrl() throws Exception {
        String url = "https://api.github.com/users/n-at/starred?access_token=test&page=2";

        StarredRepositories starredRepositories = new StarredRepositories("n-at", "test");

        assertEquals(url, starredRepositories.buildUrl(2));

    }

}