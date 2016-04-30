package cadwell_frost.amanda.hudlu;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

import cadwell_frost.amanda.hudlu.models.MashableNewsItem;

/**
 * Created by amanda.cadwellfrost on 4/30/16.
 */
public class FavoritesRecyclerAdapter extends RecyclerView.Adapter<FavoritesRecyclerAdapter.MyViewHolder> {

    private List<MashableNewsItem> mNewsItems;
    private OnAdapterInteractionListener mListener;
    private RequestQueue mRequestQueue;
    private Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mItemTitle;
        public TextView mAuthorView;
        public ImageView mImageView;
        public Button mFavoriteButton;
        public MyViewHolder(View view) {
            super(view);
            mItemTitle = (TextView) view.findViewById(R.id.item_title);
            mAuthorView = (TextView) view.findViewById(R.id.item_author);
            mImageView = (ImageView) view.findViewById(R.id.item_image);
            mFavoriteButton = (Button) view.findViewById(R.id.action_favorite);
        }
    }

    public FavoritesRecyclerAdapter(Context context, List<MashableNewsItem> newsItems)
    {
        mNewsItems = newsItems;
        mListener = (OnAdapterInteractionListener) context;
        mRequestQueue = Volley.newRequestQueue(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        MashableNewsItem newsItem = mNewsItems.get(position);
        holder.mItemTitle.setText(newsItem.title);
        holder.mAuthorView.setText(newsItem.author);
        holder.mFavoriteButton.setBackgroundResource(R.color.orange);
        holder.mItemTitle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int currentPosition = holder.getLayoutPosition();
                mListener.onItemClicked(v, currentPosition);
            }
        });
        holder.mFavoriteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int currentPosition = holder.getLayoutPosition();
                mListener.onFavoriteClicked(v, currentPosition);
                mNewsItems.remove(currentPosition);
                notifyItemRemoved(currentPosition);
            }
        });
        ImageRequest request = new ImageRequest(newsItem.feature_image,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        holder.mImageView.setImageBitmap(response);
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.ALPHA_8,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("applog", "Unable to fetch image for content");
                    }
                }
        );
        mRequestQueue.add(request);
    }

    @Override
    public int getItemCount() {
        return mNewsItems.size();
    }


    public interface OnAdapterInteractionListener {
        void onItemClicked(View view, int position);
        void onFavoriteClicked(View view, int position);
    }

}
