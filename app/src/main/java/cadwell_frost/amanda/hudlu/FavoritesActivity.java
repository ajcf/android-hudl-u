package cadwell_frost.amanda.hudlu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cadwell_frost.amanda.hudlu.models.Favorite;
import cadwell_frost.amanda.hudlu.models.MashableNewsItem;

/**
 * Created by amanda.cadwellfrost on 4/30/16.
 */
public class FavoritesActivity extends AppCompatActivity implements FavoritesRecyclerAdapter.OnAdapterInteractionListener  {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FavoritesRecyclerAdapter mFavoritesAdapter;

    private List<MashableNewsItem> mNewsItems = new ArrayList<MashableNewsItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mFavoritesAdapter = new FavoritesRecyclerAdapter(this, mNewsItems);
        mRecyclerView.setAdapter(mFavoritesAdapter);

        for(Favorite favorite : FavoriteUtil.getAllFavorites(this))
        {
            MashableNewsItem newsItem = new MashableNewsItem();
            newsItem.author = favorite.getAuthor();
            newsItem.feature_image = favorite.getImage();
            newsItem.link = favorite.getLink();
            newsItem.title = favorite.getTitle();

            mNewsItems.add(newsItem);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favorites) {
            Log.d("HudlU", "Settings menu item clicked.");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(View view, int position) {
        Log.d("HudlU", "Favorite item clicked");
        String url = mNewsItems.get(position).link;
        if (url != null)
        {
            Uri webpage = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
        else
        {
            Toast.makeText(this, "Article is invalid", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFavoriteClicked(View view, int position) {
        Log.d("HudlU", "UnfavoriteClicked");
        MashableNewsItem newsItem = mNewsItems.get(position);
        FavoriteUtil.removeFavorite(this, newsItem);
    }
}
