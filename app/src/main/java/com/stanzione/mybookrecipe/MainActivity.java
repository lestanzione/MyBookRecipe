package com.stanzione.mybookrecipe;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    RelativeLayout recipeListLayout;
    FloatingActionButton fab;

    RelativeLayout newRecipeLayout;
    RelativeLayout newRecipeNameRelativeLayout;
    RecyclerView newRecipeAllItemsRecyclerView;
    RecyclerView newRecipeSomeItemsRecyclerView;
    Button closeNewRecipeButton;
    ImageView imageView;

    ArrayList<String> allItemsArrayList;
    ArrayList<String> allItems2ArrayList;
    ArrayList<String> someItemsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Book Recipe");

        recipeListLayout = (RelativeLayout) findViewById(R.id.recipeListRelativeLayout);

        newRecipeLayout = (RelativeLayout) findViewById(R.id.newRecipeRelativeLayout);
        newRecipeNameRelativeLayout = (RelativeLayout) findViewById(R.id.newRecipeNameRelativeLayout);
        newRecipeAllItemsRecyclerView = (RecyclerView) findViewById(R.id.newRecipeAllItemsRecyclerView);
        newRecipeSomeItemsRecyclerView = (RecyclerView) findViewById(R.id.newRecipeSomeItemsRecyclerView);
        closeNewRecipeButton = (Button) findViewById(R.id.closeNewRecipeButton);

        imageView = (ImageView) findViewById(R.id.imageView);

        newRecipeLayout.setVisibility(View.INVISIBLE);

        setupElements();

    }

    private void setupElements(){

        allItemsArrayList = new ArrayList<>();
        allItemsArrayList.add("1");
        allItemsArrayList.add("2");
        allItemsArrayList.add("3");

        allItems2ArrayList = allItemsArrayList;

        someItemsArrayList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        newRecipeAllItemsRecyclerView.setLayoutManager(linearLayoutManager);
        newRecipeAllItemsRecyclerView.setAdapter(new ItemsRecyclerAdapter(this, allItems2ArrayList));

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        newRecipeSomeItemsRecyclerView.setLayoutManager(linearLayoutManager2);
        newRecipeSomeItemsRecyclerView.setAdapter(new ItemsRecyclerAdapter(this, someItemsArrayList));

        newRecipeSomeItemsRecyclerView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int action = event.getAction();
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        // do nothing
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        //v.setBackgroundDrawable(enterShape);
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        //v.setBackgroundDrawable(normalShape);
                        break;
                    case DragEvent.ACTION_DROP:
                        // Dropped, reassign View to ViewGroup
                        View view = (View) event.getLocalState();

                        TextView draggedItemPositionTextView = (TextView) view.findViewById(R.id.itemPositionTextView);
                        int itemPosition = Integer.parseInt(draggedItemPositionTextView.getText().toString());



                        Log.d(TAG, "itemPosition: " + itemPosition);

                        someItemsArrayList.add(allItemsArrayList.get(itemPosition));
                        allItems2ArrayList.remove(itemPosition);

                        //ViewGroup owner = (ViewGroup) view.getParent();
                        //owner.removeView(view);
                        //RelativeLayout container = (RelativeLayout) v;
                        //container.addView(view);
                        //view.setVisibility(View.VISIBLE);
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        //v.setBackgroundDrawable(normalShape);
                        newRecipeSomeItemsRecyclerView.getAdapter().notifyDataSetChanged();
                        newRecipeAllItemsRecyclerView.getAdapter().notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideRecipeListLayout();
            }
        });

        closeNewRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideNewRecipeLayout();
            }
        });

        //TODO: use longclick ot touch?
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(data, shadowBuilder, v, 0);
                v.setVisibility(View.INVISIBLE);
                return true;
            }
        });

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                    v.startDrag(data, shadowBuilder, v, 0);
                    v.setVisibility(View.INVISIBLE);
                    return true;
                } else {
                    return false;
                }
            }
        });

        newRecipeNameRelativeLayout.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int action = event.getAction();
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        // do nothing
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        //v.setBackgroundDrawable(enterShape);
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        //v.setBackgroundDrawable(normalShape);
                        break;
                    case DragEvent.ACTION_DROP:
                        // Dropped, reassign View to ViewGroup
                        View view = (View) event.getLocalState();
                        ViewGroup owner = (ViewGroup) view.getParent();
                        owner.removeView(view);
                        RelativeLayout container = (RelativeLayout) v;
                        container.addView(view);
                        view.setVisibility(View.VISIBLE);
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        //v.setBackgroundDrawable(normalShape);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void hideRecipeListLayout(){

        // previously visible view
        final View myView = recipeListLayout;

        // get the center for the clipping circle
        int cx = myView.getWidth();
        int cy = myView.getHeight();

        // get the initial radius for the clipping circle
        float initialRadius = (float) Math.hypot(cx, cy);

        // create the animation (the final radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);

        //anim.setDuration(500);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                myView.setVisibility(View.INVISIBLE);
                showNewRecipeLayout();
            }
        });

        // start the animation
        anim.start();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void showNewRecipeLayout(){

        // previously invisible view
        View myView = newRecipeLayout;

        // get the center for the clipping circle
        int cx = myView.getWidth();
        int cy = myView.getHeight();

        // get the final radius for the clipping circle
        float finalRadius = (float) Math.hypot(cx, cy);

        // create the animator for this view (the start radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);

        // make the view visible and start the animation
        myView.setVisibility(View.VISIBLE);
        anim.start();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void hideNewRecipeLayout(){

        // previously visible view
        final View myView = newRecipeLayout;

        // get the center for the clipping circle
        int cx = myView.getWidth();
        int cy = myView.getHeight();

        // get the initial radius for the clipping circle
        float initialRadius = (float) Math.hypot(cx, cy);

        // create the animation (the final radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);

        //anim.setDuration(500);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                myView.setVisibility(View.INVISIBLE);
                showRecipeListLayout();
            }
        });

        // start the animation
        anim.start();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void showRecipeListLayout(){

        // previously invisible view
        View myView = recipeListLayout;

        // get the center for the clipping circle
        int cx = myView.getWidth();
        int cy = myView.getHeight();

        // get the final radius for the clipping circle
        float finalRadius = (float) Math.hypot(cx, cy);

        // create the animator for this view (the start radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);

        // make the view visible and start the animation
        myView.setVisibility(View.VISIBLE);
        anim.start();

    }

}
