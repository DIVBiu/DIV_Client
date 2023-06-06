package com.example.managementapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BuildingAdapter extends ArrayAdapter<String> {
    public BuildingAdapter(@NonNull Context context, ArrayList<String> dataArrayList) {
        super(context, R.layout.item_building, dataArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        String listData = getItem(position);

        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_building, parent, false);
        }

        TextView listName = view.findViewById(R.id.listName);
        listName.setText(listData);
        return view;
    }
}