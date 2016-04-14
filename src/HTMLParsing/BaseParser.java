package HTMLParsing;

import NewsPackage.NewsClass;
import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by Emanuele on 05/04/2016.
 */
public abstract class BaseParser {
    // this is a try
    public abstract ArrayList<NewsClass> ParsePage() throws Exception;

    /**
     *
     * @param url url of the document to download
     * @return the HTML document
     * @throws Exception like IOException if there isn't connectivty or MalformedURLException if url isn't valid
     */
    public Document getDocument(String url) throws Exception{
        UrlValidator urlValidator = new UrlValidator();
        if(urlValidator.isValid(url)){
            try {
                Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36").get();
                return doc;
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }

        }
        else {
            throw new MalformedURLException(url+" is not a valid URL");
        }
    }
}
