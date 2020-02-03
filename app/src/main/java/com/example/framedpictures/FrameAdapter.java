package com.example.framedpictures;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class FrameAdapter extends ArrayAdapter<Integer> {

    private List<Integer> listFrame;
    private int layoutResource;

    public FrameAdapter(Context context,int resource,List<Integer> list){
        super(context,resource,list);
        this.listFrame = list;
        layoutResource = resource;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(this.layoutResource, null);
        }

        ImageView frame = (ImageView) convertView.findViewById(R.id.frame);
        frame.setImageDrawable(getContext().getResources().getDrawable(listFrame.get(position)));

        return convertView;
    }






}
