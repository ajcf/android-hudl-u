package cadwell_frost.amanda.hudlu;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cadwell_frost.amanda.hudlu.models.MashableNews;
import cadwell_frost.amanda.hudlu.models.MashableNewsItem;

public class MainActivity extends AppCompatActivity implements MyRecyclerAdapter.OnAdapterInteractionListener {
    private static final String MASHABLE_URL = "http://mashable.com/stories.json?hot_per_page=0&new_per_page=5&rising_per_page=0";

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyRecyclerAdapter myRecyclerAdapter;

    private List<MashableNewsItem> mNewsItems = new ArrayList<MashableNewsItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        myRecyclerAdapter = new MyRecyclerAdapter(this, mNewsItems);
        mRecyclerView.setAdapter(myRecyclerAdapter);

        SharedPreferences prefs = getSharedPreferences("HudlUAndroidPrefs", Context.MODE_PRIVATE);
        Boolean firstRun = prefs.getBoolean("FirstRun", true);
        if (firstRun) {
            SharedPreferences.Editor prefsEditor = prefs.edit();
            prefsEditor.putBoolean("FirstRun", false);
            prefsEditor.apply();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.welcome_message);
            builder.create().show();
        }

        fetchLatestNews();

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
        Snackbar.make(view, mNewsItems.get(position).author, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onFavoriteClicked(View view, int position) {
        MashableNewsItem newsItem = mNewsItems.get(position);
        if (FavoriteUtil.isFavorite(this, newsItem))
        {
            FavoriteUtil.removeFavorite(this, newsItem);
            view.findViewById(R.id.action_favorite).setBackgroundResource(R.color.cardview_light_background);
        }
        else
        {
            FavoriteUtil.addFavorite(this, newsItem);
            view.findViewById(R.id.action_favorite).setBackgroundResource(R.color.orange);
        }

    }

    public void fetchLatestNews(){
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(isConnected)
        {
            final Context context = this;
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            Toast.makeText(this, "Fetching latest news", Toast.LENGTH_SHORT).show();
            StringRequest request = new StringRequest(Request.Method.GET, MASHABLE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        MashableNews newsResponse = new Gson().fromJson(response, MashableNews.class);
                        Log.d("applog", newsResponse.newsItems.get(0).title);
                        mNewsItems.addAll(newsResponse.newsItems);
                        myRecyclerAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error fetching news", Toast.LENGTH_SHORT).show();
                    }
                }
            );
            requestQueue.add(request);
        }
        else
        {
            Toast.makeText(this, "Network Error", Toast.LENGTH_SHORT).show();
        }
    }
}
