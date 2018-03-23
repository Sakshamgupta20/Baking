package com.example.saksham.baking;

import android.content.Intent;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.saksham.baking.ingredients.Ingredients_main;
import com.example.saksham.baking.ingredients.IngredientsAll;
import com.example.saksham.baking.steps.BakingStepsAdapter;
import com.example.saksham.baking.steps.Steps;
import com.example.saksham.baking.steps.StepsMain;

import java.util.List;

public class Recipe extends AppCompatActivity implements BakingStepsAdapter.StepsAdapterOnClickHandler{
   static FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipie);

        IngredientFragment InFragment = new IngredientFragment();

        // Add the fragment to its container using a FragmentManager and a Transaction
         fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.ingredient_container, InFragment)
                .commit();


        StepsFragment StFragment=new StepsFragment();


        fragmentManager.beginTransaction()
                .replace(R.id.steps_container, StFragment)
                .commit();



        BakingStepsAdapter.get(this);

        Intent intent = getIntent();
        if (intent.hasExtra("i")) {
            String name= intent.getStringExtra("i");
            getSupportActionBar().setTitle(name);
        }



    }

    public static int flag=0;
public static int pos=0;
    public static List<Steps> steps1;
    @Override
    public void onClick(int ingre, List<Steps> steps) {
        pos = ingre;
        steps1 = steps;
        if(findViewById(R.id.android_me_linear_layouttablet) != null) {
            flag=1;
            RecipeStepsDetails SFragment = new RecipeStepsDetails();
            fragmentManager.beginTransaction()
                    .replace(R.id.ingredient_all, SFragment)
                    .commit();
        }
       // Bundle bundle=new Bundle();
       // bundle.putSerializable("value",(Serializable)steps);
       // bundle.putInt("key",ingre);
        else {
            flag=0;
            Intent intent = new Intent(this, StepsMain.class);
            startActivity(intent);
        }
    }
public void imageclick1(View view)
{
    if(findViewById(R.id.android_me_linear_layouttablet) != null) {

        IngredientsAll SFragment = new IngredientsAll();
        fragmentManager.beginTransaction()
                .replace(R.id.ingredient_all, SFragment)
                .commit();
    }
    else
    {
        Intent intent = new Intent(this, Ingredients_main.class);
        startActivity(intent);
    }
}
    public static int getPos()
    {
        return pos;
    }
    public static int getF()
    {
        return flag;
    }
    public static List<Steps> getList1()
    {
        return steps1;
    }
}