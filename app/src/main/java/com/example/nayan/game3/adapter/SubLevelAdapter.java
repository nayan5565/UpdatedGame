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
import com.example.nayan.game3.activity.Class2Activity;
import com.example.nayan.game3.activity.Class3Activity;
import com.example.nayan.game3.activity.GameActivity;
import com.example.nayan.game3.activity.MathLevel_1Activity;
import com.example.nayan.game3.model.MLevel;
import com.example.nayan.game3.model.MSubLevel;

import java.util.ArrayList;

/**
 * Created by NAYAN on 9/29/2016.
 */

public class SubLevelAdapter extends RecyclerView.Adapter<SubLevelAdapter.MyViewHolder> {
    ArrayList<MSubLevel> mSubLevels;
    MSubLevel mSubLevel = new MSubLevel();
    Context context;
    LayoutInflater inflater;
    int subLevel;

    public SubLevelAdapter(Context context) {
        this.context = context;
        mSubLevels = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<MSubLevel> mSubLevels) {
        this.mSubLevels = mSubLevels;

        Log.e("log", "setdata:" + mSubLevels.size());
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_image, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        mSubLevel = mSubLevels.get(position);
        holder.txtSubLevel.setText(mSubLevel.getName());


    }

    @Override
    public int getItemCount() {
        return mSubLevels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtSubLevel;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtSubLevel = (TextView) itemView.findViewById(R.id.txtLevel);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mSubLevel = mSubLevels.get(getAdapterPosition());
                    if (mSubLevel.getLid() == 1) {
                        Intent intent = new Intent(context, GameActivity.class);
                        context.startActivity(intent);
                    } else if (mSubLevel.getLid() == 2) {
                        Intent intent = new Intent(context, Class2Activity.class);
                        context.startActivity(intent);
                    } else if (mSubLevel.getLid() == 3) {
                        Intent intent = new Intent(context, Class3Activity.class);
                        context.startActivity(intent);
                    }
                    else if (mSubLevel.getLid() == 4) {
                        Intent intent = new Intent(context, MathLevel_1Activity.class);
                        context.startActivity(intent);
                    }


                }
            });
        }
    }


}
