package TelegramBot;

import HTMLParsing.BaseParser;
import HTMLParsing.HDBlogParser;
import HTMLParsing.RunnableParser;
import SecurityPackage.DatabaseData;
import com.pengrad.telegrambot.model.ChosenInlineResult;
import com.pengrad.telegrambot.model.InlineQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * Created by Emanuele on 06/04/2016.
 */
public class MessageManager implements TelegramInterface{

    private Bot bot;
    private BasicDataSource connectionPool;
    private ExecutorService executorService;

    public MessageManager(Bot bot) {
        this.bot = bot;
        this.executorService = Executors.newCachedThreadPool();
        createConnectionPool();
    }

    public void createConnectionPool(){
        try {
            connectionPool = new BasicDataSource();
            connectionPool.setUrl(DatabaseData.DB_URL);
            connectionPool.setUsername(DatabaseData.DB_USER);
            connectionPool.setPassword(DatabaseData.DB_PW);
            connectionPool.setDriverClassName(DatabaseData.DB_DRIVER);
            connectionPool.setLogAbandoned(true);
            //connectionPool.setMaxConnLifetimeMillis(10000*5);
            connectionPool.setInitialSize(100);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args){
        Bot bot = new Bot();
        final MessageManager messageManager = new MessageManager(bot);
        // And From your main() method or any other method
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                StartRunnable(messageManager);
            }
        },0,1, TimeUnit.HOURS);
        while (true)
            bot.getUpdates(messageManager);
    }

    public static void StartRunnable(MessageManager messageManager) {
        System.out.println("Starting runnable...");
        ArrayList<BaseParser> baseParsers = new ArrayList<>();
        baseParsers.add(new HDBlogParser());
        RunnableParser runnableParser = null;
        try {
            runnableParser = new RunnableParser(baseParsers,messageManager.connectionPool.getConnection(),messageManager.bot);
            messageManager.executorService.execute(runnableParser);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void OnUpdateReceived(Message message, Update update) {
        System.out.println("Received message!");
        MessageThread messageThread = null;
        try {
            messageThread = new MessageThread(message,connectionPool.getConnection(), bot);
            executorService.execute(messageThread);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    public void OnFileReceived(Message message) {

    }

    public void OnInlineQuery(Message message, InlineQuery inlineQuery) {

    }

    public void OnChosenInlineResult(ChosenInlineResult chosenInlineResult) {

    }

    public void OnPhotoReceived(Message message) {

    }
}
