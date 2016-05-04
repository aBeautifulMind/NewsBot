package NewsPackage;

import CustomException.NewsAlreadyAddedException;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.DriverManager;

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
            //System.out.println(e);
            throw new NewsAlreadyAddedException("This news has already been added!");
        }

    }

    public boolean InsertNewsInDbv2(Connection connection) throws SQLException, NewsAlreadyAddedException{
        PreparedStatement insertNewsStatement = null;
        boolean result=false;

        String insertString = "INSERT INTO News(news_site, news_title, news_img, news_description, news_url, news_data) VALUES"+
        " ( ? , ? , ? , ? , ? , ? )";
        try {
            connection.setAutoCommit(false);
            insertNewsStatement = connection.prepareStatement(insertString);

            insertNewsStatement.setString(1, newsSite);
            insertNewsStatement.setString(2,newsTitle);
            insertNewsStatement.setString(3,newsImg);
            insertNewsStatement.setString(4,newsDescription);
            insertNewsStatement.setString(5,newsUrl);

            java.sql.Date startDate = new java.sql.Date(newsData.getTime());
            insertNewsStatement.setDate(5,startDate);
            connection.commit();
            result=true;
        }
        catch (SQLException e){
            e.printStackTrace();
            if(connection!=null){
                try{
                    System.err.println("Transaction is being rolled back");
                    connection.rollback();
                }
                catch (SQLException excep){
                    e.printStackTrace();
                }
            }
            result=false;
        }
        finally {
            if(insertNewsStatement!=null){
                insertNewsStatement.close();
            }
            connection.setAutoCommit(true);
        }

        if(!result) {
            throw new NewsAlreadyAddedException("Already added");
        }
        return result;
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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof NewsClass)){
            return false;
        }
        else{
            NewsClass news = (NewsClass) obj;
            if(this.newsSite.equals(news.newsSite) && this.newsImg.equals(news.newsImg) && this.newsData.equals(news.newsData) && this.newsDescription.equals(news.newsDescription) && this.newsUrl.equals(news.newsUrl)){
                return true;
            }
        }
        return false;
    }
}