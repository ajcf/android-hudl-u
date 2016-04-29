package cadwell_frost.amanda.hudlu.models;

import io.realm.RealmObject;

/**
 * Created by amanda.cadwellfrost on 4/28/16.
 */
public class Favorite extends RealmObject {
    private String _title;
    private String _author;
    private String _image;
    private String _link;

    public void setTitle(String title)
    {
        _title = title;
    }

    public String getTitle()
    {
        return _title;
    }

    public void setAuthor(String author)
    {
        _author = author;
    }

    public String getAuthor()
    {
        return _author;
    }

    public void setImage(String image)
    {
        _image = image;
    }

    public String getImage()
    {
        return _image;
    }

    public void setLink(String link)
    {
        _link = link;
    }

    public String link()
    {
        return _link;
    }

}
