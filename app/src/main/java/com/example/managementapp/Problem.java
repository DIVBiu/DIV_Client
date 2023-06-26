package com.example.managementapp;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import androidx.annotation.Nullable;

import java.util.Date;
import java.util.Objects;

public class Problem {
    private String id;
    private String type;
    private String description;
    @Nullable
    private String opening_date;
    private String status;
    @Nullable
    private String problem_creator_email;
    @Nullable
    private String treatment_start;
    private String image;


    public Problem(String id, String type, String description, @Nullable String opening_date, String status, @Nullable String problem_creator_email, @Nullable String treatment_start, String image) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.opening_date = opening_date;
        this.status = status;
        this.problem_creator_email = problem_creator_email;
        this.treatment_start = treatment_start;
        this.image = image;
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

    @Nullable
    public String getOpening_date() {
        return opening_date;
    }

    public void setOpening_date(@Nullable String opening_date) {
        this.opening_date = opening_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Nullable
    public String getProblem_creator_email() {
        return problem_creator_email;
    }

    public void setProblem_creator_email(@Nullable String problem_creator_email) {
        this.problem_creator_email = problem_creator_email;
    }

    @Nullable
    public String getTreatment_start() {
        return treatment_start;
    }

    public void setTreatment_start(@Nullable String treatment_start) {
        this.treatment_start = treatment_start;
    }

    public String getImage() {
        return image;
    }

//    public static Bitmap convertToBitmap(String text, int textSize) {
//        if (Objects.equals(text, "")) {
//            return null;
//        }
//        // Create a paint object with desired attributes
//        Paint paint = new Paint();
//        paint.setTextSize(textSize);
//        paint.setColor(Color.BLACK);
//        paint.setTypeface(Typeface.DEFAULT);
//        paint.setAntiAlias(true);
//
//        // Calculate the width and height of the text
//        float textWidth = paint.measureText(text);
//        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
//        float textHeight = fontMetrics.bottom - fontMetrics.top;
//
//        // Create a bitmap with width and height based on text size
//        Bitmap bitmap = Bitmap.createBitmap((int) textWidth, (int) textHeight, Bitmap.Config.ARGB_8888);
//
//        // Create a canvas and draw the text on the bitmap
//        Canvas canvas = new Canvas(bitmap);
//        canvas.drawText(text, 0, -fontMetrics.top, paint);
//
//        return bitmap;
//    }
//    public Bitmap convertToBitmap(String text, int textSize) {
//        if (Objects.equals(text, "")){
//            return null;
//        }
//        Paint paint = new Paint();
//        paint.setTextSize(textSize);
//        paint.setColor(Color.BLACK);
//        paint.setAntiAlias(true);
//        paint.setTextAlign(Paint.Align.LEFT);
//
//        Rect bounds = new Rect();
//        paint.getTextBounds(text, 0, text.length(), bounds);
//
//        int width = bounds.width();
//        int height = bounds.height();
//
//        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        canvas.drawText(text, bounds.left, bounds.bottom, paint);
//
//        return bitmap;
//
//    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", opening_date='" + opening_date + '\'' +
                ", status='" + status + '\'' +
                ", problem_creator_email='" + problem_creator_email + '\'' +
                ", treatment_start='" + treatment_start + '\'' +
                ", image=" + image +
                '}';
    }
}
