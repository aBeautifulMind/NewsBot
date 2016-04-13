package User;

import CustomException.SiteAlreadyAddedException;
import DatabaseUtils.ConnectionPool;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by Emanuele on 10/04/2016.
 */
public class User {
    private int id;
    private String name;
    private String surname;
    private String username;
    private long chatId;

    public User(long chatId, int id, String name, String surname, String username) {
        this.chatId = chatId;
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
    }

    public boolean addUserToDb(Connection connection){
        try {
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO User(id,name,surname,username,chat_id) VALUE ("+id+",'"+name+"','"+surname+"','"+username+"',"+chatId+")");
            statement.close();
            //connection.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean addPrefs(String site, Connection connection) throws SiteAlreadyAddedException{
        try {
            System.out.println("ehy dentro add prefs");
            //Connection connection = connectionPool.getConnection();
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO UserPrefs(user_id, news_site) VALUE ("+id+",'"+site+"')");
            statement.close();
            //connection.close();
            return true;
        } catch (Exception e) {
            System.out.println("exception");
            throw new SiteAlreadyAddedException("This site has already been added!");
        }

    }
}
