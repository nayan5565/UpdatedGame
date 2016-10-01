package com.example.nayan.game3.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nayan.game3.R;
import com.example.nayan.game3.logic.NLogic;
import com.example.nayan.game3.model.MContents;

import java.util.ArrayList;

/**
 * Created by NAYAN on 10/1/2016.
 */

public class Class3Adapter extends RecyclerView.Adapter<Class3Adapter.MyViewHolder> {
    ArrayList<MContents> textArrayList;

    MContents mContents = new MContents();
    Context context;
    LayoutInflater inflater;
    NLogic nLogic;

    public Class3Adapter(Context context) {
        this.context = context;

        textArrayList = new ArrayList<>();


        inflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<MContents> textArraylist) {
        this.textArrayList = textArraylist;

        Log.e("log", "setdata:" + textArraylist.size());
        nLogic = NLogic.getInstance(context);
        nLogic.callData(textArraylist, this);

        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.image_row, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        mContents = textArrayList.get(position);
        if (mContents.getTxt()==null||mContents.getTxt().equals("")) {
            holder.txtContents.setText(mContents.getSen());
        } else {
            holder.txtContents.setText(mContents.getTxt());
        }
        holder.txtContents.setTextColor(0xffff00ff);
        holder.txtContents.setTextSize(20);
    }

    @Override
    public int getItemCount() {
        return textArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtContents;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtContents = (TextView) itemView.findViewById(R.id.textContents);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContents = textArrayList.get(getAdapterPosition());
//                    nLogic.textClick(mContents);
                    if (mContents.getTxt()==null||mContents.getTxt().equals("")){
                        Toast.makeText(context, mContents.getSen(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(context, mContents.getTxt(), Toast.LENGTH_SHORT).show();
                    }

                    nLogic.imageClick(mContents, getAdapterPosition(), textArrayList.size());
                }
            });
        }
    }
}
