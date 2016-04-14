package HTMLParsing;

import Contants.SiteConstants;
import NewsPackage.NewsClass;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Emanuele on 05/04/2016.
 */
public class HDBlogParser extends BaseParser {
    private final String url = SiteConstants.HDBLOG_URL;
    private String selector="newlist_normal";
    private String lastTitle;

    public HDBlogParser(String selector) {
        this.selector = selector;
    }

    public HDBlogParser() {
    }

    public ArrayList<NewsClass> ParsePage() throws Exception{

        try {
            Document doc = getDocument("http://www.hdblog.it");
            /*
            Elements elements = doc.select(selector);
            System.out.println("elements: "+elements.size());
            for (Element element: elements) {

                System.out.println(element);
            }
            System.out.println(elements);
            */
            Elements newsList = doc.getElementsByClass(selector);
            ArrayList<NewsClass> newsClassArrayList = new ArrayList<NewsClass>();
            for (Element element: newsList) {
                Element newsTitle = element.getElementsByClass("title_new").get(0);
                Element imageContainer = element.getElementsByClass("thumb_new_image").get(0);
                Element newsImage = imageContainer.getElementsByTag("img").get(0);
                Element newsDescription = element.getElementsByTag("p").get(0);
                //Element newsData = element.getElementsByClass().get(0);
                //System.out.println(element);
                Date date = new Date();
                NewsClass news = new NewsClass("hdblog",newsTitle.text(),newsImage.attr("data-src"),newsDescription.text(),imageContainer.attr("href"),date);
                newsClassArrayList.add(news);
            }
            return newsClassArrayList;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

            }

    //test
    public static void main(String[] args){
        HDBlogParser hdBlogParser = new HDBlogParser();
        ArrayList<NewsClass> newsList = null;
        try {
            System.out.println("PARSED");
            newsList = hdBlogParser.ParsePage();

            for(int i = 0; i< newsList.size();i++){
                System.out.println(newsList.get(i));
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
