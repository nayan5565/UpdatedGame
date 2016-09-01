package com.example.nayan.game3.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nayan.game3.R;
import com.example.nayan.game3.activity.GameActivity;
import com.example.nayan.game3.model.MLevel;
import com.example.nayan.game3.utils.Utils;

import java.util.ArrayList;

/**
 * Created by NAYAN on 8/3/2016.
 */
public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.MyViewholder> {


    ArrayList<MLevel> levels;
    MLevel mLevel = new MLevel();
    Context context;
    LayoutInflater inflater;



    public LevelAdapter(Context context) {
        this.context = context;

        levels = new ArrayList<>();


        inflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<MLevel> levels) {
        this.levels = levels;

        Log.e("log", "setdata:" + levels.size());
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

        holder.txtLevel.setText("Level " + mLevel.getLevel()+"\n point "+mLevel.getBestpoint());
        holder.txtLevel.setTextColor(0xffff00ff);


    }

    @Override
    public int getItemCount() {
        return levels.size();
    }


    class MyViewholder extends RecyclerView.ViewHolder {
        TextView txtLevel;
//        TextView txtLevelPoint;

        public MyViewholder(final View itemView) {
            super(itemView);
            txtLevel = (TextView) itemView.findViewById(R.id.txtLevel);
//            txtLevelPoint = (TextView) itemView.findViewById(R.id.txtLevelPoint);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("position", "is" + getAdapterPosition());
                    mLevel = levels.get(getAdapterPosition());
                    Utils.getSound(context, R.raw.click);
                    GameActivity.mLevel = mLevel;
                    Intent intent = new Intent(context, GameActivity.class);
                    context.startActivity(intent);

                }
            });
        }


    }

}
