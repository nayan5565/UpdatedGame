package com.example.nayan.game3.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nayan.game3.R;
import com.example.nayan.game3.activity.SubLevelActivity;
import com.example.nayan.game3.model.MLevel;
import com.example.nayan.game3.utils.Utils;

import java.util.ArrayList;

/**
 * Created by NAYAN on 8/3/2016.
 */
public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.MyViewholder> {


    private ArrayList<MLevel> levels;
    private MLevel mLevel = new MLevel();
    private Context context;
    private LayoutInflater inflater;


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

    public static String getIntToStar(int starCount) {
        String fillStar = "\u2605";
        String blankStar = "\u2606";
        String star = "";

        for (int i = 0; i < starCount; i++) {
            star = star.concat(" " + fillStar);
        }
        for (int j = (3 - starCount); j > 0; j--) {
            star = star.concat(" " + blankStar);
        }

        return star;
    }

    @Override
    public void onBindViewHolder(MyViewholder holder, int position) {
        mLevel = levels.get(position);

        holder.txtLevel.setText(mLevel.getName());
//        holder.txtLevelPoint.setText(" point " + mLevel.getBestpoint());
        holder.txtLevelStar.setText(getIntToStar(0));


        holder.txtLevel.setTextColor(0xffff00ff);
//        if (mLevel.getBestpoint() == 100) {
//            holder.txtLevelStar.setText(getIntToStar(3));
////            holder.imgStar.setImageResource(R.drawable.star);
//        } else if (mLevel.getBestpoint() == 75) {
//            holder.txtLevelStar.setText(getIntToStar(2));
////            holder.imgStar.setImageResource(R.drawable.star2);
//        } else if (mLevel.getBestpoint() == 50) {
//            holder.txtLevelStar.setText(getIntToStar(1));
////            holder.imgStar.setImageResource(R.drawable.star_icon);
//        }


    }

    @Override
    public int getItemCount() {
        return levels.size();
    }


    class MyViewholder extends RecyclerView.ViewHolder {
        TextView txtLevel;
        ImageView imgStar;
        TextView txtLevelPoint;
        TextView txtLevelStar;

        public MyViewholder(final View itemView) {
            super(itemView);
            imgStar = (ImageView) itemView.findViewById(R.id.imgStar);
            txtLevel = (TextView) itemView.findViewById(R.id.txtLevel);
            txtLevelPoint = (TextView) itemView.findViewById(R.id.txtPoint);
            txtLevelStar = (TextView) itemView.findViewById(R.id.txtStar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("position", "is" + getAdapterPosition());
                    mLevel = levels.get(getAdapterPosition());
                    Utils.getSound(context, R.raw.click);

                    Intent intent = new Intent(context, SubLevelActivity.class);
                    intent.putExtra("id", mLevel.getLid());
                    context.startActivity(intent);

                }
            });
        }


    }

}
