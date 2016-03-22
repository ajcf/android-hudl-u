package cadwell_frost.amanda.hudlu.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by amanda.cadwellfrost on 3/21/16.
 */
public class MashableNews {
    @SerializedName("new")
    public List<MashableNewsItem> newsItems;
}
