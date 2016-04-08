package HTMLParsing;

import NewsPackage.NewsClass;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;

/**
 * Created by Emanuele on 05/04/2016.
 */
public class HDBlogParser extends BaseParser {
    private final String url = "http://www.hdblog.it/";
    private String selector=".newlist_normal";
    private String lastTitle;
    private String[] newsCategory = {"cat_games, cat_android, cat_mobile, "}

    public HDBlogParser(String selector) {
        this.selector = selector;
    }

    public HDBlogParser() {
    }

    public void ParsePage() {

        try {
            Document doc = getDocument(url);
            /*
            Elements elements = doc.select(selector);
            System.out.println("elements: "+elements.size());
            for (Element element: elements) {

                System.out.println(element);
            }
            System.out.println(elements);
            */
            Elements newsList = doc.getElementsByClass(".newlist_normal");
            ArrayList<NewsClass> newsClassArrayList = new ArrayList<>();
            for (Element element: newsList) {
                Element newsTitle = element.getElementsByClass("title_new").get(0);
                Element newsImage = element.getElementsByClass("thumb_new_image").get(0);
                Element newsDescription = element.getElementsByClass().get(0);
                Element newsUrl = element.getElementsByClass().get(0);
                Element newsData = element.getElementsByClass().get(0);
                Element newsSite = element.getElementsByClass().get(0);
                System.out.println(element);

            }
            System.out.println(newsList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //test
    public static void main(String[] args){
        HDBlogParser hdBlogParser = new HDBlogParser();
        hdBlogParser.ParsePage();
        //lo metto nel db
    }
}
