package com.example.saksham.baking.steps;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.saksham.baking.BakingAdapter;
import com.example.saksham.baking.R;
import com.example.saksham.baking.ingredients.BakingIngredientsAdapter;
import com.example.saksham.baking.ingredients.Ingredients;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Saksham on 18-03-2018.
 */

public class BakingStepsAdapter extends RecyclerView.Adapter<BakingStepsAdapter.MyViewHolder> implements Serializable{

    private Context mContext;
    private List<Steps> bakingList;

    private static StepsAdapterOnClickHandler mOnClickListener;

    public interface StepsAdapterOnClickHandler {
        void onClick(int ingre, List<Steps> steps);
    }

    public BakingStepsAdapter(Context mContext) {
        this.mContext = mContext;
    }
public static void get(StepsAdapterOnClickHandler on)
{
    mOnClickListener=on;
}

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.recipenumber)
        TextView number;
        @BindView(R.id.recipestep)
        TextView shortdes;
        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            // ArrayList<Ingredients> ingredients=bakingList.get(adapterPosition).getIngredients();
            mOnClickListener.onClick(adapterPosition,bakingList);
        }
    }


    @Override
    public BakingStepsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.steps_view, parent, false);

        return new BakingStepsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BakingStepsAdapter.MyViewHolder holder, int position) {
        Steps bake = bakingList.get(position);
        String stp=bake.getId();
        if(!stp.equals("0"))
        {
            stp="Step "+stp;
            holder.number.setText(stp);
        }
        else
        {
            holder.number.setVisibility(View.GONE);
        }
        holder.shortdes.setText(bake.getShortDescription());

    }

    @Override
    public int getItemCount() {
        if (null == bakingList) return 0;
        return bakingList.size();
    }

    public void setBakingData(List<Steps> BakingData) {
        bakingList = BakingData;
        notifyDataSetChanged();
    }
}
