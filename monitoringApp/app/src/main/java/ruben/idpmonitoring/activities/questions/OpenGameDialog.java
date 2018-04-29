package ruben.idpmonitoring.activities.questions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import ruben.idpmonitoring.R;

public class OpenGameDialog extends AppCompatActivity {

    private TextView txt_information;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_open_game);
        loadElements();

        Intent intent = this.getIntent();

        this.txt_information.setText(intent.getStringExtra("information"));
        this.setTitle("");
    }

    private void loadElements(){
        this.txt_information = (TextView) this.findViewById(R.id.txt_information);
    }

    public void btnOpenGameOnClick(View view){
        setResult(Activity.RESULT_OK);
        finish();
    }

    public void btnCloseOnClick(View view){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
