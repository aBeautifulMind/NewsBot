package NewsPackage;

import CustomException.NewsAlreadyAddedException;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Giulio on 08/04/2016.
 */
public class NewsClass {

    private int id;
    private String newsSite;
    private String newsTitle;
    private String newsImg;
    private String newsDescription;
    private String newsUrl;
    private Date newsData;

    public NewsClass(String newsSite, String newsTitle, String newsImg, String newsDescription, String newsUrl, Date newsData) {
        this.newsSite = newsSite;
        this.newsTitle = newsTitle;
        this.newsImg = newsImg;
        this.newsDescription = newsDescription;
        this.newsUrl = newsUrl;
        this.newsData = newsData;
    }

    @Override
    public String toString() {
        return "NewsClass{" +
                " newsSite=" + newsSite +
                ", newsTitle='" + newsTitle + '\'' +
                ", newsImg='" + newsImg + '\'' +
                ", newsDescription='" + newsDescription + '\'' +
                ", newsUrl='" + newsUrl + '\'' +
                ", newsData=" + newsData +
                '}';
    }

    public boolean InsertNewsInDb(Connection connection) throws NewsAlreadyAddedException {
        try {

            String changedDescription = newsDescription.replace("'","''");
            String changedTitle = newsTitle.replace("'","''");
            java.sql.Date startDate = new java.sql.Date(newsData.getTime());
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO News(news_site, news_title, news_img, news_description, news_url, news_data) VALUE ('"+newsSite+"','"+changedTitle+"','"+newsImg+"','"+changedDescription+"','"+newsUrl+"','"+startDate+"')");
            statement.close();
            //connection.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            throw new NewsAlreadyAddedException("This news has already been added!");
        }

    }

    public int getId() {
        return id;
    }

    public Date getNewsData() {
        return newsData;
    }

    public String getNewsDescription() {
        return newsDescription;
    }

    public String getNewsImg() {
        return newsImg;
    }

    public String getNewsSite() {
        return newsSite;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsUrl() {
        return newsUrl;
    }
}