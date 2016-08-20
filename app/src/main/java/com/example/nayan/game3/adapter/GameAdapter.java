package com.example.nayan.game3.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.example.nayan.game3.NLogic;
import com.example.nayan.game3.R;
import com.example.nayan.game3.model.MData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by JEWEL on 8/20/2016.
 */
public class GameAdapter extends RecyclerView.Adapter<GameAdapter.MyViewholder> {
    ArrayList<MData> list;
    MData mData = new MData();
    Context context;
    LayoutInflater inflater;
    NLogic nLogic;
    Animation animation;


    public GameAdapter(Context context) {
        this.context = context;

        list = new ArrayList<>();


        inflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<MData> list) {
        this.list = list;

        Log.e("log", "setdata:" + list.size());
        nLogic = NLogic.getInstance(context);
        nLogic.callData(list, this);

        notifyDataSetChanged();
    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.image_row, parent, false);
        MyViewholder viewholder = new MyViewholder(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(MyViewholder holder, int position) {
        mData = list.get(position);

        if (mData.getStatus() == 1) {
            Picasso.with(context)
                    .load(mData.getImage())
                    .into(holder.img);

        } else {
            holder.img.setImageResource(R.drawable.place);
            //holder.img.setVisibility(View.INVISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewholder extends RecyclerView.ViewHolder {
        ImageView img;

        public MyViewholder(final View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("position", "is" + getAdapterPosition());
                    mData = list.get(getAdapterPosition());
                    nLogic.getSound(R.raw.click);

                    nLogic.imageClick(mData, getAdapterPosition());
                }
            });
        }

        public void imageVanish() {
            img.setVisibility(View.GONE);
        }


    }

}
