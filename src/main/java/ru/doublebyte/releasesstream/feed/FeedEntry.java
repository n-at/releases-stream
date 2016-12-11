package ru.doublebyte.releasesstream.feed;

import java.time.LocalDateTime;

/**
 * Releases feed entry
 */
public class FeedEntry implements Comparable<FeedEntry> {

    private String id;
    private String author;
    private LocalDateTime date;
    private String title;
    private String content;

    ///////////////////////////////////////////////////////////////////////////

    public FeedEntry() {
    }

    @Override
    public int compareTo(FeedEntry o) {
        if (o == null) {
            return -1;
        }
        if (date == null) {
            return -1;
        }
        return -date.compareTo(o.getDate());
    }

    @Override
    public String toString() {
        return String.format("FeedEntry{id='%s', author='%s', date=%s, title=%s}", id, author, date, title);
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
