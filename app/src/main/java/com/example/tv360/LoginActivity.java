package com.example.tv360;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tv360.Interface.LoginInterface;
import com.example.tv360.MainActivity;
import com.example.tv360.R;
import com.example.tv360.model.UserModel;
import com.example.tv360.presenter.LoginPresenter;

import retrofit2.Retrofit;


public class LoginActivity extends AppCompatActivity implements LoginInterface {
    EditText username,password;
    Button login_btn;
    SharedPreferences sharedPreferences;

    private LoginPresenter mloginPresenter;

    private  static  final  String SHARED_PREF_NAME = "mypref";
    private  static  final  String KEY_NAME = "name";
    private  static  final  String KEY_PASSWORD = "password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_btn = findViewById(R.id.loginbtn);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        mloginPresenter = new LoginPresenter(this);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickLogin();
            }
        });

    }
    public void ClickLogin() {
        String Username = username.getText().toString();
        String Password =   password.getText().toString();
        UserModel userModel = new UserModel(Username,Password);
        mloginPresenter.login(userModel);


    }

    @Override
    public void loginSuccess() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME, username.getText().toString());
        editor.putString(KEY_PASSWORD, password.getText().toString());
        editor.apply();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(LoginActivity.this,"Login success",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginError() {
        Toast.makeText(LoginActivity.this,"Login false",Toast.LENGTH_SHORT).show();
    }
}