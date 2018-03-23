package com.example.saksham.baking.ingredients;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saksham.baking.Data.BakeContract;
import com.example.saksham.baking.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Saksham on 17-03-2018.
 */

public class BakingIngredientsAdapter extends RecyclerView.Adapter<BakingIngredientsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Ingredients> bakingList;


    public BakingIngredientsAdapter(Context mContext) {
        this.mContext = mContext;

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ingertext)
        TextView ingerident;
        @BindView(R.id.quantity)
        TextView quantity;
        @BindView(R.id.measure)
        TextView measure;
        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }


    @Override
    public BakingIngredientsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipieview, parent, false);

        return new BakingIngredientsAdapter.MyViewHolder(itemView);
    }
    private Uri newUri;
    @Override
    public void onBindViewHolder(BakingIngredientsAdapter.MyViewHolder holder, int position) {
        Ingredients bake = bakingList.get(position);
        holder.ingerident.setText(bake.getIngredient());
        holder.quantity.setText(bake.getQuantity());
        holder.measure.setText(bake.getMeasure());




        }


    @Override
    public int getItemCount() {
        if (null == bakingList) return 0;
        return bakingList.size();
    }

    public void setBakingData(List<Ingredients> BakingData) {
        bakingList = BakingData;
        notifyDataSetChanged();
    }

    public void  clear() {
        final int size = bakingList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                bakingList.remove(0);
            }
            notifyItemRangeRemoved(0, size);
        }
    }
}


