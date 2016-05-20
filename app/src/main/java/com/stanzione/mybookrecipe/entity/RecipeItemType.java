package com.stanzione.mybookrecipe.entity;

import com.stanzione.mybookrecipe.R;

/**
 * Created by lstanzione on 5/20/2016.
 */
public enum RecipeItemType {

    MILK(1, "Milk", R.drawable.ic_milk),
    EGG(2, "Egg", R.drawable.ic_egg),
    SUGAR(3, "Sugar", R.drawable.ic_sugar),
    BREAD(4, "Bread", R.drawable.ic_bread),
    SALT(5, "Salt", R.drawable.ic_salt),
    MEAT(6, "Meat", R.drawable.ic_meat),
    VEGETABLES(7, "Vegetables", R.drawable.ic_vegetables),
    FISH(8, "Fish", R.drawable.ic_fish),
    FRUIT(9, "Fruit", R.drawable.ic_fruit);

    private int id;
    private String name;
    private int drawableId;

    RecipeItemType(int id, String name, int drawableId){
        this.id = id;
        this.name = name;
        this.drawableId = drawableId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDrawableId() {
        return drawableId;
    }

}
