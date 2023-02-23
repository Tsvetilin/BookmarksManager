package bg.sofia.uni.fmi.mjt.bookmarks.server.services.htmlextractor;

import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.HtmlExtractorException;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.Service;
import org.jsoup.nodes.Document;

public interface HtmlExtractorService extends Service {
    Document get(String url) throws HtmlExtractorException;
}
