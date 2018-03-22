package com.example.saksham.baking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saksham on 16-03-2018.
 */

public class Bakinga implements Serializable {
    private String id;
    private String name;
    private String servings;
    private String image;


    public Bakinga(String id, String name, String servings, String image) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.image = image;

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }


}
