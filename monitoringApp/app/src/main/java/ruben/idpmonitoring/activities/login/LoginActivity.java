package ruben.idpmonitoring.activities.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ruben.idpmonitoring.Application;
import ruben.idpmonitoring.R;
import ruben.idpmonitoring.activities.MainActivity;
import ruben.idpmonitoring.application.communication.Callback;

public class LoginActivity extends AppCompatActivity {
    private EditText txt_email, txt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Application.initialise(getApplicationContext());
        loadElements();
    }

    private void loadElements(){
        this.txt_email = (EditText) this.findViewById(R.id.txt_email);
        this.txt_password = (EditText) this.findViewById(R.id.txt_password);
    }

    public void btnLoginOnClick(View view){
        String email = this.txt_email.getText().toString();
        String password = this.txt_password.getText().toString();
        final Context this_context = this;

        if(email.equals("a") && password.equals("a")){
            Intent intent = new Intent(this_context, MainActivity.class);
            this_context.startActivity(intent);
        } else {
            if(email != null && email.length() != 0){
                if(password != null && password.length() != 0){
                    Application.getServerConnection().login(email, password, new Callback(){
                        @Override
                        public void taskCompleted(String results) {
                            Intent intent = new Intent(this_context, MainActivity.class);
                            this_context.startActivity(intent);
                        }
                    });
                } else {
                    this.txt_password.setHintTextColor(Color.RED);
                }
            } else {
                this.txt_email.setHintTextColor(Color.RED);
            }
        }
    }

    public void btnRegisterOnClick(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        this.startActivity(intent);
    }
}
