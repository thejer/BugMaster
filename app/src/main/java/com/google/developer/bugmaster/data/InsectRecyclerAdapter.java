package com.google.developer.bugmaster.data;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.developer.bugmaster.R;
import com.google.developer.bugmaster.views.DangerLevelView;

/**
 * RecyclerView adapter extended with project-specific required methods.
 */

public class InsectRecyclerAdapter extends
        RecyclerView.Adapter<InsectRecyclerAdapter.InsectHolder> {

    /* ViewHolder for each insect item */
    public class InsectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView commonNameTextView,
                scientificNameTextView;
        DangerLevelView dangerLevelView;

        public InsectHolder(View itemView) {
            super(itemView);
            commonNameTextView = (TextView) itemView.findViewById(R.id.common_name_text);
            scientificNameTextView = (TextView) itemView.findViewById(R.id.scientific_name_text);
            dangerLevelView = (DangerLevelView) itemView.findViewById(R.id.danger_level_view);

        }

        @Override
        public void onClick(View v) {
        }
    }

    private Cursor mCursor;
    private Context mContext;

    public InsectRecyclerAdapter(Cursor cursor, Context context) {
        this.mCursor = cursor;
        this.mContext = context;
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = newCursor;
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
    }


    @Override
    public InsectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.insect_list_item, parent, false);
        return new InsectHolder(view);
    }

    @Override
    public void onBindViewHolder(InsectHolder holder, int position) {
        Insect currentInsect = getItem(position);
        holder.commonNameTextView.setText(currentInsect.name);
        holder.scientificNameTextView.setText(currentInsect.scientificName);
        holder.dangerLevelView.setDangerLevel(currentInsect.dangerLevel);


    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    /**
     * Return the {@link Insect} represented by this item in the adapter.
     *
     * @param position Adapter item position.
     *
     * @return A new {@link Insect} filled with this position's attributes
     *
     * @throws IllegalArgumentException if position is out of the adapter's bounds.
     */
    public Insect getItem(int position) {
        if (position < 0 || position >= getItemCount()) {
            throw new IllegalArgumentException("Item position is out of adapter's range");
        } else if (mCursor.moveToPosition(position)) {
            return new Insect(mCursor);
        }
        return null;
    }
}
