package com.nhan.quach.facebookfeed;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by nhan on 4/23/17.
 */

public class Tab_1 extends Fragment {
    ListView listView;
    //ArrayList<Post> Posts = new ArrayList<Post>();
    ArrayList<String> messages = new ArrayList<String>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_1, container, false);
        listView = (ListView) rootView.findViewById(R.id.list);

        GraphRequest.Callback gCallBack = new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                JSONObject jsonObject = response.getJSONObject();
                JSONArray jsonArray = null;
                try {
                    jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject m = jsonArray.getJSONObject(i);
                        String mess = m.getString("message");
                        String created_date = m.getString("created_time");
                        String id = m.getString("id");

                        //Post post = new Post(mess, created_date, id);
                        //Log.d("feed", "onCompleted: " + post);
                        //Posts.add(post);
                        messages.add(mess);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ArrayAdapter adapter = new ArrayAdapter(rootView.getContext(), android.R.layout.simple_list_item_1, messages);
                listView.setAdapter(adapter);
            }
        };

        GraphRequest g =  new GraphRequest(AccessToken.getCurrentAccessToken(), "me/feed", null, HttpMethod.GET, gCallBack);

        g.executeAsync();
        /*
        * ["data": {
                    *   "message" : ...,
                    *   "id: : ...,
                    *   "created_time : ...
        * }]
        * */

        return rootView;
    }
}
