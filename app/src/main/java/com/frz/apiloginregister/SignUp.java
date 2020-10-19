package com.frz.apiloginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class SignUp extends AppCompatActivity {

    TextInputEditText fullNameText,userNameText,emailText,passwordText;
    Button signUp;
    TextView loginText;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fullNameText = findViewById(R.id.fullname);
        userNameText = findViewById(R.id.username);
        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);
        signUp = findViewById(R.id.buttonSignUp);
        loginText = findViewById(R.id.loginText);
        progressBar = findViewById(R.id.progress);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String fullname,username,email,password;
                fullname = String.valueOf(fullNameText.getText());
                username = String.valueOf(userNameText.getText());
                email = String.valueOf(emailText.getText());
                password = String.valueOf(passwordText.getText());

                if (!fullname.equals("") && !username.equals("") && !email.equals("") && !password.equals("")){


                    //Start ProgressBar first (Set visibility VISIBLE)
                    progressBar.setVisibility(View.VISIBLE);

                    //putData Start;
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[4];
                            field[0] = "fullname";
                            field[1] = "username";
                            field[2] = "email";
                            field[3] = "password";
                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = fullname;
                            data[1] = username;
                            data[2] = email;
                            data[3] = password;
                            //PutData putData = new PutData("http://localhost:8080/api_login/signup.php", "POST", field, data);
                            PutData putData = new PutData("http://192.168.0.112:8080/api_login/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    //End ProgressBar (Set visibility to GONE)
                                    progressBar.setVisibility(View.GONE);

                                    String result = putData.getResult();
                                    //Log.i("PutData", result);
                                    if (result.equals("Sign Up Success"))
                                    {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignUp.this,Login.class));
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
                    Toast.makeText(SignUp.this, "All fields required", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this,Login.class));
                finish();
            }
        });

    }

}