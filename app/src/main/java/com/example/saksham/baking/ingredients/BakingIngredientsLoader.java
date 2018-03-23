package com.example.saksham.baking.ingredients;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Saksham on 17-03-2018.
 */

public class BakingIngredientsLoader extends AsyncTaskLoader<List<Ingredients>>

{

    List<Ingredients> mMovieData = null;

    private String murl;
    private int pos;
    BakingIngredientsLoader(Context context, String url,int pos)
    {
        super(context);
        murl=url;
        this.pos=pos;
    }

    @Override
    public List<Ingredients> loadInBackground() {
        if(murl==null)
        {
            return null;
        }
        List<Ingredients> result = NetworkUtilsIngredients.fetchBakingInData(murl,pos);
        return result;
    }

    @Override
    protected void onStartLoading() {
        if (mMovieData != null) {
            deliverResult(mMovieData);
        } else {
            forceLoad();
        }
    }

    public void deliverResult(List<Ingredients> data) {
        mMovieData = data;
        super.deliverResult(data);
    }
}
