package ru.doublebyte.releasesstream.github;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Fetch user starts on GitHub
 */
public class StarredRepositories {

    private static final Logger logger = LoggerFactory.getLogger(StarredRepositories.class);

    private static final String API_URL = "https://api.github.com/";

    private final RestTemplate restTemplate = new RestTemplate();
    private final String userName;
    private final String accessToken;

    public StarredRepositories(String userName, String accessToken) {
        this.userName = userName;
        this.accessToken = accessToken;
    }

    ///////////////////////////////////////////////////////////////////////////

    /**
     * List user's starred repositories
     * @return List of repos
     */
    public List<Repository> getRepositories() {
        List<Repository> repositories = new ArrayList<>();
        int currentPage = 1;

        logger.info("requesting starred repositories for {}...", userName);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/vnd.github.v3+json");
        headers.set("User-Agent", "releases-stream");
        headers.set("Authorization", "token " + accessToken);

        while (true) {
            try {
                ResponseEntity<Repository[]> responseEntity = restTemplate.exchange(
                        buildUrl(currentPage),
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        Repository[].class
                );

                Repository[] repositoriesBatch = responseEntity.getBody();

                if (repositoriesBatch == null || repositoriesBatch.length == 0) {
                    break;
                }

                repositories.addAll(Arrays.asList(repositoriesBatch));
            } catch (Exception e) {
                logger.error("starred repositories fetch error", e);
                break;
            }

            currentPage++;
        }

        logger.info("fetched {} starred repositories", repositories.size());

        return repositories;
    }

    ///////////////////////////////////////////////////////////////////////////

    /**
     * Create URL to fetch user starred repos
     * @param page Current page number
     * @return URL
     */
    protected String buildUrl(int page) {
        return UriComponentsBuilder.fromHttpUrl(API_URL)
                .pathSegment("users", userName, "starred")
                .queryParam("page", page)
                .build()
                .toString();
    }

}
