package ruben.idpmonitoring.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import java.util.ArrayList;

import ruben.idpmonitoring.R;

public class StepHistoryActivity extends AppCompatActivity {
    private Button btn_dayview, btn_weekview, btn_monthview, btn_allview;
    private BarChart chart_steps;
    private ArrayList<BarEntry> bar_entry;
    private ArrayList<String> bar_entry_labels;
    private BarDataSet bar_dataset;
    private BarData bar_data;
    private int primaryColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_history);
        loadElements();
        loadDummyDataDay();
        setChartData();
    }

    private void loadElements(){
        this.btn_dayview = (Button)this.findViewById(R.id.btn_dayview);
        this.btn_weekview = (Button)this.findViewById(R.id.btn_weekview);
        this.btn_monthview = (Button)this.findViewById(R.id.btn_monthview);
        this.btn_allview = (Button)this.findViewById(R.id.btn_allview);
        this.chart_steps = (BarChart)this.findViewById(R.id.chart_steps);
        this.bar_entry = new ArrayList<BarEntry>();
        this.bar_entry_labels = new ArrayList<String>();

        this.primaryColor = Color.parseColor("#00AA8D"); //Should be set programmatically
    }

    private void setChartData(){
        this.chart_steps.setData(this.bar_data);
        this.chart_steps.getXAxis().setValueFormatter(new IndexAxisValueFormatter(this.bar_entry_labels));
        this.chart_steps.animateY(1000);
        this.chart_steps.getDescription().setText("");
        this.chart_steps.invalidate(); //refresh
    }

    private void loadDummyDataDay(){
        resetChartData();
        this.bar_entry.add(new BarEntry(0, 200));
        this.bar_entry.add(new BarEntry(1, 600));
        this.bar_entry.add(new BarEntry(2, 1500));
        this.bar_entry.add(new BarEntry(3, 900));
        this.bar_entry.add(new BarEntry(4, 2100));
        this.bar_entry.add(new BarEntry(5, 1000));

        this.bar_entry_labels.add("8:00");
        this.bar_entry_labels.add("10:00");
        this.bar_entry_labels.add("12:00");
        this.bar_entry_labels.add("14:00");
        this.bar_entry_labels.add("16:00");
        this.bar_entry_labels.add("18:00");

        this.bar_dataset = new BarDataSet(this.bar_entry, "Stappen");
        this.bar_data = new BarData(this.bar_dataset);

        //this.bar_dataset.setColor(R.attr.colorAccent);
    }

    private void loadDummyDataWeek(){
        this.bar_entry.add(new BarEntry(0, 9520));
        this.bar_entry.add(new BarEntry(1, 8600));
        this.bar_entry.add(new BarEntry(2, 5930));
        this.bar_entry.add(new BarEntry(3, 9874));
        this.bar_entry.add(new BarEntry(4, 11000));
        this.bar_entry.add(new BarEntry(5, 7200));
        this.bar_entry.add(new BarEntry(6, 8745));

        this.bar_entry_labels.add("Ma");
        this.bar_entry_labels.add("Di");
        this.bar_entry_labels.add("Wo");
        this.bar_entry_labels.add("Do");
        this.bar_entry_labels.add("Vr");
        this.bar_entry_labels.add("Za");
        this.bar_entry_labels.add("Zo");

        this.bar_dataset = new BarDataSet(this.bar_entry, "Stappen");
        this.bar_data = new BarData(this.bar_dataset);
    }

    private void loadDummyDataMonth(){
        this.bar_entry.add(new BarEntry(0, 11940));
        this.bar_entry.add(new BarEntry(1, 8485));
        this.bar_entry.add(new BarEntry(2, 11322));
        this.bar_entry.add(new BarEntry(3, 11872));
        this.bar_entry.add(new BarEntry(4, 10822));
        this.bar_entry.add(new BarEntry(5, 7980));
        this.bar_entry.add(new BarEntry(6, 7545));
        this.bar_entry.add(new BarEntry(7, 7964));
        this.bar_entry.add(new BarEntry(8, 6452));
        this.bar_entry.add(new BarEntry(9, 8175));
        this.bar_entry.add(new BarEntry(10, 8298));
        this.bar_entry.add(new BarEntry(11, 10344));
        this.bar_entry.add(new BarEntry(12, 6497));
        this.bar_entry.add(new BarEntry(13, 11104));
        this.bar_entry.add(new BarEntry(14, 9436));
        this.bar_entry.add(new BarEntry(15, 8482));
        this.bar_entry.add(new BarEntry(16, 11146));
        this.bar_entry.add(new BarEntry(17, 9107));
        this.bar_entry.add(new BarEntry(18, 7711));
        this.bar_entry.add(new BarEntry(19, 10105));
        this.bar_entry.add(new BarEntry(20, 10360));
        this.bar_entry.add(new BarEntry(21, 8433));
        this.bar_entry.add(new BarEntry(22, 8672));
        this.bar_entry.add(new BarEntry(23, 5731));
        this.bar_entry.add(new BarEntry(24, 7693));
        this.bar_entry.add(new BarEntry(25, 10651));
        this.bar_entry.add(new BarEntry(26, 8634));
        this.bar_entry.add(new BarEntry(27, 10574));
        this.bar_entry.add(new BarEntry(28, 7691));
        this.bar_entry.add(new BarEntry(29, 7240));
        this.bar_entry.add(new BarEntry(30, 5930));

        this.bar_entry_labels.add("1");
        this.bar_entry_labels.add("2");
        this.bar_entry_labels.add("3");
        this.bar_entry_labels.add("4");
        this.bar_entry_labels.add("5");
        this.bar_entry_labels.add("6");
        this.bar_entry_labels.add("7");
        this.bar_entry_labels.add("8");
        this.bar_entry_labels.add("9");
        this.bar_entry_labels.add("10");
        this.bar_entry_labels.add("11");
        this.bar_entry_labels.add("12");
        this.bar_entry_labels.add("14");
        this.bar_entry_labels.add("15");
        this.bar_entry_labels.add("16");
        this.bar_entry_labels.add("17");
        this.bar_entry_labels.add("18");
        this.bar_entry_labels.add("19");
        this.bar_entry_labels.add("20");
        this.bar_entry_labels.add("21");
        this.bar_entry_labels.add("22");
        this.bar_entry_labels.add("23");
        this.bar_entry_labels.add("24");
        this.bar_entry_labels.add("25");
        this.bar_entry_labels.add("26");
        this.bar_entry_labels.add("27");
        this.bar_entry_labels.add("28");
        this.bar_entry_labels.add("29");
        this.bar_entry_labels.add("30");

        this.bar_dataset = new BarDataSet(this.bar_entry, "Stappen");
        this.bar_data = new BarData(this.bar_dataset);
    }

    private void resetChartData(){
        this.bar_entry = new ArrayList<BarEntry>();
        this.bar_entry_labels = new ArrayList<String>();
        this.chart_steps.clear();
    }

    public void btnToggleDayViewOnClick(View view){
        resetButtons();
        setActiveButton(this.btn_dayview);
        resetChartData();
        loadDummyDataDay();
        setChartData();
    }

    public void btnToggleWeekViewOnClick(View view){
        resetButtons();
        setActiveButton(this.btn_weekview);
        resetChartData();
        loadDummyDataWeek();
        setChartData();
    }

    public void btnToggleMonthViewOnClick(View view){
        resetButtons();
        setActiveButton(this.btn_monthview);
        resetChartData();
        loadDummyDataMonth();
        setChartData();
    }

    public void btnToggleAllViewOnClick(View view){
        resetButtons();
        setActiveButton(this.btn_allview);
    }

    private void resetButtons(){
        this.btn_dayview.setBackgroundColor(Color.WHITE);
        this.btn_dayview.setTextColor(this.primaryColor);

        this.btn_weekview.setBackgroundColor(Color.WHITE);
        this.btn_weekview.setTextColor(this.primaryColor);

        this.btn_monthview.setBackgroundColor(Color.WHITE);
        this.btn_monthview.setTextColor(this.primaryColor);

        this.btn_allview.setBackgroundColor(Color.WHITE);
        this.btn_allview.setTextColor(this.primaryColor);
    }

    private void setActiveButton(Button btn){
        btn.setBackgroundColor(this.primaryColor);
        btn.setTextColor(Color.WHITE);
    }

    public void btnBackOnClick(View view){
        Intent intent = new Intent();
        this.setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }
}
