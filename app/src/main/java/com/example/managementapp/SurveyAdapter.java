package com.example.managementapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SurveyAdapter extends ArrayAdapter<Survey> {
    public SurveyAdapter(Context context, List<Survey> surveys){
        super(context,R.layout.item_survey,surveys);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Survey survey = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_survey, parent ,false);
        }
        TextView title = convertView.findViewById(R.id.Surveytitle);
        TextView deadline = convertView.findViewById(R.id.Surveydeadline);
        title.setText(survey.getTitle());
        deadline.setText(survey.getDeadline());
        return convertView;
    }

}
