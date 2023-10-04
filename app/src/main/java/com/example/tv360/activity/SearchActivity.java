package com.example.tv360.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tv360.R;
import com.example.tv360.model.DataObject;
import com.example.tv360.retrofit.ApiService;
import com.example.tv360.retrofit.HomeService;
import com.google.gson.JsonElement;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    ImageButton previous_search,recordButton, removebutton;
    EditText keywordsearch;

    boolean isremovedisplay = false;

    private HomeService apiserver;

    private  static  final  String SHARED_PREF_NAME = "mypref";

    private  static  final  String KEY_USERID = "userId";

    private  static  final  String KEY_PROFILEID = "profileId";

    private  static  final  String KEY_ACCESSTOKEN ="accessToken";

    private  static  final int REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_search);
        previous_search = findViewById(R.id.previous_search);
        recordButton = findViewById(R.id.recordButton);
        keywordsearch = findViewById(R.id.searchView);
        removebutton = findViewById(R.id.removeButton);
        keywordsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0)
                {
                    isremovedisplay = true;
                    removebutton.setBackground(getResources().getDrawable(R.drawable.baseline_cancel_24));
                    SharedPreferences sharedPref = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
                    String userID = sharedPref.getString(KEY_USERID,"");
                    String profileID = sharedPref.getString(KEY_PROFILEID,"");
                    String accessToken = sharedPref.getString(KEY_ACCESSTOKEN,"");
                    String m_andoid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                    apiserver = ApiService.getlink(profileID,userID, m_andoid,"Bearer " + accessToken).create(HomeService.class);
                    Call<JsonElement> data  = apiserver.search(s.toString(),0,"all","suggest");
                    data.enqueue(new Callback<JsonElement>() {
                        @Override
                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                            Log.d("Data ne ", ""+response.body());
                        }
                        @Override
                        public void onFailure(Call<JsonElement> call, Throwable t) {
                        }
                    });
                }
                else
                {
                    isremovedisplay = false;
                    removebutton.setBackground(getResources().getDrawable(R.drawable.none));
                }

            }
        });

        keywordsearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT)
                {
                    Log.d("Thay doi ok 3" , " "+v.getText().toString());
                }
                return false;
            }
        });

        removebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isremovedisplay)
                {
                    keywordsearch.setText("");
                    removebutton.setBackground(getResources().getDrawable(R.drawable.none));
                }
            }
        });
        previous_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Nói gì đó. . .");
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
    }

    private void fillerListener(String newText) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String spokentext = results.get(0);
            keywordsearch.setText(spokentext);

        }
    }
}