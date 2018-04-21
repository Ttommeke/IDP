package ruben.idpmonitoring.activities.history;

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

public class HeartrateVsMoodActivity extends AppCompatActivity {
    private LineChart chart_heartrate_mood;
    private ArrayList<Entry> entries_heartrate, entries_mood;
    private LineDataSet heartrate_dataset, mood_dataset;
    private LineData chart_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heartrate_vs_mood);
        loadElements();
        loadDummyData();
        setChartData();
    }

    private void loadElements(){
        this.chart_heartrate_mood = (LineChart) findViewById(R.id.chart_heartrate_mood);
    }

    private void loadDummyData(){
        this.entries_heartrate = new ArrayList<Entry>();
        this.entries_heartrate.add(new Entry(0, 100));
        this.entries_heartrate.add(new Entry(1, 96));
        this.entries_heartrate.add(new Entry(2, 97));
        this.entries_heartrate.add(new Entry(3, 100));
        this.entries_heartrate.add(new Entry(4, 95));
        this.entries_heartrate.add(new Entry(5, 86));
        this.entries_heartrate.add(new Entry(6, 93));
        this.entries_heartrate.add(new Entry(7, 96));
        this.entries_heartrate.add(new Entry(8, 89));
        this.entries_heartrate.add(new Entry(9, 86));
        this.entries_heartrate.add(new Entry(10, 83));
        this.entries_heartrate.add(new Entry(11, 92));
        this.entries_heartrate.add(new Entry(12, 98));
        this.entries_heartrate.add(new Entry(13, 96));
        this.entries_heartrate.add(new Entry(14, 83));
        this.entries_heartrate.add(new Entry(15, 81));
        this.entries_heartrate.add(new Entry(16, 84));
        this.entries_heartrate.add(new Entry(17, 81));
        this.entries_heartrate.add(new Entry(18, 96));
        this.entries_heartrate.add(new Entry(19, 87));
        this.entries_heartrate.add(new Entry(20, 87));
        this.entries_heartrate.add(new Entry(21, 80));
        this.entries_heartrate.add(new Entry(22, 94));
        this.entries_heartrate.add(new Entry(23, 83));
        this.entries_heartrate.add(new Entry(24, 80));
        this.entries_heartrate.add(new Entry(25, 80));
        this.entries_heartrate.add(new Entry(26, 92));
        this.entries_heartrate.add(new Entry(27, 90));
        this.entries_heartrate.add(new Entry(28, 98));
        this.entries_heartrate.add(new Entry(29, 85));
        this.entries_heartrate.add(new Entry(30, 93));

        this.heartrate_dataset = new LineDataSet(this.entries_heartrate, "Hartslag"); // add entries to dataset
        this.heartrate_dataset.setColor(Color.RED);
        this.heartrate_dataset.setDrawValues(false);
        this.heartrate_dataset.setLineWidth(2f);
        this.heartrate_dataset.setDrawCircles(false);
        this.heartrate_dataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        this.heartrate_dataset.setAxisDependency(YAxis.AxisDependency.LEFT);

        this.entries_mood = new ArrayList<Entry>();
        this.entries_mood.add(new Entry(0, 10));
        this.entries_mood.add(new Entry(1, 10));
        this.entries_mood.add(new Entry(2, 9));
        this.entries_mood.add(new Entry(3, 5));
        this.entries_mood.add(new Entry(4, 6));
        this.entries_mood.add(new Entry(5, 8));
        this.entries_mood.add(new Entry(6, 5));
        this.entries_mood.add(new Entry(7, 8));
        this.entries_mood.add(new Entry(8, 4));
        this.entries_mood.add(new Entry(9, 4));
        this.entries_mood.add(new Entry(10, 5));
        this.entries_mood.add(new Entry(11, 8));
        this.entries_mood.add(new Entry(12, 3));
        this.entries_mood.add(new Entry(13, 7));
        this.entries_mood.add(new Entry(14, 3));
        this.entries_mood.add(new Entry(15, 4));
        this.entries_mood.add(new Entry(16, 7));
        this.entries_mood.add(new Entry(17, 5));
        this.entries_mood.add(new Entry(18, 4));
        this.entries_mood.add(new Entry(19, 5));
        this.entries_mood.add(new Entry(20, 6));
        this.entries_mood.add(new Entry(21, 6));
        this.entries_mood.add(new Entry(22, 4));
        this.entries_mood.add(new Entry(23, 8));
        this.entries_mood.add(new Entry(24, 5));
        this.entries_mood.add(new Entry(25, 6));
        this.entries_mood.add(new Entry(26, 3));
        this.entries_mood.add(new Entry(27, 3));
        this.entries_mood.add(new Entry(28, 4));
        this.entries_mood.add(new Entry(29, 7));
        this.entries_mood.add(new Entry(30, 8));

        this.mood_dataset = new LineDataSet(this.entries_mood, "Stemming");
        this.mood_dataset.setColor(Color.BLUE);
        this.mood_dataset.setDrawValues(false);
        this.mood_dataset.setLineWidth(2f);
        this.mood_dataset.setDrawCircles(false);
        this.mood_dataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        this.mood_dataset.setAxisDependency(YAxis.AxisDependency.RIGHT);
    }

    private void setChartData(){
        this.chart_data = new LineData(this.heartrate_dataset, this.mood_dataset);
        this.chart_heartrate_mood.setData(this.chart_data);
        this.chart_heartrate_mood.getDescription().setText("");
        this.chart_heartrate_mood.invalidate();
    }

    public void btnBackOnClick(View view){
        Intent intent = new Intent();
        this.setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }
}
