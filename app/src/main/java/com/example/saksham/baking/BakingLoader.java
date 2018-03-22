package com.example.saksham.baking;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Saksham on 16-03-2018.
 */

public class BakingLoader extends AsyncTaskLoader<List<Bakinga>>

    {

        List<Bakinga> mMovieData = null;

        private String murl;

        BakingLoader(Context context, String url)
        {
            super(context);
            murl=url;
        }

        @Override
        public List<Bakinga> loadInBackground() {
        if(murl==null)
        {
            return null;
        }
        List<Bakinga> result = NetworkUtils.fetchBakingData(murl);
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

    public void deliverResult(List<Bakinga> data) {
        mMovieData = data;
        super.deliverResult(data);
    }
}
