package ruben.idpmonitoring.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ruben.idpmonitoring.Application;
import ruben.idpmonitoring.R;
import ruben.idpmonitoring.activities.history.OverviewActivity;
import ruben.idpmonitoring.activities.questions.QuestionActivity;
import ruben.idpmonitoring.activities.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Application.checkUserLoggedIn();
    }

    public void btnQuestionsOnClick(View view){
        Intent intent = new Intent(this, QuestionActivity.class);
        this.startActivity(intent);
    }

    public void btnOverviewOnClick(View view){
        Intent intent = new Intent(this, OverviewActivity.class);
        this.startActivity(intent);
    }

    public void btnSettingsOnClick(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        this.startActivity(intent);
    }

    public void btnTestConnectionSmartwatchOnClick(View view){
        Intent intent = new Intent(this, SmartwatchActivity.class);
        this.startActivity(intent);
    }

    public void btnLogoutOnClick(View view){
        Application.logout();
    }

}
