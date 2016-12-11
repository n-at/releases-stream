package ru.doublebyte.releasesstream.feed;

import java.time.LocalDateTime;

/**
 * Releases feed entry
 */
public class FeedEntry {

    private String id;
    private String author;
    private LocalDateTime date;
    private String content;

    ///////////////////////////////////////////////////////////////////////////

    public FeedEntry() {
    }

    @Override
    public String toString() {
        return String.format("FeedEntry{id='%s', author='%s', date=%s}", id, author, date);
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
}
