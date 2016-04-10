package CustomContainer;

import Contants.SiteConstants;
import com.sun.org.apache.bcel.internal.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emanuele on 10/04/2016.
 */
public class SiteList extends ArrayList<String> {

    private ArrayList<String> siteArrayList = new ArrayList<>();

    public SiteList() {
        siteArrayList.add(SiteConstants.HDBLOG);
    }

    @Override
    public boolean contains(Object o) {
        if(! (o instanceof String)) {
            //throw new Exception(o + "is not a String!");
            return false;
        }
        else{
            String objectToCompare = (String) o;
            for(int i = 0; i < siteArrayList.size();i++){
                if (siteArrayList.get(i).equals(o)){
                    return true;
                }
            }
            return false;
        }

    }
}
