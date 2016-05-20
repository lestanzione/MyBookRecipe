package com.stanzione.mybookrecipe;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by lstanzione on 5/20/2016.
 */
public class RecipeItemsDecorationColumns extends RecyclerView.ItemDecoration {

    private int topSpacing;
    private int bottomSpacing;
    private int leftSpacing;
    private int rightSpacing;

    public RecipeItemsDecorationColumns(int topSpacing, int bottomSpacing, int leftSpacing, int rightSpacing) {
        this.topSpacing = topSpacing;
        this.bottomSpacing = bottomSpacing;
        this.leftSpacing = leftSpacing;
        this.rightSpacing = rightSpacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        outRect.top = topSpacing;
        outRect.bottom = bottomSpacing;
        outRect.left = leftSpacing;
        outRect.right = rightSpacing;

    }

}