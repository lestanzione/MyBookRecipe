package com.stanzione.mybookrecipe.entity;

import java.util.List;

/**
 * Created by lstanzione on 5/20/2016.
 */
public class Recipe {

    private int id;
    private String name;
    private List<RecipeItem> recipeItems;
    private int peopleToServe;
    private String prepareDuration;

    public Recipe(int id, String name, List<RecipeItem> recipeItems, int peopleToServe, String prepareDuration){
        this.id = id;
        this.name = name;
        this.recipeItems = recipeItems;
        this.setPeopleToServe(peopleToServe);
        this.setPrepareDuration(prepareDuration);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RecipeItem> getRecipeItems() {
        return recipeItems;
    }

    public void setRecipeItems(List<RecipeItem> recipeItems) {
        this.recipeItems = recipeItems;
    }

    public int getPeopleToServe() {
        return peopleToServe;
    }

    public void setPeopleToServe(int peopleToServe) {
        this.peopleToServe = peopleToServe;
    }

    public String getPrepareDuration() {
        return prepareDuration;
    }

    public void setPrepareDuration(String prepareDuration) {
        this.prepareDuration = prepareDuration;
    }

}
