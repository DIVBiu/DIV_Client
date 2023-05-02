package com.example.managementapp;

import java.util.List;

public class SurveyItem {
    private String question;
    private List<String> list_of_answers;

    public SurveyItem(String question, List<String> list_of_answers) {
        this.question = question;
        this.list_of_answers = list_of_answers;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getList_of_answers() {
        return list_of_answers;
    }

    public void setList_of_answers(List<String> list_of_answers) {
        this.list_of_answers = list_of_answers;
    }
}
