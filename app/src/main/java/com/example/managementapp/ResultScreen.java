package com.example.managementapp;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ResultScreen extends AppCompatActivity {
    private TextView title;
    private String survey_title;
    private Results my_survey;
    BarChart barChart;

    private static final String SERVER_URL = "http://" + GetIP.getIPAddress() + ":5000/buildings/get_results?title=%s";

    public static Results fromJson(String json) {
        Gson gson = new Gson();

        // Convert JSON string to a B object
        Results b = gson.fromJson(json, Results.class);

        // Convert the list of A elements from JSON array to List<A>
        Type listType = new TypeToken<ArrayList<Result>>(){}.getType();
        ArrayList<Result> aList = gson.fromJson(gson.toJson(b.getResults()), listType);

        // Set the converted list of A elements in the B object
        b.setResults(aList);

        return b;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_result_screen);
        title = findViewById(R.id.survey_title);
        survey_title = getIntent().getExtras().get("title").toString();
        //title.setText(survey_title);
        get_survey(survey_title);
        barChart = findViewById(R.id.barChart);
    }

    protected void get_survey(String survey_title){
        URL url = null;
        try {
            url = new URL(String.format(SERVER_URL, survey_title));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonResponse = response.body().string();
                    my_survey = fromJson(jsonResponse);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            title.setText(my_survey.getQuestion());
                            List<Result> results = my_survey.getResults();
                            List<BarEntry> entries = new ArrayList<>();
                            List<String> labels = new ArrayList<>();
                            for (int i = 0; i < results.size(); i++) {
                                Result result = results.get(i);
                                float amount = result.getAmount();
                                entries.add(new BarEntry(i, amount));
                                labels.add(result.getAnswer()); // Add result name to the labels list
                            }
                            BarDataSet dataSet = new BarDataSet(entries, "Results");
                            dataSet.setColor(Color.parseColor("#2CA4B3")); // Set the color of the bars
                            dataSet.setValueTextSize(12f); // Set the size of value labels on top of the bars
                            dataSet.setValueTextColor(Color.BLACK); // Set the color of value labels
                            dataSet.setDrawValues(true); // Enable drawing value labels on top of the bars
                            BarData barData = new BarData(dataSet);
                            barData.setBarWidth(0.5f); // Set a smaller width for the bars

                            barChart.setFitBars(true); // Adjust the bar width to fill the available space
                            barChart.getDescription().setEnabled(false); // Hide chart description if not needed
                            // Add more customizations as desired
                            barChart.setData(barData);
                            barChart.setFitBars(true); // Adjust the bar width to fill the available space
                            barChart.getDescription().setEnabled(false); // Hide chart description if not needed
                            barChart.setDrawGridBackground(false); // Disable drawing background grid
                            barChart.setDrawBorders(false); // Disable drawing chart borders
                            barChart.getLegend().setEnabled(false); // Hide legend if not needed
                            barChart.setScaleEnabled(false); // Disable scaling and dragging
                            barChart.setPinchZoom(false); // Disable pinch zooming
                            barChart.setExtraBottomOffset(16f); // Add extra bottom offset for X-axis labels

                            XAxis xAxis = barChart.getXAxis();
                            xAxis.setValueFormatter(new IndexAxisValueFormatter(labels)); // Set custom labels
                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // Position the labels at the bottom
                            xAxis.setGranularity(1f); // Set the label interval to 1 (show every label)
                            xAxis.setDrawAxisLine(false); // Hide X-axis line
                            xAxis.setDrawGridLines(false); // Hide X-axis grid lines
                            xAxis.setTextSize(14f); // Set the size of X-axis labels
                            xAxis.setTextColor(Color.BLACK); // Set the color of X-axis labels



                            YAxis leftAxis = barChart.getAxisLeft();
                            leftAxis.setEnabled(false); // Disable left Y-axis
                            leftAxis.setDrawAxisLine(false); // Hide left Y-axis line
                            leftAxis.setDrawGridLines(false); // Hide left Y-axis grid lines

                            YAxis rightAxis = barChart.getAxisRight();
                            rightAxis.setEnabled(false); // Disable right Y-axis

                            barChart.setData(barData);
                            barChart.invalidate();


                        }
                    });

                } else {
                    // Handle unsuccessful response
                    //onResume();
                }
            }
        });
    }
}