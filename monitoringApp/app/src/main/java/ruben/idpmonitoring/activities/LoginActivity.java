package ruben.idpmonitoring.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ruben.idpmonitoring.R;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void btnLoginOnClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }

    public void btnRegisterOnClick(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        this.startActivity(intent);
    }
}
