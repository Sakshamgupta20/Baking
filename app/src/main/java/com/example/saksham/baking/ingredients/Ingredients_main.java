
package com.example.saksham.baking.ingredients;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.saksham.baking.R;

public class Ingredients_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingeridients_main);



        FragmentManager fragmentManager = getSupportFragmentManager();

            IngredientsAll InFragment = new IngredientsAll();
            fragmentManager.beginTransaction()
                    .replace(R.id.ingredient_all, InFragment)
                    .commit();




    }

}
