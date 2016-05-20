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

        holder.recipeNameTextView.setText(currentRecipe.getName());

    }

    @Override
    public int getItemCount() {
        return (null != values ? values.size() : 0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout recipeRelativeLayout;
        TextView recipeNameTextView;

        public ViewHolder(View view) {
            super(view);
            this.recipeRelativeLayout = (RelativeLayout) view.findViewById(R.id.recipeRelativeLayout);
            this.recipeNameTextView = (TextView) view.findViewById(R.id.recipeNameTextView);
        }
    }

}