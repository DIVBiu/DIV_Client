package com.example.managementapp;

public class Result {
    private String answer;
    private int amount;

    public Result(String answer, int amount) {
        this.answer = answer;
        this.amount = amount;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
