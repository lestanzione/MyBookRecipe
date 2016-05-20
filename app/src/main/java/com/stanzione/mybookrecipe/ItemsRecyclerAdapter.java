package com.stanzione.mybookrecipe;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stanzione.mybookrecipe.entity.RecipeItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Leandro Stanzione on 19/05/2016.
 */
public class ItemsRecyclerAdapter extends RecyclerView.Adapter<ItemsRecyclerAdapter.ViewHolder> {

    private static final String TAG = ItemsRecyclerAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<RecipeItem> values;

    public ItemsRecyclerAdapter(Context context, ArrayList<RecipeItem> values) {
        this.context = context;
        this.values = values;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, null);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final RecipeItem currentRecipeItem = values.get(position);
        final int recipeItemPosition = position;

        Log.d(TAG, "currentRecipeItem.getName(): " + currentRecipeItem.getName());

        holder.recipeItemPositionTextView.setText(String.valueOf(recipeItemPosition));
        //holder.recipeItemNameTextView.setText(currentRecipeItem.getName());
        holder.recipeItemImageView.setImageResource(currentRecipeItem.getDrawableId());

        //TODO: use longclick ot touch?
        holder.recipeItemRelativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(data, shadowBuilder, v, 0);
                //v.setVisibility(View.INVISIBLE);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != values ? values.size() : 0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout recipeItemRelativeLayout;
        TextView recipeItemPositionTextView;
        ImageView recipeItemImageView;
        //TextView recipeItemNameTextView;

        public ViewHolder(View view) {
            super(view);
            this.recipeItemRelativeLayout = (RelativeLayout) view.findViewById(R.id.recipeItemRelativeLayout);
            this.recipeItemPositionTextView = (TextView) view.findViewById(R.id.recipeItemPositionTextView);
            this.recipeItemImageView = (ImageView) view.findViewById(R.id.recipeItemImageView);
            //this.recipeItemNameTextView = (TextView) view.findViewById(R.id.recipeItemNameTextView);
        }
    }

}