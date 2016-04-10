package User;

import DatabaseUtils.ConnectionPool;

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

    public boolean addUserToDb(ConnectionPool connectionPool){
        try {
            Connection connection = connectionPool.getConnection();
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO User(id,name,surname,username,chat_id) VALUE ("+id+",'"+name+"','"+surname+"','"+username+"',"+chatId+")");
            statement.close();
            connection.close();
            connectionPool.releaseConnection(connection);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
