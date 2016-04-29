package cadwell_frost.amanda.hudlu;

import android.content.Context;

import cadwell_frost.amanda.hudlu.models.Favorite;
import cadwell_frost.amanda.hudlu.models.MashableNewsItem;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by amanda.cadwellfrost on 4/28/16.
 */
public class FavoriteUtil {

    public static void addFavorite(Context context, MashableNewsItem newsItem)
    {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(context).build();
        Realm realm = Realm.getInstance(realmConfig);

        realm.beginTransaction();
        Favorite favorite = realm.createObject(Favorite.class);
        favorite.setTitle(newsItem.title);
        favorite.setAuthor(newsItem.author);
        favorite.setImage(newsItem.feature_image);
        favorite.setLink(newsItem.link);
        realm.commitTransaction();
    }

    public static void removeFavorite(Context context, MashableNewsItem newsItem)
    {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(context).build();
        Realm realm = Realm.getInstance(realmConfig);

        RealmResults<Favorite> results = realm.where(Favorite.class)
                .equalTo("_link", newsItem.link)
                .equalTo("_author", newsItem.author)
                .equalTo("_image", newsItem.feature_image)
                .equalTo("_title", newsItem.title)
                .findAll();

        realm.beginTransaction();
        results.deleteAllFromRealm();
        realm.commitTransaction();
    }

    public static boolean isFavorite(Context context, MashableNewsItem newsItem)
    {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(context).build();
        Realm realm = Realm.getInstance(realmConfig);

        RealmResults<Favorite> results = realm.where(Favorite.class)
                .equalTo("_link", newsItem.link)
                .equalTo("_author", newsItem.author)
                .equalTo("_image", newsItem.feature_image)
                .equalTo("_title", newsItem.title)
                .findAll();

        return !results.isEmpty();
    }

    public static RealmResults<Favorite> getAllFavorites(Context context)
    {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(context).build();
        Realm realm = Realm.getInstance(realmConfig);

        return realm.allObjects(Favorite.class);
    }
}
