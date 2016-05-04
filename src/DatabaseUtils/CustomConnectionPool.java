package DatabaseUtils;

import SecurityPackage.DatabaseData;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Emanuele on 04/05/2016.
 */
public class CustomConnectionPool {

    //instance
    private static CustomConnectionPool customConnectionPool;
    private BasicDataSource trueConnectionPool;

    //private constructor
    private CustomConnectionPool(){
        createConnectionPool();
    }

    public void createConnectionPool(){
        try {
            trueConnectionPool = new BasicDataSource();
            trueConnectionPool.setUrl(DatabaseData.DB_URL);
            trueConnectionPool.setUsername(DatabaseData.DB_USER);
            trueConnectionPool.setPassword(DatabaseData.DB_PW);
            trueConnectionPool.setDriverClassName(DatabaseData.DB_DRIVER);
            trueConnectionPool.setInitialSize(50);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static CustomConnectionPool getInstance(){
        if(customConnectionPool == null){
            customConnectionPool = new CustomConnectionPool();
        }
        return customConnectionPool;
    }

    public Connection getConnection() throws SQLException {
        return trueConnectionPool.getConnection();
    }

}
