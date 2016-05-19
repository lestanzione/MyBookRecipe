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

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Leandro Stanzione on 19/05/2016.
 */
public class ItemsRecyclerAdapter extends RecyclerView.Adapter<ItemsRecyclerAdapter.ViewHolder> {

    public interface OnContinentListener{
        void onContinentSelected(int position);
    }

    private static final String TAG = ItemsRecyclerAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<String> values;
    private WeakReference<OnContinentListener> activity;

    public ItemsRecyclerAdapter(Context context, ArrayList<String> values) {
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

        final String currentItem = values.get(position);
        final int itemPosition = position;

        holder.itemPositionTextView.setText(String.valueOf(itemPosition));

        holder.itemRelativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, final MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                    v.startDrag(data, shadowBuilder, v, 0);
                    //v.setVisibility(View.INVISIBLE);
                    return true;
                } else {
                    return false;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != values ? values.size() : 0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout itemRelativeLayout;
        ImageView itemImageView;
        TextView itemPositionTextView;

        public ViewHolder(View view) {
            super(view);
            this.itemRelativeLayout = (RelativeLayout) view.findViewById(R.id.itemRelativeLayout);
            this.itemImageView = (ImageView) view.findViewById(R.id.itemImageView);
            this.itemPositionTextView = (TextView) view.findViewById(R.id.itemPositionTextView);
        }
    }

}