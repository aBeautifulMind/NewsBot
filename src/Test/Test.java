package Test;

import CustomException.NewsAlreadyAddedException;
import DatabaseUtils.CustomConnectionPool;
import NewsPackage.NewsClass;
import org.junit.*;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Date;

/**
 * Created by Emanuele on 04/05/2016.
 */
public class Test {

    private NewsClass news;
    private NewsClass news2;
    CustomConnectionPool customConnectionPool;

    @Before public void setUp(){
        Date date = new Date();
        news = new NewsClass("prova","PROVA","immagine","descr","www.manu.com",date);
        news2 = new NewsClass("prova","PROVA","immagine","descr","www.manu.com",date);
         customConnectionPool = CustomConnectionPool.getInstance();
    }

    @org.junit.Test public void TestEquals(){
        assertNotNull(news);
        assertNotNull(news2);
        assertEquals(news,news2);
        Date date = new Date();
        assertNotEquals(news, new NewsClass("prova","PROVA","immagine","descr","www.manu.com",date));

    }

    @org.junit.Test (timeout=10000)
    public void TestInsert(){

        try {
            news.InsertNewsInDb(customConnectionPool.getConnection());
        } catch (NewsAlreadyAddedException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
