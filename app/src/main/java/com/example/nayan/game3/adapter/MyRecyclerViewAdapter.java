package com.example.nayan.game3.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nayan.game3.Logic;
import com.example.nayan.game3.R;
import com.example.nayan.game3.model.MData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by NAYAN on 8/3/2016.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewholder> {


    ArrayList<MData> arrayList;
    MData mData;
    Context context;
    LayoutInflater inflater;
    Logic logic;



    public MyRecyclerViewAdapter(Context context) {
        this.context = context;
        mData = new MData();


        inflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<MData> arrayList) {
        this.arrayList = arrayList;

            Log.e("log","setdata");
        logic=Logic.getInstance(context);
        logic.callData(arrayList,this);

        notifyDataSetChanged();
    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_image, parent, false);
        MyViewholder viewholder = new MyViewholder(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(MyViewholder holder, int position) {
        mData = arrayList.get(position);
        if (mData.getStatus() == 1) {
            Picasso.with(context)
                    .load(mData.getImage())
                    .into(holder.img);
            //holder.img.setImageResource(mData.getImage());
        } else {
            holder.img.setImageResource(R.drawable.place);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class MyViewholder extends RecyclerView.ViewHolder {
        ImageView img;

        public MyViewholder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("position", "is" + getAdapterPosition());
                    mData = arrayList.get(getAdapterPosition());
                    logic.getSound(R.raw.click);
                    logic.imageClick(mData, getAdapterPosition());
                }
            });
        }


    }

}
