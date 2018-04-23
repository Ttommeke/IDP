package ruben.idpmonitoring.activities.login;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import ruben.idpmonitoring.Application;
import ruben.idpmonitoring.R;
import ruben.idpmonitoring.application.communication.Callback;

public class RegisterActivity extends AppCompatActivity {
    private EditText txt_firstname, txt_lastname, txt_email, txt_password, txt_password_repeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        loadElements();
    }

    private void loadElements(){
        this.txt_firstname = (EditText) this.findViewById(R.id.txt_firstname);
        this.txt_lastname = (EditText) this.findViewById(R.id.txt_lastname);
        this.txt_email = (EditText) this.findViewById(R.id.txt_email);
        this.txt_password = (EditText) this.findViewById(R.id.txt_password);
        this.txt_password_repeat = (EditText) this.findViewById(R.id.txt_password_repeat);
    }

    public void btnRegisterOnClick(View view){
        String firstname = this.txt_firstname.getText().toString();
        String lastname = this.txt_lastname.getText().toString();
        String email = this.txt_email.getText().toString();
        String password = this.txt_password.getText().toString();
        String password_repeat = this.txt_password_repeat.getText().toString();

        if(firstname != null && firstname.length() != 0){
            if(lastname != null && lastname.length() != 0){
                if(email != null && email.length() != 0) {
                    if(password != null && password.length() != 0){
                        if(password_repeat != null && password_repeat.length() != 0){
                            if(password.equals(password_repeat)){
                                Application.getServerConnection().createUser(firstname, lastname, email, password, new Callback() {
                                    @Override
                                    public void taskCompleted(int status_code, String json) {
                                        switch(status_code){
                                            case 0:
                                                Toast.makeText(getApplicationContext(), "Fout bij het maken van verbinding.", Toast.LENGTH_SHORT).show();
                                                break;
                                            case 200:
                                                Toast.makeText(getApplicationContext(), "Account gecreëerd. U kan nu aanmelden.", Toast.LENGTH_SHORT).show();
                                                finish();
                                                break;
                                            case 400:
                                                Toast.makeText(getApplicationContext(), "Email reeds in gebruik of foutief wachtwoord.", Toast.LENGTH_SHORT).show();
                                                break;
                                            default:
                                                Toast.makeText(getApplicationContext(), "Onbekende fout.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(this, "Wachtwoorden komen niet overeen.", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            this.txt_password_repeat.setHintTextColor(Color.RED);
                        }
                    } else {
                        this.txt_password.setHintTextColor(Color.RED);
                    }
                } else {
                    this.txt_email.setHintTextColor(Color.RED);
                }
            } else {
                this.txt_lastname.setHintTextColor(Color.RED);
            }
        } else {
            this.txt_firstname.setHintTextColor(Color.RED);
        }
    }
}
