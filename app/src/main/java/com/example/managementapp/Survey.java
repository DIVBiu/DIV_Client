package com.example.managementapp;

public class Survey {
    private String title;
    private String deadline;

    public Survey(String title, String deadline) {
        this.title = title;
        this.deadline = deadline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Survey{" +
                "title='" + title + '\'' +
                ", deadline='" + deadline + '\'' +
                '}';
    }
}
