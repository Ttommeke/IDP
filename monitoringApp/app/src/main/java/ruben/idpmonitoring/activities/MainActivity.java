package ruben.idpmonitoring.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ruben.idpmonitoring.Application;
import ruben.idpmonitoring.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Application.loadQuestions(); test connection
    }

    public void btnQuestionsOnClick(View view){
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra("optionele", "parameters");
        this.startActivityForResult(intent, 100);
    }

    public void btnOverviewOnClick(View view){
        Intent intent = new Intent(this, OverviewActivity.class);
        this.startActivity(intent);
    }

    public void btnTestConnectionSmartwatchOnClick(View view){
        Intent intent = new Intent(this, SmartwatchActivity.class);
        this.startActivity(intent);
    }

    public void btnCloseOnClick(View view){
        this.finishAffinity();
    }
}
