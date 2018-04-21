package ruben.idpmonitoring.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import ruben.idpmonitoring.R;

public class FatigueVsMoodActivity extends AppCompatActivity {
    private LineChart chart_fatigue_mood;
    private ArrayList<Entry> entries_fatigue, entries_mood;
    private LineDataSet fatigue_dataset, mood_dataset;
    private LineData chart_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fatigue_vs_mood);
        loadElements();
        loadDummyData();
        setChartData();
    }

    private void loadElements(){
        this.chart_fatigue_mood = (LineChart) findViewById(R.id.chart_fatigue_mood);
    }

    private void loadDummyData(){
        this.entries_fatigue = new ArrayList<Entry>();
        this.entries_fatigue.add(new Entry(0, 1));
        this.entries_fatigue.add(new Entry(1, 2));
        this.entries_fatigue.add(new Entry(2, 2));
        this.entries_fatigue.add(new Entry(3, 4));
        this.entries_fatigue.add(new Entry(4, 5));
        this.entries_fatigue.add(new Entry(5, 4));
        this.entries_fatigue.add(new Entry(6, 5));
        this.entries_fatigue.add(new Entry(7, 3));
        this.entries_fatigue.add(new Entry(8, 8));
        this.entries_fatigue.add(new Entry(9, 9));
        this.entries_fatigue.add(new Entry(10, 7));
        this.entries_fatigue.add(new Entry(11, 2));
        this.entries_fatigue.add(new Entry(12, 5));
        this.entries_fatigue.add(new Entry(13, 6));

        this.fatigue_dataset = new LineDataSet(this.entries_fatigue, "Vermoeidheid"); // add entries to dataset
        this.fatigue_dataset.setColor(Color.RED);
        this.fatigue_dataset.setLineWidth(2f);
        this.fatigue_dataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        this.fatigue_dataset.setAxisDependency(YAxis.AxisDependency.LEFT);

        this.entries_mood = new ArrayList<Entry>();
        entries_mood.add(new Entry(0, 10));
        entries_mood.add(new Entry(1, 10));
        entries_mood.add(new Entry(2, 9));
        entries_mood.add(new Entry(3, 5));
        entries_mood.add(new Entry(4, 6));
        entries_mood.add(new Entry(5, 8));
        entries_mood.add(new Entry(6, 5));
        entries_mood.add(new Entry(7, 8));
        entries_mood.add(new Entry(8, 4));
        entries_mood.add(new Entry(9, 4));
        entries_mood.add(new Entry(10, 5));
        entries_mood.add(new Entry(11, 8));
        entries_mood.add(new Entry(12, 3));
        entries_mood.add(new Entry(13, 7));

        this.mood_dataset = new LineDataSet(entries_mood, "Stemming");
        this.mood_dataset.setColor(Color.BLUE);
        this.mood_dataset.setLineWidth(2f);
        this.mood_dataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        this.mood_dataset.setAxisDependency(YAxis.AxisDependency.RIGHT);
    }

    private void setChartData(){
        this.chart_data = new LineData(this.fatigue_dataset, this.mood_dataset);
        this.chart_fatigue_mood.getAxisLeft().setAxisMinimum(0);
        this.chart_fatigue_mood.getAxisLeft().setAxisMaximum(10);
        this.chart_fatigue_mood.getAxisRight().setAxisMinimum(0);
        this.chart_fatigue_mood.getAxisRight().setAxisMaximum(10);
        this.chart_fatigue_mood.setData(this.chart_data);
        this.chart_fatigue_mood.getDescription().setText("");
        this.chart_fatigue_mood.invalidate();
    }

    public void btnBackOnClick(View view){
        Intent intent = new Intent();
        this.setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }
}
