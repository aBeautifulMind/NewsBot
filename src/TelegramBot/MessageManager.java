package TelegramBot;

import Contants.SiteConstants;
import CustomContainer.SiteList;
import DatabaseUtils.ConnectionPool;
import SecurityPackage.DatabaseData;
import User.User;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.ChosenInlineResult;
import com.pengrad.telegrambot.model.InlineQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.sun.org.apache.bcel.internal.Constants;

import java.util.ArrayList;

/**
 * Created by Emanuele on 06/04/2016.
 */
public class MessageManager implements TelegramInterface{

    private Bot bot;
    private ConnectionPool connectionPool;

    public MessageManager(Bot bot) {
        this.bot = bot;
        createConnectionPool();
    }

    public void createConnectionPool(){
        try {
            connectionPool = new ConnectionPool(DatabaseData.DB_PW,DatabaseData.DB_URL,DatabaseData.DB_USER,DatabaseData.DB_DRIVER,10,10);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args){
        Bot bot = new Bot();
        MessageManager messageManager = new MessageManager(bot);
        while (true)
            bot.getUpdates(messageManager);
    }

    public void OnUpdateReceived(Message message, Update update) {
        String command = "";
        String realMessage = "";
        if(message.text().startsWith("/start")) {
            try {
                bot.SendMessage(message.chat().id(), "EHI CIAO, BENVENUTO SU NEWS BOT! Tra poco saranno disponibili molti servizi news");
                User user = new User(message.chat().id(),update.message().from().id(),message.from().firstName(),message.from().lastName(),message.from().username());
                if(user.addUserToDb(connectionPool)){
                    bot.SendMessage(message.chat().id(),"Complimenti, sei stato aggiunto correttamente al DB!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        if(message.text().contains(" ")){
            command = message.text().substring(0,message.text().indexOf(" "));
            realMessage = message.text().substring(message.text().indexOf(" ")+1);
        }

        switch (command){
            case ("Aggiungi"):
                if(VerifySite(realMessage)){
                    try {
                        bot.SendMessage(message.chat().id(),"AGGIUNTO CORRETAMENTE");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            default:

        }

    }

    private boolean VerifySite(String site) {
        SiteList availableSite = new SiteList();
        String siteLowerCase = site.toLowerCase();
        availableSite.add(SiteConstants.HDBLOG);

        if(availableSite.contains(siteLowerCase)){
            return true;
        }

        return false;

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
