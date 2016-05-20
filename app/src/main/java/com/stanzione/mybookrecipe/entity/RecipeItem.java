package com.stanzione.mybookrecipe.entity;

/**
 * Created by lstanzione on 5/20/2016.
 */
public class RecipeItem {

    private int id;
    private String name;
    private String type;
    private int drawableId;

    public RecipeItem(int id, String name, String type, int drawableId){
        this.id = id;
        this.name = name;
        this.type = type;
        this.drawableId = drawableId;
    }

    public RecipeItem(RecipeItemType recipeItemType){
        this(recipeItemType.getId(), recipeItemType.getName(), recipeItemType.toString(), recipeItemType.getDrawableId());
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

}
