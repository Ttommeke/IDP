package ruben.idpmonitoring.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import ruben.idpmonitoring.R;

public class StepsVsHeartRateActivity extends AppCompatActivity {
    private CombinedChart chart_steps_heartrate;
    private ArrayList<Entry> entries_heartrate;
    private ArrayList<BarEntry> entries_steps;
    private LineDataSet heartrate_dataset;
    private BarDataSet steps_dataset;
    private LineData heartrate_data;
    private BarData steps_data;
    private CombinedData combined_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_vs_heartrate);
        loadElements();
        loadDummyData();
        setChartData();
    }

    private void loadElements(){
        this.chart_steps_heartrate = (CombinedChart) findViewById(R.id.chart_steps_heartrate);
    }

    private void loadDummyData(){
        this.entries_heartrate = new ArrayList<Entry>();
        this.entries_heartrate.add(new Entry(0, 81));
        this.entries_heartrate.add(new Entry(1, 96));
        this.entries_heartrate.add(new Entry(2, 87));
        this.entries_heartrate.add(new Entry(3, 87));
        this.entries_heartrate.add(new Entry(4, 80));
        this.entries_heartrate.add(new Entry(5, 94));
        this.entries_heartrate.add(new Entry(6, 83));
        this.entries_heartrate.add(new Entry(7, 80));
        this.entries_heartrate.add(new Entry(8, 80));
        this.entries_heartrate.add(new Entry(9, 92));
        this.entries_heartrate.add(new Entry(10, 90));
        this.entries_heartrate.add(new Entry(11, 98));
        this.entries_heartrate.add(new Entry(12, 85));
        this.entries_heartrate.add(new Entry(13, 93));

        this.heartrate_dataset = new LineDataSet(this.entries_heartrate, "Hartslag"); // add entries to dataset
        this.heartrate_dataset.setColor(Color.rgb(240, 238, 70));
        this.heartrate_dataset.setLineWidth(2.5f);
        this.heartrate_dataset.setCircleColor(Color.rgb(240, 238, 70));
        this.heartrate_dataset.setCircleRadius(5f);
        this.heartrate_dataset.setFillColor(Color.rgb(240, 238, 70));
        this.heartrate_dataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        this.heartrate_dataset.setValueTextSize(10f);
        this.heartrate_dataset.setValueTextColor(Color.rgb(240, 238, 70));
        this.heartrate_dataset.setAxisDependency(YAxis.AxisDependency.LEFT);

        this.heartrate_data = new LineData();
        this.heartrate_data.addDataSet(this.heartrate_dataset);

        this.entries_steps = new ArrayList<BarEntry>();
        this.entries_steps.add(new BarEntry(0, 11940));
        this.entries_steps.add(new BarEntry(1, 8485));
        this.entries_steps.add(new BarEntry(2, 11322));
        this.entries_steps.add(new BarEntry(3, 11872));
        this.entries_steps.add(new BarEntry(4, 10822));
        this.entries_steps.add(new BarEntry(5, 7980));
        this.entries_steps.add(new BarEntry(6, 7545));
        this.entries_steps.add(new BarEntry(7, 7964));
        this.entries_steps.add(new BarEntry(8, 6452));
        this.entries_steps.add(new BarEntry(9, 8175));
        this.entries_steps.add(new BarEntry(10, 8298));
        this.entries_steps.add(new BarEntry(11, 10344));
        this.entries_steps.add(new BarEntry(12, 6497));
        this.entries_steps.add(new BarEntry(13, 11104));

        this.steps_dataset = new BarDataSet(this.entries_steps, "Stappen");
        this.steps_dataset.setColor(Color.rgb(60, 220, 78));
        this.steps_dataset.setValueTextColor(Color.rgb(60, 220, 78));
        this.steps_dataset.setValueTextSize(10f);
        this.steps_dataset.setAxisDependency(YAxis.AxisDependency.RIGHT);

        this.steps_data = new BarData();
        this.steps_data.addDataSet(this.steps_dataset);
    }

    private void setChartData(){
        chart_steps_heartrate.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE
        });

        YAxis leftAxis = chart_steps_heartrate.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(50);

        YAxis rightAxis = chart_steps_heartrate.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0);

        this.combined_data = new CombinedData();

        combined_data.setData(this.heartrate_data);
        combined_data.setData(this.steps_data);

        this.chart_steps_heartrate.setData(this.combined_data);
        this.chart_steps_heartrate.getDescription().setText("");
        this.chart_steps_heartrate.invalidate();
    }

    public void btnBackOnClick(View view){
        Intent intent = new Intent();
        this.setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }
}
