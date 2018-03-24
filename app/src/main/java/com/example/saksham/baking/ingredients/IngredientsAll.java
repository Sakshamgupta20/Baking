package com.example.saksham.baking.ingredients;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.content.ContentResolver;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.saksham.baking.Data.BakeContract;
import com.example.saksham.baking.InstructionsWidgetProvider;
import com.example.saksham.baking.MainActivity;
import com.example.saksham.baking.MyWidgetRemoteViewsFactory;
import com.example.saksham.baking.R;
import com.example.saksham.baking.steps.BakingStepsAdapter;

import java.util.List;

public class IngredientsAll extends Fragment{
    int Position;
    private BakingIngredientsAdapter mAdapter;
   private RecyclerView recyclerView;
public static List<Ingredients> ingre;
    LinearLayoutManager layoutManager;

    public static int index = -1;
    public static int top = -1;
   public IngredientsAll()
   {
   }
    private static final String REQUEST_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_ingredients_all, container, false);


        recyclerView=(RecyclerView)rootView.findViewById(R.id.increrecy);
        //ingredients=(ArrayList<Ingredients>)bundle.getSerializable("ingre");
        Position= MainActivity.getData();
        Position+=1;

        if(savedInstanceState!=null) {
            index = savedInstanceState.getInt("index");
            top = savedInstanceState.getInt("top");
        }

            layoutManager
                    = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);


            recyclerView.setLayoutManager(layoutManager);


        recyclerView.setHasFixedSize(true);


        mAdapter=new BakingIngredientsAdapter(getContext());

        recyclerView.setAdapter(mAdapter);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(1, null, incredata);


return rootView;
    }


    private LoaderManager.LoaderCallbacks<List<Ingredients>> incredata =new LoaderManager.LoaderCallbacks<List<Ingredients>>() {
        @Override
        public Loader<List<Ingredients>> onCreateLoader(int i, Bundle bundle) {
            return  new BakingIngredientsLoader(getContext(),REQUEST_URL,Position);
        }

        @Override
        public void onLoadFinished(Loader<List<Ingredients>> loader, List<Ingredients> ingredients) {
            if (ingredients != null && ingredients.size() > 0) {
                mAdapter.setBakingData(ingredients);
                ingre=ingredients;

                getActivity().getContentResolver().delete(BakeContract.BakingEntry.CONTENTURI,null,null);
                for (int i=0;i<ingredients.size();i++)
                {
                    Ingredients bake = ingredients.get(i);
                    ContentValues values = new ContentValues();
                    values.put(BakeContract.BakingEntry.COLUMN_INGREDIENT_NAME, bake.getIngredient());
                    getActivity().getContentResolver().insert(BakeContract.BakingEntry.CONTENTURI, values);
                }
                InstructionsWidgetProvider.sendRefreshBroadcast(getContext());
                if(index != -1)
                {
                    layoutManager.scrollToPositionWithOffset( index, top);
                }
            }
        }

        @Override
        public void onLoaderReset(Loader<List<Ingredients>> loader) {
            mAdapter.clear();
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        index =layoutManager.findFirstVisibleItemPosition();
        View v = recyclerView.getChildAt(0);
        top = (v == null) ? 0 : (v.getTop() - recyclerView.getPaddingTop());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("index",index);
        outState.putInt("top",top);
    }


}