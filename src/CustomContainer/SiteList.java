package CustomContainer;

import Contants.SiteConstants;
import SitePackage.Site;
import com.sun.org.apache.bcel.internal.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emanuele on 10/04/2016.
 */
public class SiteList extends ArrayList<String> {

    private ArrayList<Site> siteArrayList = new ArrayList<>();

    public SiteList() {
        siteArrayList.add(new Site(SiteConstants.HDBLOG,SiteConstants.HDBLOG_URL));
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
                System.out.println("Contains");
                System.out.println(siteArrayList.get(i).getName());
                if ((siteArrayList.get(i).getName()).equals(o)){
                    System.out.println("trovata!");
                    return true;
                }
            }
            return false;
        }

    }

    public static boolean VerifySite(String site) {
        SiteList availableSite = new SiteList();
        String siteLowerCase = site.toLowerCase();
        //availableSite.add(SiteConstants.HDBLOG);

        if(availableSite.contains(siteLowerCase)){
            return true;
        }

        return false;

    }
}
