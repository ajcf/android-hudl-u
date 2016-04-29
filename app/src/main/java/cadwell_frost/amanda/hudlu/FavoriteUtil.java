package cadwell_frost.amanda.hudlu;

import cadwell_frost.amanda.hudlu.models.Favorite;
import cadwell_frost.amanda.hudlu.models.MashableNewsItem;
import io.realm.RealmResults;
import io.realm.internal.Context;

/**
 * Created by amanda.cadwellfrost on 4/28/16.
 */
public class FavoriteUtil {

    public static void addFavorite(Context context, MashableNewsItem newsItem)
    {

    }

    public static void removeFavorite(Context context, MashableNewsItem newsItem)
    {

    }

    public static boolean isFavorite(Context context, MashableNewsItem newsItem)
    {
        return false;
    }

    public static RealmResults<Favorite> getAllFavorites(Context context)
    {
        return null;//new RealmResults<Favorite>();
    }
}
