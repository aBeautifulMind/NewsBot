package TelegramBot;

import SecurityPackage.BotData;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.response.GetUpdatesResponse;

import java.util.List;

/**
 * Created by Emanuele on 06/04/2016.
 */
public class Bot {

    private final String botToken = BotData.BOT_TOKEN;
    private int offset;
    private final int timeout;
    private int limit;
    private static TelegramBot telegramBot;

    public Bot(int timeout, int offset, int limit) {
        this.timeout = timeout;
        this.offset = offset;
        this.limit = limit;
        if(telegramBot==null)
            telegramBot = TelegramBotAdapter.build(botToken);
    }

    public Bot() {
        timeout = 0;
        offset=0;
        limit=0;
        if(telegramBot==null)
            telegramBot = TelegramBotAdapter.build(botToken);

    }

    public List<Update> getUpdates(){
        GetUpdatesResponse updatesResponse = telegramBot.getUpdates(offset, limit, timeout);
        List<Update> updates = updatesResponse.updates();
        return updates;
    }

    public void SendMessage(int chatId, String message) throws Exception{
        try {
            telegramBot.sendMessage(chatId,message);
        }
        catch (Exception e){
            throw e;
        }

    }
}
