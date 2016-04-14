package TelegramBot;

import CustomContainer.SiteList;
import CustomException.SiteAlreadyAddedException;
import DatabaseUtils.ConnectionPool;
import User.User;
import com.pengrad.telegrambot.model.Message;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * Created by Emanuele on 13/04/2016.
 * This is a thread that really manage message
 */
public class MessageThread implements Runnable{

    private Message messageToManage;
    private Connection connection;
    private Bot bot;

    public MessageThread(Message message, Connection connection, Bot bot) {
        this.messageToManage = message;
        this.connection = connection;
        this.bot = bot;

    }

    @Override
    public void run() {
        System.out.println("nel thread");
        String command = "";
        String realMessage = "";
        User user = new User(messageToManage.chat().id(),messageToManage.from().id(),messageToManage.from().firstName(),messageToManage.from().lastName(),messageToManage.from().username());
        if(messageToManage.text().startsWith("/start")) {
            try {
                bot.SendMessage(messageToManage.chat().id(), "EHI CIAO, BENVENUTO SU NEWS BOT! Tra poco saranno disponibili molti servizi news");
                if(user.addUserToDb(connection)){
                    bot.SendMessage(messageToManage.chat().id(),"Complimenti, sei stato aggiunto correttamente al DB!");
                    bot.SendMessage(messageToManage.chat().id(), "Manda Aggiungi e poi un sito per aggiungere un sito ai tuoi preferiti!");
                }
                else {
                    bot.SendMessage(messageToManage.chat().id(),"Ciao! Vedo che sei tornato su NewsBot!");
                    bot.SendMessage(messageToManage.chat().id(), "Manda Aggiungi e poi un sito per aggiungere un sito ai tuoi preferiti!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        }

        if(messageToManage.text().contains(" ")){
            command = messageToManage.text().substring(0,messageToManage.text().indexOf(" "));
            realMessage = messageToManage.text().substring(messageToManage.text().indexOf(" ")+1);
        }

        System.out.println(messageToManage);
        System.out.println(command+" e : "+ realMessage);
        switch (command){
            case ("Aggiungi"):
                System.out.println("Dentro!!");
                if(SiteList.VerifySite(realMessage)){
                    System.out.println("DENTRO IF");
                    String messageToSend;
                    try {
                        user.addPrefs(realMessage.toLowerCase(),connection);
                        messageToSend = "Sito aggiunto correttamente ai tuoi preferiti!";
                        System.out.println("AGGIUNTO!");
                    } catch (SiteAlreadyAddedException e) {
                        messageToSend = "Il sito è già stato aggiunto ai tuoi preferiti!";
                    }

                    try {
                        System.out.println("INVIO!");
                        bot.SendMessage(messageToManage.chat().id(),messageToSend);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else{
                    try {
                        bot.SendMessage(messageToManage.chat().id(),"Spiacenti, sito non ancora disponibile!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            default:
                System.out.println("DEFAULT");
                try {
                    bot.SendMessage(messageToManage.chat().id(), "Manda Aggiungi e poi un sito per aggiungere un sito ai tuoi preferiti!");
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
