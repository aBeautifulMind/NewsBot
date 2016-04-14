package HTMLParsing;

import CustomException.NewsAlreadyAddedException;
import NewsPackage.NewsClass;
import TelegramBot.Bot;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Emanuele on 13/04/2016.
 */
public class RunnableParser implements Runnable {

    private ArrayList<BaseParser> baseParserArrayList;
    private Connection connection;
    private Bot bot;


    public RunnableParser(ArrayList<BaseParser> baseParsers, Connection connection, Bot bot) {
        this.baseParserArrayList = baseParsers;
        this.connection = connection;
        this.bot=bot;
    }

    @Override
    public void run() {
        ArrayList<NewsClass> downloadedNews;
        ArrayList<NewsClass> notAreadyAddedNews;
        for(int i = 0; i < baseParserArrayList.size(); i++ ){
            try {
                downloadedNews = baseParserArrayList.get(i).ParsePage();
                notAreadyAddedNews = FindNewNews(downloadedNews);
                SendNewsToUser(notAreadyAddedNews);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void SendNewsToUser(ArrayList<NewsClass> notAreadyAddedNews) {
        try {
            System.out.println(notAreadyAddedNews);
        for (int i = 0; i< notAreadyAddedNews.size();i ++){
            NewsClass news = notAreadyAddedNews.get(i);
            System.out.println(news);
            Statement s = connection.createStatement();
            ResultSet res = s.executeQuery("SELECT * from User JOIN UserPrefs on User.id = UserPrefs.user_id WHERE UserPrefs.news_site = '"+notAreadyAddedNews.get(i).getNewsSite()+"'");
            while (res.next()){
                String name = res.getString("name");
                long chat_id = res.getLong("chat_id");
                try {
                    bot.SendMessage(chat_id,"Nuova news da "+news.getNewsSite()+" "+news.getNewsSite()+" url:"+news.getNewsUrl()+" Immagine : "+news.getNewsImg());
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
            res.close();
            s.close();
        }
            //connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<NewsClass> FindNewNews(ArrayList<NewsClass> newsToFind) {
        ArrayList<NewsClass> notAlreadyAddedNews = new ArrayList<>();
        for (int i = 0; i< newsToFind.size(); i++){
            try{
                (newsToFind.get(i)).InsertNewsInDb(connection);
                notAlreadyAddedNews.add(newsToFind.get(i));
            }
            catch (NewsAlreadyAddedException e){
                //System.out.println("exc: "+e);
            }
        }
        return notAlreadyAddedNews;

    }
}
