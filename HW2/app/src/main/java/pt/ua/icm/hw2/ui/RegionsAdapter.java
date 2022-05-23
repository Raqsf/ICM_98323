package pt.ua.icm.hw2.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

import pt.ua.icm.hw2.R;
import pt.ua.icm.hw2.model.Region;

public class RegionsAdapter extends
        RecyclerView.Adapter<RegionsAdapter.RegionsViewHolder> {

    private List<String> regions;
    private Context context;
    private LayoutInflater mInflater;

    public RegionsAdapter(Context context, List<String> regions) {
        this.context = context;
        this.regions = regions;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RegionsAdapter.RegionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.region_item,
                parent, false);
        return new RegionsViewHolder(mItemView, this);

    }

    @Override
    public void onBindViewHolder(@NonNull RegionsAdapter.RegionsViewHolder holder, int position) {
        String mCurrent = regions.get(position);
        holder.wordItemView.setText(mCurrent/*.getLocal()*/);

    }

    @Override
    public int getItemCount() {
        return regions.size();
    }

    class RegionsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView wordItemView;
        final RegionsAdapter mAdapter;

        public RegionsViewHolder(View itemView, RegionsAdapter adapter) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.region);
            this.mAdapter = adapter;
            //itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }

        /*@Override
        public void onClick(View view) {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();
            // Use that to access the affected item in mWordList.
            String element = mWordList.get(mPosition);
            // Change the word in the mWordList.
            mWordList.set(mPosition, "Clicked! " + element);
            // Notify the adapter that the data has changed so it can
            // update the RecyclerView to display the data.
            mAdapter.notifyDataSetChanged();
        }*/
    }

}
