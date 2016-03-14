package cadwell_frost.amanda.hudlu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Amanda Cadwell-Frost on 3/13/2016.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {
    private String[] mDataSet;
    private OnAdapterInteractionListener mListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public MyViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.my_text_view);
        }
    }

    public MyRecyclerAdapter(Context context, String[] dataset)
    {
        mDataSet = dataset;
        mListener = (OnAdapterInteractionListener) context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                               .inflate(R.layout.my_text_view, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.mTextView.setText(mDataSet[position]);
        holder.mTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mListener.onItemClicked(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.length;
    }


    public interface OnAdapterInteractionListener {
        void onItemClicked(View view, int position);
    }

}
