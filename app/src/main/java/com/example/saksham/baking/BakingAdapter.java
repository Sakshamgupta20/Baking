package com.example.saksham.baking;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Saksham on 16-03-2018.
 */

public class BakingAdapter extends RecyclerView.Adapter<BakingAdapter.MyViewHolder>  {

    private Context mContext;
    private List<Bakinga> bakingList;


    private final AdapterOnClickHandler mOnClickListener;

    public interface AdapterOnClickHandler {
        void onClick(int ingre,String rename);
    }

    public BakingAdapter(Context mContext,AdapterOnClickHandler clickHandler) {
        this.mContext = mContext;
        mOnClickListener=clickHandler;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.serving) TextView servings;
        @BindView(R.id.racipename) TextView name;
        @BindView(R.id.recipeimage) ImageView image;
        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int adapterPosition = getAdapterPosition();
            String name=bakingList.get(adapterPosition).getName();
           // ArrayList<Ingredients> ingredients=bakingList.get(adapterPosition).getIngredients();
           mOnClickListener.onClick(adapterPosition,name);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Bakinga bake = bakingList.get(position);
        holder.name.setText(bake.getName());
        holder.servings.setText(bake.getServings());
        String a=bake.getImage();
        if(TextUtils.isEmpty(a))
        {
            Picasso.with(mContext).load(R.drawable.logo).into(holder.image);
        }
        else
        {
            Picasso.with(mContext).load(a).into(holder.image);
        }

    }

    @Override
    public int getItemCount() {
        if (null == bakingList) return 0;
        return bakingList.size();
    }

    public void setBakingData(List<Bakinga> BakingData) {
        bakingList = BakingData;
        notifyDataSetChanged();
    }
    }

