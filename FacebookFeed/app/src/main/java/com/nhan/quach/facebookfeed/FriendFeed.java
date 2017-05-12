package com.nhan.quach.facebookfeed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FriendFeed extends AppCompatActivity {

    ArrayList<String> messages = new ArrayList<String>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_feed);

        listView = (ListView) findViewById(R.id.list3);

        Intent intent = getIntent();
        String user_id = intent.getStringExtra("user-id");

        GraphRequest.Callback gCallBack = new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                JSONObject jsonObject = response.getJSONObject();
                Log.d("JSON", "onCompleted: "+jsonObject);
                JSONArray jsonArray = null;
                try {
                    jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject m = jsonArray.getJSONObject(i);
                        String mess = m.getString("message");
                        String created_date = m.getString("created_time");
                        String id = m.getString("id");

                        messages.add(mess);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ArrayAdapter adapter = new ArrayAdapter(FriendFeed.this, android.R.layout.simple_list_item_1, messages);
                listView.setAdapter(adapter);
            }
        };

        GraphRequest g =  new GraphRequest(AccessToken.getCurrentAccessToken(), user_id+"/feed", null, HttpMethod.GET, gCallBack);

        g.executeAsync();
    }
}
