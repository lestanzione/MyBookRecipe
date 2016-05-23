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

import com.stanzione.mybookrecipe.entity.Recipe;
import com.stanzione.mybookrecipe.entity.RecipeItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leandro Stanzione on 19/05/2016.
 */
public class RecipesRecyclerAdapter extends RecyclerView.Adapter<RecipesRecyclerAdapter.ViewHolder> {

    private static final String TAG = RecipesRecyclerAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<Recipe> values;

    public RecipesRecyclerAdapter(Context context, ArrayList<Recipe> values) {
        this.context = context;
        this.values = values;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recipe, null);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Recipe currentRecipe = values.get(position);
        final int recipeItemPosition = position;

        holder.recipeItem1ImageView.setVisibility(View.INVISIBLE);
        holder.recipeItem2ImageView.setVisibility(View.INVISIBLE);
        holder.recipeItem3ImageView.setVisibility(View.INVISIBLE);
        holder.recipeItem4ImageView.setVisibility(View.INVISIBLE);

        holder.recipeNameTextView.setText(currentRecipe.getName());
        holder.recipeServesTextView.setText(String.valueOf(currentRecipe.getPeopleToServe()));
        holder.recipeDurationTextView.setText(currentRecipe.getPrepareDuration());

        List<RecipeItem> recipeItems = currentRecipe.getRecipeItems();

        switch (recipeItems.size()){
            case 0:
                break;
            case 1:
                holder.recipeItem2ImageView.setImageResource(recipeItems.get(0).getDrawableId());
                holder.recipeItem2ImageView.setVisibility(View.VISIBLE);
                break;
            case 2:
                holder.recipeItem2ImageView.setImageResource(recipeItems.get(0).getDrawableId());
                holder.recipeItem4ImageView.setImageResource(recipeItems.get(1).getDrawableId());
                holder.recipeItem2ImageView.setVisibility(View.VISIBLE);
                holder.recipeItem4ImageView.setVisibility(View.VISIBLE);
                break;
            case 3:
                holder.recipeItem1ImageView.setImageResource(recipeItems.get(0).getDrawableId());
                holder.recipeItem2ImageView.setImageResource(recipeItems.get(1).getDrawableId());
                holder.recipeItem4ImageView.setImageResource(recipeItems.get(2).getDrawableId());
                holder.recipeItem1ImageView.setVisibility(View.VISIBLE);
                holder.recipeItem2ImageView.setVisibility(View.VISIBLE);
                holder.recipeItem4ImageView.setVisibility(View.VISIBLE);
                break;
            case 4:
                holder.recipeItem1ImageView.setImageResource(recipeItems.get(0).getDrawableId());
                holder.recipeItem2ImageView.setImageResource(recipeItems.get(1).getDrawableId());
                holder.recipeItem3ImageView.setImageResource(recipeItems.get(2).getDrawableId());
                holder.recipeItem4ImageView.setImageResource(recipeItems.get(3).getDrawableId());
                holder.recipeItem1ImageView.setVisibility(View.VISIBLE);
                holder.recipeItem2ImageView.setVisibility(View.VISIBLE);
                holder.recipeItem3ImageView.setVisibility(View.VISIBLE);
                holder.recipeItem4ImageView.setVisibility(View.VISIBLE);
                break;
            default:
                holder.recipeItem1ImageView.setImageResource(recipeItems.get(0).getDrawableId());
                holder.recipeItem2ImageView.setImageResource(recipeItems.get(1).getDrawableId());
                holder.recipeItem3ImageView.setImageResource(recipeItems.get(2).getDrawableId());
                holder.recipeItem4ImageView.setImageResource(R.drawable.ic_more);
                holder.recipeItem1ImageView.setVisibility(View.VISIBLE);
                holder.recipeItem2ImageView.setVisibility(View.VISIBLE);
                holder.recipeItem3ImageView.setVisibility(View.VISIBLE);
                holder.recipeItem4ImageView.setVisibility(View.VISIBLE);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return (null != values ? values.size() : 0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout recipeRelativeLayout;
        TextView recipeNameTextView;
        TextView recipeServesTextView;
        TextView recipeDurationTextView;
        ImageView recipeItem1ImageView;
        ImageView recipeItem2ImageView;
        ImageView recipeItem3ImageView;
        ImageView recipeItem4ImageView;

        public ViewHolder(View view) {
            super(view);
            this.recipeRelativeLayout = (RelativeLayout) view.findViewById(R.id.recipeRelativeLayout);
            this.recipeNameTextView = (TextView) view.findViewById(R.id.recipeNameTextView);
            this.recipeServesTextView = (TextView) view.findViewById(R.id.recipeServerTextView);
            this.recipeDurationTextView = (TextView) view.findViewById(R.id.recipeDurationTextView);
            this.recipeItem1ImageView = (ImageView) view.findViewById(R.id.recipeItem1ImageView);
            this.recipeItem2ImageView = (ImageView) view.findViewById(R.id.recipeItem2ImageView);
            this.recipeItem3ImageView = (ImageView) view.findViewById(R.id.recipeItem3ImageView);
            this.recipeItem4ImageView = (ImageView) view.findViewById(R.id.recipeItem4ImageView);
        }
    }

}