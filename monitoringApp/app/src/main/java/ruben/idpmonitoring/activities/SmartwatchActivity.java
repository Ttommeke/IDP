package ruben.idpmonitoring.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import ruben.idpmonitoring.Application;
import ruben.idpmonitoring.R;
import ruben.idpmonitoring.application.communication.Callback;

public class SmartwatchActivity extends AppCompatActivity{

    private EditText txt_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smartwatch_test);

        loadElements();
    }

    private void loadElements(){
        this.txt_message = (EditText) this.findViewById(R.id.txt_message);
    }

    public void btnSendMessageOnClick(View view){
        String message = this.txt_message.getText().toString();

        /*Application.getSmartwatchConnection().sendData(new Callback() {
            @Override
            public void taskCompleted(int status_code, String json) {
                Log.d("SmartwatchActivity", json);
            }
        });*/
    }
}
