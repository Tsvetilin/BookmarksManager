package bg.sofia.uni.fmi.mjt.bookmarks.server.services.htmlextractor;

import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.HtmlExtractorException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class DefaultHtmlExtractorService implements HtmlExtractorService {

    @Override
    public Document get(String url) throws HtmlExtractorException {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new HtmlExtractorException(e);
        }
    }
}
