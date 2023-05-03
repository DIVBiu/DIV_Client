package com.example.managementapp;

import androidx.annotation.Nullable;

import java.util.Date;

public class Problem {
    private String id;
    private String type;
    private String description;
    @Nullable
    private String opening_date;
    private String status;
    @Nullable
    private String treatment_start;

    public Problem(String id, String type, String description, String opening_date, String status, String treatment_start) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.opening_date = opening_date;
        this.status = status;
        this.treatment_start = treatment_start;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOpening_date() {
        return opening_date;
    }

    public void setOpening_date(String opening_date) {
        this.opening_date = opening_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTreatment_start() {
        return treatment_start;
    }

    public void setTreatment_start(String treatment_start) {
        this.treatment_start = treatment_start;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", opening_date=" + opening_date +
                ", status='" + status + '\'' +
                ", treatment_start=" + treatment_start +
                '}';
    }
}
