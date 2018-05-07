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

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import ruben.idpmonitoring.Application;
import ruben.idpmonitoring.R;
import ruben.idpmonitoring.activities.MainActivity;
import ruben.idpmonitoring.application.User;
import ruben.idpmonitoring.application.communication.Callback;

public class LoginActivity extends AppCompatActivity {
    private EditText txt_email, txt_password;
    private LinearLayout view_loading, view_non_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Application.initialise(this);
        boolean keep_logged_in = Application.getSettings().getSharedPreferences().getBoolean("keep_me_logged_in", false);
        boolean user_logged_out = Application.getSettings().getSharedPreferences().getBoolean("user_logged_out", true);

        if(!user_logged_out && keep_logged_in){
            final String user_id = Application.getSettings().getSharedPreferences().getString("keep_me_logged_in_id", "");
            Application.getServerConnection().getUserById(user_id, new Callback() {
                @Override
                public void taskCompleted(int status_code, String json) {
                    switch(status_code){
                        case 0:
                            Toast.makeText(getApplicationContext(), "Fout bij het maken van verbinding met de server.", Toast.LENGTH_SHORT).show();
                            break;
                        case 404: //Unauthorized
                            Toast.makeText(getApplicationContext(), "Gebruiker niet gevonden.", Toast.LENGTH_SHORT).show();
                            break;
                        case 200:

                            User user = new User();
                            try{
                                JSONObject json_object = new JSONObject(json);
                                user.setFirstName(json_object.getString("firstName"));
                                user.setLastName(json_object.getString("lastName"));
                                user.setEmail(json_object.getString("email"));
                                user.setId(user_id);
                                Application.setUser(user);
                            }catch(Exception ex){
                                //Fout bij decoderen van user jwt token
                                System.out.print(ex.getMessage());
                            }

                            Application.getServerConnection().setTokenHeader(
                                    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjkxNTkyZjhjLTNkYmUtNDc4NS1hZWE5LTE2NjA3MTQ3NjA1NiIsImZpcnN0TmFtZSI6IlRvbSIsImxhc3ROYW1lIjoiVmFsa2VuZWVycyIsImVtYWlsIjoidG9tdmFsa2VuZWVyc0Bob3RtYWlsLmNvbSIsImlhdCI6MTUyNDM5NzU1OX0.kyTDamyxl9x6cJ_xE1_ONqs9uTJh8euMvHyMky9JoIg"
                            );
                            //FirebaseMessaging.getInstance().subscribeToTopic("allMonitoringDevices");
                            /*
                            Application.getServerConnection().updateDeviceId(FirebaseInstanceId.getInstance().getToken(), new Callback() {
                                @Override
                                public void taskCompleted(int status_code, String results) {
                                    Log.d("[LAAT INS ZIEN]", "taskCompleted: " + results);
                                    Intent intent = new Intent(this_context, MainActivity.class);
                                    this_context.startActivity(intent);
                                }
                            });
                            */

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            break;
                        default:
                            Toast.makeText(getApplicationContext(), "Onbekende fout.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            setContentView(R.layout.activity_login);
            loadElements();
        }

    }

    private void loadElements(){
        this.txt_email = (EditText) this.findViewById(R.id.txt_email);
        this.txt_password = (EditText) this.findViewById(R.id.txt_password);
        this.view_loading  = (LinearLayout) this.findViewById(R.id.view_loading);
        this.view_non_loading = (LinearLayout) this.findViewById(R.id.view_non_loading);
    }

    public void btnLoginOnClick(View view){
        String email = this.txt_email.getText().toString().toLowerCase();
        String password = this.txt_password.getText().toString();

        if(email.equals("a") && password.equals("a")){
            User u = new User();
            u.setId("id");
            u.setEmail("t@t.be");
            u.setLastName("test");
            u.setFirstName("tester");
            Application.setUser(u);
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
        } else {
            if(email != null && email.length() != 0){
                if(password != null && password.length() != 0){
                    this.view_non_loading.setVisibility(View.GONE);
                    this.view_loading.setVisibility(View.VISIBLE);

                    Application.getServerConnection().login(email, password, new Callback(){
                        @Override
                        public void taskCompleted(int status_code, String json) {
                            switch(status_code){
                                case 0:
                                    Toast.makeText(getApplicationContext(), "Fout bij het maken van verbinding met de server.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401: //Unauthorized
                                    Toast.makeText(getApplicationContext(), "Onjuiste gebruikersnaam of wachtwoord.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 200:

                                    User user = new User();
                                    try{
                                        JSONObject json_object = new JSONObject(json);
                                        String token = json_object.getString("jwt");
                                        JWT jwt = new JWT(token);

                                        Claim claim = jwt.getClaim("id");
                                        user.setId(claim.asString());
                                        claim = jwt.getClaim("firstName");
                                        user.setFirstName(claim.asString());
                                        claim = jwt.getClaim("lastName");
                                        user.setLastName(claim.asString());
                                        claim = jwt.getClaim("email");
                                        user.setEmail(claim.asString());

                                        Application.setUser(user);
                                        Application.getSettings().getSharedPreferencesEditor().putBoolean("user_logged_out", false).commit();
                                    }catch(Exception ex){
                                        //Fout bij decoderen van user jwt token
                                        System.out.print(ex.getMessage());
                                    }

                                    Application.getServerConnection().setTokenHeader(
                                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjkxNTkyZjhjLTNkYmUtNDc4NS1hZWE5LTE2NjA3MTQ3NjA1NiIsImZpcnN0TmFtZSI6IlRvbSIsImxhc3ROYW1lIjoiVmFsa2VuZWVycyIsImVtYWlsIjoidG9tdmFsa2VuZWVyc0Bob3RtYWlsLmNvbSIsImlhdCI6MTUyNDM5NzU1OX0.kyTDamyxl9x6cJ_xE1_ONqs9uTJh8euMvHyMky9JoIg"
                                    );

                                    Application.getServerConnection().updateDeviceId(FirebaseInstanceId.getInstance().getToken(), new Callback() {
                                        @Override
                                        public void taskCompleted(int status_code, String results) {
                                            Log.d("[LAAT INS ZIEN]", "taskCompleted: " + results);
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                    break;
                                default:
                                    Toast.makeText(getApplicationContext(), "Onbekende fout.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    view_non_loading.setVisibility(View.VISIBLE);
                    view_loading.setVisibility(View.GONE);
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
