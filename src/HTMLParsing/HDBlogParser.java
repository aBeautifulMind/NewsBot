package HTMLParsing;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Emanuele on 05/04/2016.
 */
public class HDBlogParser extends BaseParser {
    private final String url = "http://www.hdblog.it/";
    private String selector=".newlist_normal";
    private String lastTitle;

    public HDBlogParser(String selector) {
        this.selector = selector;
    }

    public HDBlogParser() {
    }

    public void ParsePage() {
        try {
            Document doc = getDocument(url);
            Elements elements = doc.select(selector);
            System.out.println("elements: "+elements.size());
            for (Element element: elements) {
                System.out.println(element);
            }
            System.out.println(elements);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //test
    public static void main(String[] args){
        System.out.println("Init...");
        HDBlogParser hdBlogParser = new HDBlogParser();
        hdBlogParser.ParsePage();
    }
}
