package com.example.saksham.baking.steps;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.saksham.baking.R;
import com.example.saksham.baking.RecipieStepsDetails;

public class StepsMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingeridients_main);


        FragmentManager fragmentManager = getSupportFragmentManager();

        RecipieStepsDetails StFragment = new RecipieStepsDetails();
            fragmentManager.beginTransaction()
                    .replace(R.id.ingredient_all, StFragment)
                   .commit();
    }
}
