package CustomException;

/**
 * Created by Emanuele on 13/04/2016.
 */
public class NewsAlreadyAddedException extends Exception {

    public NewsAlreadyAddedException() {
        super();
    }

    public NewsAlreadyAddedException(String message){
        super(message);
    }
}
