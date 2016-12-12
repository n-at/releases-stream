package ru.doublebyte.releasesstream.db;

import ru.doublebyte.releasesstream.feed.FeedEntry;
import ru.doublebyte.releasesstream.github.Repository;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Database release record
 */
@Entity
@Table(name = "releases")
public class Release {

    private static final String DATE_TIME_PATTERN = "dd.MM.yyyy HH:mm";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    @Id
    @Column(name = "id", length = 5000)
    private String id;

    @Column(name = "author", length = 5000)
    private String author;

    @Column(name = "title", length = 5000)
    private String title;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "content", length = 10_000_000)
    private String content;

    @Column(name = "repo_name", length = 5000)
    private String repositoryName;

    @Column(name = "repo_description", length = 5000)
    private String repositoryDescription;

    @Column(name = "repo_url", length = 5000)
    private String repositoryUrl;

    ///////////////////////////////////////////////////////////////////////////

    public Release() {

    }

    public Release(Repository repository, FeedEntry feedEntry) {
        id = feedEntry.getId();
        author = feedEntry.getAuthor();
        title = feedEntry.getTitle();
        date = feedEntry.getDate();
        content = feedEntry.getContent();

        repositoryName = repository.getName();
        repositoryDescription = repository.getDescription();
        repositoryUrl = repository.getUrl();
    }

    @Transient
    public String getDateFormatted() {
        if (date == null) {
            return null;
        }
        return date.format(DATE_TIME_FORMATTER);
    }

    ///////////////////////////////////////////////////////////////////////////

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getRepositoryDescription() {
        return repositoryDescription;
    }

    public void setRepositoryDescription(String repositoryDescription) {
        this.repositoryDescription = repositoryDescription;
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }
}
