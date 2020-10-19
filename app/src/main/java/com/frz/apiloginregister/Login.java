package com.frz.apiloginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Login extends AppCompatActivity {

    TextInputEditText userNameText,passwordText;
    Button login;
    TextView signUpText;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNameText = findViewById(R.id.usernameLogin);
        passwordText = findViewById(R.id.passwordLogin);
        login = findViewById(R.id.buttonLogin);
        signUpText = findViewById(R.id.signUpText);
        progressBar = findViewById(R.id.progress);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String username,password;
                username = String.valueOf(userNameText.getText());

                password = String.valueOf(passwordText.getText());

                if (!username.equals("") && !password.equals("")){


                    //Start ProgressBar first (Set visibility VISIBLE)
                    progressBar.setVisibility(View.VISIBLE);

                    //putData Start;
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "username";
                            field[1] = "password";
                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = username;
                            data[1] = password;
                            //PutData putData = new PutData("http://localhost:8080/api_login/login.php", "POST", field, data);
                            PutData putData = new PutData("http://192.168.0.112:8080/api_login/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    //End ProgressBar (Set visibility to GONE)
                                    progressBar.setVisibility(View.GONE);

                                    String result = putData.getResult();
                                    //Log.i("PutData", result);
                                    if (result.equals("Login Success"))
                                    {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Login.this,MainActivity.class));
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });

                    //putData end;

                }
                else {
                    Toast.makeText(Login.this, "All fields required", Toast.LENGTH_SHORT).show();
                }
            }
        });


        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,SignUp.class));
                finish();
            }
        });


    }
}