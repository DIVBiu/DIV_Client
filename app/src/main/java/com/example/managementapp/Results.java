package com.example.managementapp;


import java.util.ArrayList;

public class Results {
    private String question;
    private ArrayList<Result> results;

    public Results(String question, ArrayList<Result> results) {
        this.question = question;
        this.results = results;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }
}
