package com.nhan.quach.facebookfeed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nhan on 4/23/17.
 */

public class Tab_2 extends Fragment {

    ListView listView;
    ArrayList<String> friends = new ArrayList<String>();
    String[] ids = new String[1000];
    TextView tvS;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("tab2", "onCreateView: Tab2 Activated");
        final View rootView = inflater.inflate(R.layout.fragment_2, container, false);

        listView = (ListView) rootView.findViewById(R.id.list1);
        tvS = (TextView) rootView.findViewById(R.id.tv_total);

        GraphRequest.Callback gCallBack1 = new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                Log.d("res", "res: " + response.getJSONObject().toString());
                JSONObject jsonObject = response.getJSONObject();
                JSONArray jsonArray = null;
                try {
                    jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject m = jsonArray.getJSONObject(i);
                        String Fname = m.getString("name");
                        String id = m.getString("id");
                        ids[i] = id;
                        friends.add(Fname);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ArrayAdapter adapter = new ArrayAdapter(rootView.getContext(), android.R.layout.simple_list_item_1, friends);

                listView.setAdapter(adapter);
/*                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(rootView.getContext(), FriendFeed.class);
                        intent.putExtra("user-id", ids[position]);
                        startActivity(intent);
                    }
                });
*/
                try {
                    JSONObject n = jsonObject.getJSONObject("summary");
                    String to = n.getString("total_count");
                    tvS.setText("You have: "+ friends.size() +" friend(s) using this app in total " + to + " friends.");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        GraphRequest g1 =  new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/friends", null, HttpMethod.GET, gCallBack1);

        g1.executeAsync();

        return rootView;
    }
}
