package com.example.saksham.baking;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.saksham.baking.steps.BakingStepsAdapter;


/**
 * Created by Saksham on 17-03-2018.
 */

public class IngredientFragment extends Fragment {

    int Position;
    private BakingStepsAdapter mAdapter;
    private RecyclerView recyclerView;

    private static final String REQUEST_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public IngredientFragment() {
    }

    /**
     * Inflates the fragment layout file and sets the correct resource for the image to display
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fradment_ingredient, container, false);

        return rootView;

    }

}
