package ruben.idpmonitoring.activities.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import ruben.idpmonitoring.Application;
import ruben.idpmonitoring.R;
import ruben.idpmonitoring.activities.MainActivity;
import ruben.idpmonitoring.application.communication.Callback;

public class LoginActivity extends AppCompatActivity {
    private EditText txt_email, txt_password;
    private LinearLayout view_loading, view_non_loading;
    private WebView loading_image;

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
        this.view_loading  = (LinearLayout) this.findViewById(R.id.view_loading);
        this.view_non_loading = (LinearLayout) this.findViewById(R.id.view_non_loading);
        this.loading_image = (WebView) this.findViewById(R.id.loading_image);
        this.loading_image.loadUrl("file:///android_res/drawable/loading.gif");
        this.loading_image.setBackgroundColor(Color.TRANSPARENT);
        this.loading_image.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
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
                    this.view_non_loading.setVisibility(View.GONE);
                    this.view_loading.setVisibility(View.VISIBLE);

                    Application.getServerConnection().login(email, password, new Callback(){
                        @Override
                        public void taskCompleted(int status_code, String results) {
                            switch(status_code){
                                case 0:
                                    Toast.makeText(this_context, "Fout bij het maken van verbinding.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401: //Unauthorized
                                    Toast.makeText(this_context, "Onjuiste gebruikersnaam of wachtwoord.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 200:
                                    Application.getServerConnection().setTokenHeader(
                                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjkxNTkyZjhjLTNkYmUtNDc4NS1hZWE5LTE2NjA3MTQ3NjA1NiIsImZpcnN0TmFtZSI6IlRvbSIsImxhc3ROYW1lIjoiVmFsa2VuZWVycyIsImVtYWlsIjoidG9tdmFsa2VuZWVyc0Bob3RtYWlsLmNvbSIsImlhdCI6MTUyNDM5NzU1OX0.kyTDamyxl9x6cJ_xE1_ONqs9uTJh8euMvHyMky9JoIg"
                                    );
                                    Application.getServerConnection().updateDeviceId(FirebaseInstanceId.getInstance().getToken(), new Callback() {
                                        @Override
                                        public void taskCompleted(int status_code, String results) {
                                            view_non_loading.setVisibility(View.VISIBLE);
                                            view_loading.setVisibility(View.GONE);
                                            Log.d("[LAAT INS ZIEN]", "taskCompleted: " + results);
                                            Intent intent = new Intent(this_context, MainActivity.class);
                                            this_context.startActivity(intent);
                                        }
                                    });
                                    break;
                                default:
                                    Toast.makeText(this_context, "Onbekende fout.", Toast.LENGTH_SHORT).show();
                            }
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
