package HTMLParsing;

import Contants.SiteConstants;
import NewsPackage.NewsClass;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by Giulio on 14/04/2016.
 */
public class TomsParser extends BaseParser{
    private final String url = SiteConstants.TOMS_URL;
    private String selector="list menu-mini";
    private String lastTitle;

    public TomsParser (){}
    public TomsParser (String selector){this.selector = selector;}


    @Override
    public ArrayList<NewsClass> ParsePage() throws Exception {
        try {
            Document document = getDocument(url);
            Elements newsList = document.getElementsByTag("li");
            ArrayList<NewsClass> newsClassArrayList = new ArrayList<NewsClass>();
            for (Element element : newsList){
                Element newsTitle = element.getElementsByTag("a").get(0);
                System.out.println(newsTitle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main (String[] args) throws Exception {
        TomsParser tomsParser = new TomsParser();
        ArrayList<NewsClass> prova = tomsParser.ParsePage();
    }

}
