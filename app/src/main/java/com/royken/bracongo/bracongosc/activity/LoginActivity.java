package com.royken.bracongo.bracongosc.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.royken.bracongo.bracongosc.R;

public class LoginActivity extends AppCompatActivity {

    private TextView usernameTvw;
    private TextView passwordTvw;
    private Button loginBtn;
    private String username;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameTvw = (TextView) findViewById(R.id.username);
        passwordTvw = (TextView) findViewById(R.id.password);
        loginBtn = (Button) findViewById(R.id.login);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameTvw.getText().toString();
                password = passwordTvw.getText().toString();
                if(username.trim().equalsIgnoreCase("bracongo") && password.trim().equalsIgnoreCase("Commercial1")){
                    Intent intent = new Intent(getApplicationContext(), LoadCircuitActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Login/Mot de passe incorrecte", Toast.LENGTH_LONG).show();

                }
            }
        });

    }
}
