package com.example.nayan.game3.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nayan.game3.Logic;
import com.example.nayan.game3.MLevel;
import com.example.nayan.game3.R;

import java.util.ArrayList;

/**
 * Created by NAYAN on 8/3/2016.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewholder> {


    ArrayList<MLevel> levels;
    MLevel mLevel;
    Context context;
    LayoutInflater inflater;
    Logic logic;


    public MyRecyclerViewAdapter(Context context) {
        this.context = context;
        mLevel = new MLevel();
        levels=new ArrayList<>();


        inflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<MLevel> levels) {
        this.levels = levels;

        Log.e("log", "setdata:"+levels.size());
        logic = Logic.getInstance(context);
        logic.callData(levels, this);

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
        mLevel = levels.get(position);

        holder.img.setImageResource(R.drawable.place);


    }

    @Override
    public int getItemCount() {
        return levels.size();
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
                    mLevel = levels.get(getAdapterPosition());
                    logic.getSound(R.raw.click);
                    logic.imageClick(mLevel, getAdapterPosition());
                }
            });
        }


    }

}
