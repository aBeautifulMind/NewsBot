package SitePackage;

/**
 * Created by Emanuele on 13/04/2016.
 */
public class Site {
    private String url;
    private String name;

    public Site(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
