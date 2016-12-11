package ru.doublebyte.releasesstream.feed;

import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Read ATOM releases feed
 */
public class AtomReader {

    private static final Logger logger = LoggerFactory.getLogger(AtomReader.class);

    public AtomReader() {

    }

    ///////////////////////////////////////////////////////////////////////////

    /**
     * Read all feed entries by URL
     * @param url Feed URL
     * @return List of entries
     */
    public List<FeedEntry> read(String url) {
        List<FeedEntry> entries = new ArrayList<>();

        try {
            SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(url)));
            feed.getEntries().forEach(entry -> {
                FeedEntry feedEntry = new FeedEntry();

                feedEntry.setId(entry.getUri());
                feedEntry.setAuthor(entry.getAuthor());
                feedEntry.setTitle(entry.getTitle());
                feedEntry.setDate(LocalDateTime.ofInstant(entry.getUpdatedDate().toInstant(), ZoneId.systemDefault()));
                feedEntry.setContent(buildContents(entry.getContents()));

                entries.add(feedEntry);
            });
        } catch (Exception e) {
            logger.error("cannot read atom feed", e);
        }

        return entries;
    }

    /**
     * Build content from SyndContent list
     * @param content Feed content list
     * @return Built content
     */
    private String buildContents(List<SyndContent> content) {
        if (content == null || content.isEmpty()) {
            return "";
        }

        StringBuilder contentBuilder = new StringBuilder();

        content.forEach(it -> contentBuilder.append(it.getValue()));

        return contentBuilder.toString();
    }

}
