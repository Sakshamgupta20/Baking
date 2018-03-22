
package com.example.saksham.baking.ingredients;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.saksham.baking.IngredientFragment;
import com.example.saksham.baking.R;
import com.example.saksham.baking.RecipieStepsDetails;

public class Ingeridients_main extends AppCompatActivity {
    int flag=0;
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
