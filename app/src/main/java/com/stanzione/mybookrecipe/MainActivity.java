package com.stanzione.mybookrecipe;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stanzione.mybookrecipe.entity.Recipe;
import com.stanzione.mybookrecipe.entity.RecipeItem;
import com.stanzione.mybookrecipe.entity.RecipeItemType;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    RelativeLayout recipeListLayout;
    RecyclerView recipeRecyclerView;
    FloatingActionButton createNewRecipeButton;

    RelativeLayout newRecipeLayout;
    RelativeLayout newRecipeNameRelativeLayout;
    EditText newRecipeNameEditText;
    RecyclerView newRecipeTemporaryItemsRecyclerView;
    RecyclerView newRecipeItemsRecyclerView;
    Button saveNewRecipeButton;
    Button closeNewRecipeButton;

    ArrayList<Recipe> recipesArrayList;
    ArrayList<RecipeItem> allItemsArrayList;
    ArrayList<RecipeItem> temporaryItemsArrayList;
    ArrayList<RecipeItem> newRecipeItemsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Book Recipe");

        recipeListLayout = (RelativeLayout) findViewById(R.id.recipeListRelativeLayout);
        recipeRecyclerView = (RecyclerView) findViewById(R.id.recipeRecyclerView);
        createNewRecipeButton = (FloatingActionButton) findViewById(R.id.createNewRecipeButton);

        newRecipeLayout = (RelativeLayout) findViewById(R.id.newRecipeRelativeLayout);
        newRecipeNameRelativeLayout = (RelativeLayout) findViewById(R.id.newRecipeNameRelativeLayout);
        newRecipeTemporaryItemsRecyclerView = (RecyclerView) findViewById(R.id.newRecipeTemporaryItemsRecyclerView);
        newRecipeNameEditText = (EditText) findViewById(R.id.newRecipeNameEditText);
        newRecipeItemsRecyclerView = (RecyclerView) findViewById(R.id.newRecipeItemsRecyclerView);
        saveNewRecipeButton = (Button) findViewById(R.id.saveNewRecipeButton);
        closeNewRecipeButton = (Button) findViewById(R.id.closeNewRecipeButton);

        newRecipeLayout.setVisibility(View.INVISIBLE);

        setupElements();

    }

    private void setupElements(){

        recipesArrayList = new ArrayList<>();

        allItemsArrayList = new ArrayList<>();
        allItemsArrayList.add(new RecipeItem(RecipeItemType.MILK));
        allItemsArrayList.add(new RecipeItem(RecipeItemType.EGG));
        allItemsArrayList.add(new RecipeItem(RecipeItemType.SUGAR));
        allItemsArrayList.add(new RecipeItem(RecipeItemType.BREAD));
        allItemsArrayList.add(new RecipeItem(RecipeItemType.SALT));
        allItemsArrayList.add(new RecipeItem(RecipeItemType.MEAT));
        allItemsArrayList.add(new RecipeItem(RecipeItemType.VEGETABLES));
        allItemsArrayList.add(new RecipeItem(RecipeItemType.FISH));
        allItemsArrayList.add(new RecipeItem(RecipeItemType.FRUIT));

        RecipeItemsDecorationColumns recipeItemsDecorationColumns = new RecipeItemsDecorationColumns(8, 8, 8, 8);

        LinearLayoutManager temporaryItemsLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        newRecipeTemporaryItemsRecyclerView.setLayoutManager(temporaryItemsLinearLayoutManager);
        newRecipeTemporaryItemsRecyclerView.addItemDecoration(recipeItemsDecorationColumns);

        LinearLayoutManager allItemsLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        newRecipeItemsRecyclerView.setLayoutManager(allItemsLinearLayoutManager);
        newRecipeItemsRecyclerView.addItemDecoration(recipeItemsDecorationColumns);

        LinearLayoutManager recipesLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recipeRecyclerView.setLayoutManager(recipesLinearLayoutManager);
        recipeRecyclerView.setAdapter(new RecipesRecyclerAdapter(this, recipesArrayList));

        resetNewRecipeFields();

        newRecipeItemsRecyclerView.setOnDragListener(new View.OnDragListener() {
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

                        TextView draggedItemPositionTextView = (TextView) view.findViewById(R.id.recipeItemPositionTextView);
                        int itemPosition = Integer.parseInt(draggedItemPositionTextView.getText().toString());

                        Log.d(TAG, "itemPosition: " + itemPosition);

                        newRecipeItemsArrayList.add(temporaryItemsArrayList.get(itemPosition));
                        temporaryItemsArrayList.remove(itemPosition);

                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        //v.setBackgroundDrawable(normalShape);
                        newRecipeItemsRecyclerView.getAdapter().notifyDataSetChanged();
                        newRecipeTemporaryItemsRecyclerView.getAdapter().notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        newRecipeTemporaryItemsRecyclerView.setOnDragListener(new View.OnDragListener() {
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

                        TextView draggedItemPositionTextView = (TextView) view.findViewById(R.id.recipeItemPositionTextView);
                        int itemPosition = Integer.parseInt(draggedItemPositionTextView.getText().toString());

                        Log.d(TAG, "itemPosition: " + itemPosition);

                        temporaryItemsArrayList.add(newRecipeItemsArrayList.get(itemPosition));
                        newRecipeItemsArrayList.remove(itemPosition);

                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        //v.setBackgroundDrawable(normalShape);
                        newRecipeItemsRecyclerView.getAdapter().notifyDataSetChanged();
                        newRecipeTemporaryItemsRecyclerView.getAdapter().notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });


        createNewRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideRecipeListLayout();
            }
        });

        saveNewRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewRecipe();
            }
        });

        closeNewRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideNewRecipeLayout();
            }
        });

    }

    private void saveNewRecipe(){

        String recipeName = newRecipeNameEditText.getText().toString();

        recipesArrayList.add(new Recipe(1, recipeName, newRecipeItemsArrayList, 0, "0"));
        recipeRecyclerView.getAdapter().notifyDataSetChanged();

        Toast.makeText(this, "Recipe has been created successfully", Toast.LENGTH_SHORT).show();

        hideNewRecipeLayout();
        resetNewRecipeFields();

    }

    private void resetNewRecipeFields(){

        temporaryItemsArrayList = new ArrayList<>();
        for(RecipeItem recipeItem : allItemsArrayList){
            temporaryItemsArrayList.add(recipeItem);
        }

        newRecipeItemsArrayList = new ArrayList<>();

        newRecipeTemporaryItemsRecyclerView.setAdapter(new ItemsRecyclerAdapter(this, temporaryItemsArrayList));
        newRecipeItemsRecyclerView.setAdapter(new ItemsRecyclerAdapter(this, newRecipeItemsArrayList));

        newRecipeNameEditText.setText("");

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
                createNewRecipeButton.setVisibility(View.INVISIBLE);
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
                createNewRecipeButton.setVisibility(View.VISIBLE);
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
