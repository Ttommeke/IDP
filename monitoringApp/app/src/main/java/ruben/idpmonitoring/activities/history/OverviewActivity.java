package ruben.idpmonitoring.activities.history;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ruben.idpmonitoring.R;

public class OverviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
    }

    public void btnStepHistoryOnClick(View view){
        Intent intent = new Intent(this, StepHistoryActivity.class);
        this.startActivity(intent);
    }

    public void btnStepsVsHeartrateOnClick(View view){
        Intent intent = new Intent(this, StepsVsHeartRateActivity.class);
        this.startActivity(intent);
    }

    public void btnHeartrateVsMoodOnClick(View view){
        Intent intent = new Intent(this, HeartrateVsMoodActivity.class);
        this.startActivity(intent);
    }

    public void btnFatigueVsMoodOnClick(View view){
        Intent intent = new Intent(this, FatigueVsMoodActivity.class);
        this.startActivity(intent);
    }

    public void btnBackOnClick(View view){
        Intent intent = new Intent();
        this.setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }
}
