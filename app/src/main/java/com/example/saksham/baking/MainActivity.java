package com.example.saksham.baking;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Bakinga>>,BakingAdapter.AdapterOnClickHandler{


static  int  pos=9999;
    private BakingAdapter mAdapter;
    @BindView(R.id.recard) RecyclerView recyclerView;
    @BindView(R.id.tv_error_message_display) TextView mErrorMessageDisplay;
    @BindView(R.id.pb_loading_indicator) ProgressBar mLoadingIndicator;

private boolean tabletlinear=false;
    private static final String REQUEST_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        if(findViewById(R.id.tabletlinear)!=null)
        {

            tabletlinear=true;
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
            recyclerView.setLayoutManager(gridLayoutManager);

        }
        else
        {
            tabletlinear=false;
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

            recyclerView.setLayoutManager(layoutManager);
        }

        recyclerView.setHasFixedSize(true);


        mAdapter=new BakingAdapter(this,this);

        recyclerView.setAdapter(mAdapter);

        loader();

    }


    private void loader()
    {
        showWeatherDataView();

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(1, null, this);

        }
        else
        {
            showErrorMessage();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }
    }

    private void showWeatherDataView() {

        mErrorMessageDisplay.setVisibility(View.INVISIBLE);

        recyclerView.setVisibility(View.VISIBLE);
    }


    private void showErrorMessage() {

        recyclerView.setVisibility(View.INVISIBLE);

        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public Loader<List<Bakinga>> onCreateLoader(int i, Bundle bundle) {
        return new BakingLoader(this,REQUEST_URL );
    }

    @Override
    public void onLoadFinished(Loader<List<Bakinga>> loader, List<Bakinga> bakingas) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (bakingas != null && bakingas.size()>0) {
            showWeatherDataView();
            mAdapter.setBakingData(bakingas);
        }
        else
        {
            showErrorMessage();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Bakinga>> loader) {
    }
    @Override
    public void onClick(int ingre,String name) {

        Intent intent=new Intent(this,Recipe.class);
        intent.putExtra("i",name);
        pos=ingre;
        startActivity(intent);
    }

    public static int getData()
    {
        return pos;
    }
}
