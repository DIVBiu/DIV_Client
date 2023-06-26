package com.example.managementapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ProblemAdapter extends RecyclerView.Adapter<ProblemAdapter.ViewHolder> {
    Context context;
    private final RecyclerViewInterface recyclerViewInterface;
    private List<Problem> dataList;
    private List<Problem> originalList;
    private CustomFilter filter;
    private TextView textEmpty;
    public static final int VIEW_STATUS = 1;
    public static final int VIEW_UPDATE_PROBLEM = 2;

    public ProblemAdapter(Context context, List<Problem> dataList, RecyclerViewInterface recyclerViewInterface, TextView textEmpty) {
        this.context = context;
        this.dataList = dataList;
        this.originalList = dataList;
        this.recyclerViewInterface = recyclerViewInterface;
        this.textEmpty = textEmpty;
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
        if (getItemCount() == 0) {
            textEmpty.setVisibility(View.VISIBLE);
        } else {
            textEmpty.setVisibility(View.GONE);
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ProblemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.problem_list_item_layout, parent, false);
        return new ProblemAdapter.ViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ProblemAdapter.ViewHolder holder, int position) {
        // Bind data to views in each row
        Problem problem = dataList.get(position);
        holder.problem_description.setText(problem.getDescription());
        holder.problem_type.setText(problem.getType());
        holder.opening_date.setText(problem.getOpening_date());
        holder.problem_status.setText(problem.getStatus());
        holder.problem_creator.setText(problem.getProblem_creator_email());
        // dont have an image yet
        String image = problem.getImage();
        if (!Objects.equals(image, "")){
            byte[] imageBytes = Base64.decode(problem.getImage(), Base64.DEFAULT);
            // Decode the byte array into a Bitmap object
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.problem_image.setImageBitmap(bitmap);
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView problem_description;
        public TextView problem_type;
        public TextView opening_date;
        public TextView problem_status;
        public TextView problem_creator;
        public TextView opening_date_label;
        public TextView status_label;
        public ImageView problem_image;


        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            problem_description = itemView.findViewById(R.id.problemDescription);
            problem_type = itemView.findViewById(R.id.problemType);
            opening_date = itemView.findViewById(R.id.problemOpeningDate);
            problem_status = itemView.findViewById(R.id.problemStatus);
            problem_creator = itemView.findViewById(R.id.problemCreatorEmail);
            problem_image = itemView.findViewById(R.id.problemImage);
            opening_date_label = itemView.findViewById(R.id.openingDateLabel);
            //status_label = itemView.findViewById(R.id.statusLabel);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (recyclerViewInterface != null ){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(position);
                        }
                    }


                }
            });
        }
    }
}