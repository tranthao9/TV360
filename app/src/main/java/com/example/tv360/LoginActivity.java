package com.example.tv360;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tv360.Interface.LoginInterface;
import com.example.tv360.MainActivity;
import com.example.tv360.R;
import com.example.tv360.model.DataObject;
import com.example.tv360.model.DataObjectLogin;
import com.example.tv360.model.DeviceInfo;
import com.example.tv360.model.LoginModel;
import com.example.tv360.model.UserModel;
import com.example.tv360.presenter.LoginPresenter;
import com.example.tv360.retrofit.ApiService;
import com.example.tv360.retrofit.HomeService;
import com.example.tv360.retrofit.UserApiService;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


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
        String Username = username.getText().toString();
        String Password =   password.getText().toString();
//        UserModel userModel = new UserModel(Username,Password,deviceInfo);
        apiInterface = ApiService.getClient().create(UserApiService.class);
        Call<DataObjectLogin> data = apiInterface.login(new LoginModel(Username,Password,"PASS",null,deviceInfo));
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
        Intent intent = new Intent(LoginActivity.this, PlayingVideoAvtivity.class);
        startActivity(intent);
        Toast.makeText(LoginActivity.this,"Login success",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void loginError() {
        Toast.makeText(LoginActivity.this,"Login false",Toast.LENGTH_SHORT).show();
    }
}