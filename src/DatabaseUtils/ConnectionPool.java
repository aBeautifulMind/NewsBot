package DatabaseUtils;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Emanuele on 06/04/2016.
 */
public class ConnectionPool {
    private String dbUrl;
    private String dbUser;
    private String dbPw;
    private String driverString;
    // array di connessioni al database
    Connection connections[];
    // array delle disponibilità delle connessioni
    boolean busy[];
    // registra chi sta tenendo occupata la connessione
    String who[];
    // numero di connessioni attive
    int numCon;
    // incremento dimensione del pool per accogliere nuove richieste
    int inc;

    public ConnectionPool(String dbPw, String dbUrl, String dbUser, String driverString, int inc, int numCon) throws Exception {
        this.dbPw = dbPw;
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.driverString = driverString;
        this.inc = inc;
        this.numCon = numCon;
        newConnections();
    }

    private synchronized void newConnections() throws Exception {
        // alloca gli array globali (connessioni e info)
        connections = new Connection[numCon];
        busy = new boolean[numCon];
        who = new String[numCon];
        Class.forName(driverString);
        for (int i = 0; i < numCon; i++) {
            connections[i] = DriverManager.getConnection(dbUrl, dbUser, dbPw);
            busy[i] = false;
            who[i] = "";
        }
    }

    public synchronized void extendConnections() throws Exception {
        Connection connectionsAdded[] = connections;
        boolean busyAdded[] = busy;
        String whoAdded[] = who;

        connections = new Connection[numCon + inc];
        busy = new boolean[numCon + inc];
        who = new String[numCon + inc];

        for (int i = 0; i < numCon; i++){
            connections[i] = connectionsAdded[i];
            busy[i] = busyAdded[i];
            who[i] = whoAdded[i];
        }

        for (int i = numCon; i < numCon + inc; i++){
            connections[i] = DriverManager.getConnection(dbUrl, dbUser, dbPw);
            busy[i] = false;
            who[i] = "";
        }

        numCon += inc;
    }

    public synchronized Connection getConnection(String who) throws Exception{

        int indFree = findFreeConnection();
        if (indFree < 0){
            extendConnections();
            indFree = findFreeConnection();
            if (indFree < 0)
                return null;
        }
        busy[indFree] = true;
        this.who[indFree] = who;
        return connections[indFree];
    }

    public synchronized Connection getConnection() throws Exception{
        return getConnection("noName");
    }

    public synchronized  void releaseConnection (Connection connectionToRelease){
        for (int i = 0; i < numCon; i++){
            if (connections[i] == connectionToRelease){
                who[i] = "";
                busy[i] = false;
            }
        }
    }

    protected int findFreeConnection(){
        for (int i = 0; i < numCon; i++)
            if (!busy[i])
                return i;
        return -1;
    }

    public String printStatusConnection(){
        String result = "";
        for (int i = 0; i < numCon; i++)
            result += "Conn. " + i + ": " + busy[i] + "used by: " + who[i];
        return result;
    }
}