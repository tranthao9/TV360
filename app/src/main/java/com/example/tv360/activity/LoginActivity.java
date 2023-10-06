package com.example.tv360.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tv360.Interface.LoginInterface;
import com.example.tv360.R;
import com.example.tv360.model.DataObjectLogin;
import com.example.tv360.model.DeviceInfo;
import com.example.tv360.model.LoginModel;
import com.example.tv360.presenter.LoginPresenter;
import com.example.tv360.retrofit.ApiService;
import com.example.tv360.retrofit.UserApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity implements LoginInterface {
    EditText username,password;
    Button login_btn;
    SharedPreferences sharedPreferences;


    UserApiService apiInterface;

    private LoginPresenter mloginPresenter;

    private  static  final  String SHARED_PREF_NAME = "mypref";
    private  static  final  String KEY_ACCESSTOKEN ="accessToken";
    private  static  final  String KEY_REFRESHTOKEN = "refreshToken";

    private  static  final  String KEY_USERID = "userId";

    private  static  final  String KEY_PROFILEID = "profileId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getSupportActionBar().hide();
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
        String m_andoid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        DeviceInfo deviceInfo = new DeviceInfo(m_andoid,m_andoid, "WEB_ANDROID", "ANDROID","1.0", "Galaxy Note 10");
        apiInterface = ApiService.getClient().create(UserApiService.class);
            Call<DataObjectLogin> data = apiInterface.login(new LoginModel("0832297222","999999","PASS",null,deviceInfo));
        data.enqueue(new Callback<DataObjectLogin>() {
            @Override
            public void onResponse(Call<DataObjectLogin> call, Response<DataObjectLogin> response) {
                DataObjectLogin dataObjectLogin = response.body();
                mloginPresenter.login(dataObjectLogin);
            }

            @Override
            public void onFailure(Call<DataObjectLogin> call, Throwable t) {
                loginError();
                call.cancel();
            }
        });
    }

    @Override
    public void loginSuccess(String accessToke, String refeshToken, String userID, String profileId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ACCESSTOKEN, accessToke);
        editor.putString(KEY_REFRESHTOKEN, refeshToken);
        editor.putString(KEY_USERID, userID);
        editor.putString(KEY_PROFILEID, profileId);
        editor.apply();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(LoginActivity.this,"Login success",Toast.LENGTH_SHORT).show();
            }
        };
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(runnable,1000);
    }

    @Override
    public void loginError() {
        Toast.makeText(LoginActivity.this,"Login false",Toast.LENGTH_SHORT).show();
    }
}