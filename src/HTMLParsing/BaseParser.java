package HTMLParsing;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by Emanuele on 05/04/2016.
 */
public abstract class BaseParser {
    // this is a try
    public abstract void ParsePage();

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
                Document doc = Jsoup.connect(url).get();
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
