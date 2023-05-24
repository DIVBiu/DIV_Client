package com.example.managementapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class ProblemAdapter extends RecyclerView.Adapter<ProblemAdapter.ViewHolder> {

    private List<Problem> dataList;
    private List<Problem> originalList;
    private CustomFilter filter;
    private static OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public ProblemAdapter(List<Problem> dataList, OnItemClickListener listener) {
        this.dataList = dataList;
        this.originalList = dataList;
        this.listener = listener;
    }


    @NonNull
    //@Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter(this, originalList);
        }
        return filter;
    }

    public void setData(List<Problem> dataList) {
        this.dataList = dataList;
    }

    public void updateData(List<Problem> newDataList) {
        dataList = newDataList;
        originalList = newDataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_problem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind data to views in each row
        Problem problem = dataList.get(position);
        holder.problem_description.setText(problem.getDescription());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView problem_description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            problem_description = itemView.findViewById(R.id.problem_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });

        }

    }
}