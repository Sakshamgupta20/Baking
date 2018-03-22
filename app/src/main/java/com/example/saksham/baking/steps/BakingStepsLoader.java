package com.example.saksham.baking.steps;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.saksham.baking.ingredients.Ingredients;
import com.example.saksham.baking.ingredients.NetworkUtilsIncredients;

import java.util.List;

/**
 * Created by Saksham on 18-03-2018.
 */

public class BakingStepsLoader extends AsyncTaskLoader<List<Steps>>

{

    List<Steps> mMovieData = null;

    private String murl;
    private int pos;
    public BakingStepsLoader(Context context, String url, int pos)
    {
        super(context);
        murl=url;
        this.pos=pos;
    }

    @Override
    public List<Steps> loadInBackground() {
        if(murl==null)
        {
            return null;
        }
        List<Steps> result = NetworkUtilsSteps.fetchBakingStData(murl,pos);
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

    public void deliverResult(List<Steps> data) {
        mMovieData = data;
        super.deliverResult(data);
    }
}
