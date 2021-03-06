package com.example.saksham.baking;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.saksham.baking.steps.BakingStepsAdapter;
import com.example.saksham.baking.steps.BakingStepsLoader;
import com.example.saksham.baking.steps.Steps;

import java.util.List;

/**
 * Created by Saksham on 18-03-2018.
 */

public class StepsFragment extends Fragment  {
    int Position=0;
    private BakingStepsAdapter mAdapter;
    private RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    public static int index = -1;
    public static int top = -1;
    private static final String REQUEST_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public StepsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);

         layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclersteps);

        Position= MainActivity.getData();
        Position+=1;

        if(savedInstanceState!=null) {
            index = savedInstanceState.getInt("index");
            top = savedInstanceState.getInt("top");
        }
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setHasFixedSize(true);


        mAdapter = new BakingStepsAdapter(getContext());

        recyclerView.setAdapter(mAdapter);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(2, null, incredata);

        return rootView;

    }

    private LoaderManager.LoaderCallbacks<List<Steps>> incredata = new LoaderManager.LoaderCallbacks<List<Steps>>() {

        @Override
        public Loader<List<Steps>> onCreateLoader(int i, Bundle bundle) {
            return new BakingStepsLoader(getContext(), REQUEST_URL, Position);
        }

        @Override
        public void onLoadFinished(Loader<List<Steps>> loader, List<Steps> steps) {
            if (steps != null && steps.size() > 0) {
                mAdapter.setBakingData(steps);
            }
            if(index != -1)
            {
                layoutManager.scrollToPositionWithOffset( index, top);
            }
        }

        @Override
        public void onLoaderReset(Loader<List<Steps>> loader) {

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
