package com.example.locationshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class LoadShopsAdapter extends BaseAdapter {
    Context context;
    List<DataModel> AllList=new ArrayList<>();

    LayoutInflater layoutInflater;

    public LoadShopsAdapter(Context context, List<DataModel> allList) {
        this.context = context;
        AllList = allList;
        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return AllList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=layoutInflater.inflate(R.layout.custom_loadshops,null);

        ImageView I=view.findViewById(R.id.PlaceImage);
        TextView TN=view.findViewById(R.id.PlaceName);
        TextView TP=view.findViewById(R.id.PlacePrice);

        DataModel dataModel=AllList.get(position);

        TN.setText(""+dataModel.getSPName());
        TP.setText("Price:- "+dataModel.getPrice());
        Glide.with(context).load(""+dataModel.getUrl()).centerCrop().placeholder(R.drawable.btn_bg).into(I);



        return view;
    }
}
