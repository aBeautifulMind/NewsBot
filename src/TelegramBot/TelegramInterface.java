package TelegramBot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.ChosenInlineResult;
import com.pengrad.telegrambot.model.InlineQuery;
import com.pengrad.telegrambot.model.Message;

/**
 * Created by Emanuele on 10/04/2016.
 */
public interface TelegramInterface {
    public void OnUpdateReceived(Message message);
    public void OnFileReceived(Message message);
    public void OnInlineQuery(Message message, InlineQuery inlineQuery);
    public void OnChosenInlineResult(ChosenInlineResult chosenInlineResult);
    public void OnPhotoReceived(Message message);
}
