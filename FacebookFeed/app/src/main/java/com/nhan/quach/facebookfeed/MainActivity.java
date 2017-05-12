package com.nhan.quach.facebookfeed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.lang.reflect.Array;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    LoginButton loginButton;
    TextView tv;
    CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        loginButton = (LoginButton) findViewById(R.id.loginBtn);
        tv = (TextView) findViewById(R.id.textView);

        loginButton.setReadPermissions(Arrays.asList("email", "user_about_me", "user_birthday", "user_posts", "user_friends", "read_custom_friendlists"));

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "user_about_me", "user_birthday", "user_posts", "read_custom_friendlists", "user_friends"));


        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                tv.setText("ID: " + loginResult.getAccessToken().getToken());
                Log.d("FB", "onSuccess: " + loginResult.getAccessToken().getToken());
                Toast.makeText(getApplicationContext(), "Permissions: "+loginResult.getAccessToken().getPermissions(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplication(), Tab.class);
                startActivity(intent);
            }

            @Override
            public void onCancel() {
                tv.setText("Cancelled");
            }

            @Override
            public void onError(FacebookException error) {
                tv.setText(error.getMessage());
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
