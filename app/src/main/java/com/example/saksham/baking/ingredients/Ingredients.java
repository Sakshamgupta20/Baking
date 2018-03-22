package com.example.saksham.baking.ingredients;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Saksham on 16-03-2018.
 */

public class Ingredients implements Serializable {
    private String quantity;
    private String measure;
    private String ingredient;

    public Ingredients(String quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }
}
