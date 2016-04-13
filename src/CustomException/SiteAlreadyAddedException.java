package CustomException;

/**
 * Created by Emanuele on 13/04/2016.
 */
public class SiteAlreadyAddedException extends Exception {

    public SiteAlreadyAddedException() {
        super();
    }

    public SiteAlreadyAddedException(String message){
        super(message);
    }
}

