package com.example.managementapp;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

public class CustomFilter extends Filter {
    private final ProblemAdapter adapter;
    private final List<Problem> originalList;

    public CustomFilter(ProblemAdapter adapter, List<Problem> originalList) {
        this.adapter = adapter;
        this.originalList = originalList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        List<Problem> filteredList = new ArrayList<>();

        // Implement your filtering logic here
        // You can use the constraint parameter to filter the data based on user input

        // Example filtering based on a specific field in YourModel
        if (constraint.equals("All")) {
            filteredList.addAll(originalList);
        } else {
            for (Problem item : originalList) {
                if (item.getType().equals(constraint.toString())) {
                    filteredList.add(item);
                }
            }
        }

        results.values = filteredList;
        results.count = filteredList.size();
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.setData((List<Problem>) results.values);
        adapter.notifyDataSetChanged();
    }
}
