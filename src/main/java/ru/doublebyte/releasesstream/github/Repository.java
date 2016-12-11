package ru.doublebyte.releasesstream.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * GitHub repository info
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Repository {

    @JsonProperty("full_name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("html_url")
    private String url;

    ///////////////////////////////////////////////////////////////////////////

    public Repository() {
    }

    public String getReleasesAtomUrl() {
        return UriComponentsBuilder.fromHttpUrl(url)
                .pathSegment("releases.atom")
                .build()
                .toString();
    }

    @Override
    public String toString() {
        return String.format("Repository{name=%s}", name);
    }

    ///////////////////////////////////////////////////////////////////////////

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
