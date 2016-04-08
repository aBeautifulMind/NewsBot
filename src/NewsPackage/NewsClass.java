package NewsPackage;

import java.util.Date;

/**
 * Created by Giulio on 08/04/2016.
 */
public class NewsClass {

    private int id;
    private int newsSite;
    private String newsTitle;
    private String newsImg;
    private String newsDescription;
    private String newsUrl;
    private Date newsData;

    public NewsClass(int newsSite, String newsTitle, String newsImg, String newsDescription, String newsUrl, Date newsData) {
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
                "id=" + id +
                ", newsSite=" + newsSite +
                ", newsTitle='" + newsTitle + '\'' +
                ", newsImg='" + newsImg + '\'' +
                ", newsDescription='" + newsDescription + '\'' +
                ", newsUrl='" + newsUrl + '\'' +
                ", newsData=" + newsData +
                '}';
    }
}