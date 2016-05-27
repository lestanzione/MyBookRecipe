package com.stanzione.mybookrecipe;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.stanzione.mybookrecipe.entity.Recipe;
import com.stanzione.mybookrecipe.entity.RecipeItem;
import com.stanzione.mybookrecipe.entity.RecipeItemType;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    RelativeLayout recipeListLayout;
    TextView recipeEmptyListTextView;
    ImageView recipeEmptyListImageView;
    RecyclerView recipeRecyclerView;
    FloatingActionButton createNewRecipeButton;

    NestedScrollView newRecipeNestedScrollView;
    RelativeLayout newRecipeLayout;
    CardView newRecipeNameCardView;
    EditText newRecipeNameEditText;
    RecyclerView newRecipeTemporaryItemsRecyclerView;
    RecyclerView newRecipeItemsRecyclerView;
    Spinner newRecipeDurationSpinner;
    EditText newRecipeServesEditText;
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
        recipeEmptyListTextView = (TextView) findViewById(R.id.recipeEmptyListTextView);
        recipeEmptyListImageView = (ImageView) findViewById(R.id.recipeEmptyListImageView);
        recipeRecyclerView = (RecyclerView) findViewById(R.id.recipeRecyclerView);
        createNewRecipeButton = (FloatingActionButton) findViewById(R.id.createNewRecipeButton);

        newRecipeNestedScrollView = (NestedScrollView) findViewById(R.id.newRecipeNestedScrollView);
        newRecipeLayout = (RelativeLayout) findViewById(R.id.newRecipeRelativeLayout);
        newRecipeNameCardView = (CardView) findViewById(R.id.newRecipeNameCardView);
        newRecipeTemporaryItemsRecyclerView = (RecyclerView) findViewById(R.id.newRecipeTemporaryItemsRecyclerView);
        newRecipeNameEditText = (EditText) findViewById(R.id.newRecipeNameEditText);
        newRecipeItemsRecyclerView = (RecyclerView) findViewById(R.id.newRecipeItemsRecyclerView);
        newRecipeDurationSpinner = (Spinner) findViewById(R.id.newRecipeDurationSpinner);
        newRecipeServesEditText = (EditText) findViewById(R.id.newRecipeServesEditText);
        saveNewRecipeButton = (Button) findViewById(R.id.saveNewRecipeButton);
        closeNewRecipeButton = (Button) findViewById(R.id.closeNewRecipeButton);

        newRecipeNestedScrollView.setVisibility(View.INVISIBLE);

        setupElements();

    }

    private void setupElements(){

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.recipeDurationArray, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        newRecipeDurationSpinner.setAdapter(adapter);

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
        RecipeDecorationColumns recipeDecorationColumns = new RecipeDecorationColumns(8, 8, 8, 8);

        LinearLayoutManager temporaryItemsLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        newRecipeTemporaryItemsRecyclerView.setLayoutManager(temporaryItemsLinearLayoutManager);
        newRecipeTemporaryItemsRecyclerView.addItemDecoration(recipeItemsDecorationColumns);

        LinearLayoutManager allItemsLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        newRecipeItemsRecyclerView.setLayoutManager(allItemsLinearLayoutManager);
        newRecipeItemsRecyclerView.addItemDecoration(recipeItemsDecorationColumns);

        LinearLayoutManager recipesLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recipeRecyclerView.setLayoutManager(recipesLinearLayoutManager);
        recipeRecyclerView.setAdapter(new RecipesRecyclerAdapter(this, recipesArrayList));
        recipeRecyclerView.addItemDecoration(recipeDecorationColumns);

        recipeListLayout.setVisibility(View.VISIBLE);
        recipeRecyclerView.setVisibility(View.VISIBLE);

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

                        Log.d(TAG, "Dragged to recipe items");

                        ViewGroup owner = (ViewGroup) view.getParent();
                        Log.d(TAG, "" + owner.getId());

                        //if the target recycler view is the same origin recycler view, do nothing
                        if(owner.getId() == R.id.newRecipeItemsRecyclerView){
                            Log.d(TAG, "newRecipeItems");
                            break;
                        }

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

                        Log.d(TAG, "Dragged to temporary items");

                        ViewGroup owner = (ViewGroup) view.getParent();
                        Log.d(TAG, "" + owner.getId());

                        //if the target recycler view is the same origin recycler view, do nothing
                        if(owner.getId() == R.id.newRecipeTemporaryItemsRecyclerView){
                            Log.d(TAG, "newRecipeTemporaryItems");
                            break;
                        }

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
                resetNewRecipeFields();
            }
        });

    }

    private void saveNewRecipe(){

        String recipeDurationArray[] = getResources().getStringArray(R.array.recipeDurationArray);

        String recipeName = newRecipeNameEditText.getText().toString();
        String recipeServes = newRecipeServesEditText.getText().toString();
        String recipeDuration = recipeDurationArray[newRecipeDurationSpinner.getSelectedItemPosition()];

        if(recipeName.trim().isEmpty()){
            Toast.makeText(this, "Please enter a recipe name to save it", Toast.LENGTH_SHORT).show();
            newRecipeNameEditText.requestFocus();
            return;
        }

        if(recipeServes.trim().isEmpty()){
            Toast.makeText(this, "Please enter how many people this recipe serves", Toast.LENGTH_SHORT).show();
            newRecipeServesEditText.requestFocus();
            return;
        }

        recipesArrayList.add(new Recipe(1, recipeName, newRecipeItemsArrayList, Integer.parseInt(recipeServes), recipeDuration));
        recipeRecyclerView.getAdapter().notifyDataSetChanged();

        recipeEmptyListTextView.setVisibility(View.INVISIBLE);
        recipeEmptyListImageView.setVisibility(View.INVISIBLE);

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
        newRecipeServesEditText.setText("");

        newRecipeDurationSpinner.setSelection(0);

    }

    private void hideRecipeListLayout(){

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            hideRecipeListLayoutCircular();
        }
        else{
            hideRecipeListLayoutFade();
        }

    }

    private void showNewRecipeLayout(){

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            showNewRecipeLayoutCircular();
        }
        else{
            showNewRecipeLayoutFade();
        }

    }

    private void hideNewRecipeLayout(){

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            hideNewRecipeLayoutCircular();
        }
        else{
            hideNewRecipeLayoutFade();
        }

    }

    private void showRecipeListLayout(){

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            showRecipeListLayoutCircular();
        }
        else{
            showRecipeListLayoutFade();
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void hideRecipeListLayoutCircular(){

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

    private void hideRecipeListLayoutFade(){

        // previously visible view
        final View myView = recipeListLayout;

        // create the animation (the final radius is zero)
        Animation anim = new AlphaAnimation(1, 0);
        anim.setDuration(500);

        // make the view invisible when the animation is done
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                myView.setVisibility(View.INVISIBLE);
                createNewRecipeButton.setVisibility(View.INVISIBLE);
                showNewRecipeLayout();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        // start the animation
        myView.startAnimation(anim);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void showNewRecipeLayoutCircular(){

        // previously invisible view
        View myView = newRecipeNestedScrollView;

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

    private void showNewRecipeLayoutFade(){

        // previously visible view
        final View myView = newRecipeNestedScrollView;

        // create the animation (the final radius is zero)
        Animation anim = new AlphaAnimation(0, 1);

        anim.setDuration(500);

        // make the view invisible when the animation is done
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                myView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        // start the animation
        myView.startAnimation(anim);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void hideNewRecipeLayoutCircular(){

        // previously visible view
        final View myView = newRecipeNestedScrollView;

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

    private void hideNewRecipeLayoutFade(){

        // previously visible view
        final View myView = newRecipeNestedScrollView;

        // create the animation (the final radius is zero)
        Animation anim = new AlphaAnimation(1, 0);

        anim.setDuration(500);

        // make the view invisible when the animation is done
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                myView.setVisibility(View.INVISIBLE);
                showRecipeListLayout();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        // start the animation
        myView.startAnimation(anim);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void showRecipeListLayoutCircular(){

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

    private void showRecipeListLayoutFade(){

        // previously visible view
        final View myView = recipeListLayout;

        // create the animation (the final radius is zero)
        Animation anim = new AlphaAnimation(0, 1);

        anim.setDuration(500);

        // make the view invisible when the animation is done
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                myView.setVisibility(View.VISIBLE);
                createNewRecipeButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        // start the animation
        myView.startAnimation(anim);

    }

}
