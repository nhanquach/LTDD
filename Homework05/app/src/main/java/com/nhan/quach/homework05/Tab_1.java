package com.nhan.quach.homework05;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import static android.content.ContentValues.TAG;

/**
 * Created by nhan on 4/26/17.
 */

public class Tab_1 extends Fragment {

    public List<News> NewsList = new ArrayList<>();
    RecyclerView mRecyclerview;
    public News_Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_one, container, false);
        NewsFeed getFeed = new NewsFeed();
        getFeed.execute();

        mRecyclerview = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setItemAnimator(new DefaultItemAnimator());

        adapter = new News_Adapter(NewsList);
        mRecyclerview.addItemDecoration(new Divider(getContext(), LinearLayoutManager.VERTICAL));

        mRecyclerview.setAdapter(adapter);

        mRecyclerview.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerview, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ReadingActivity.class).putExtra("one_news", NewsList.get(position) );
                intent.putExtra("TitleBar", "Excerpts with photos");
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return rootView;
    }

    public class NewsFeed extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog = ProgressDialog.show(getContext(), "","Loading",true);

        @Override
        protected void onPostExecute(Void aVoid) {
            adapter.notifyDataSetChanged();
            if (dialog.isShowing()){
                dialog.dismiss();
            }
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String downloadURL = "http://feeds.feedburner.com/elise/simplyrecipes";
            try {
                URL url = new URL(downloadURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                processXML(inputStream);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
            return null;
        }

        public void processXML(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            Element rootElement = document.getDocumentElement();

            //Get list of <item></item>
            NodeList itemList = rootElement.getElementsByTagName("item");
            NodeList itemChild = null;

            //Node
            Node currentItem = null;
            Node currentChild = null;


            //Loop through all <item>
            for (int i = 0; i < itemList.getLength(); i++){
                currentItem = itemList.item(i);
                itemChild = currentItem.getChildNodes();
                int flag = 0;
                //Single_news object
                News single_news = new News();
                for (int j = 0; j < itemChild.getLength(); j++){
                    currentChild = itemChild.item(j);

                    if (currentChild.getNodeName().equalsIgnoreCase("title")){
                        single_news.setTitle(currentChild.getTextContent());
                        flag++;
                    }

                    if (currentChild.getNodeName().equalsIgnoreCase("link")){
                        single_news.setLink(currentChild.getTextContent());
                        flag++;
                    }

                    if (currentChild.getNodeName().equalsIgnoreCase("dc:creator")){
                        single_news.setCreator(currentChild.getTextContent());
                        flag++;
                    }

                    if (currentChild.getNodeName().equalsIgnoreCase("description")){
                        single_news.setDescription(currentChild.getTextContent());
                        flag++;
                    }

                    if (currentChild.getNodeName().equalsIgnoreCase("comments")){
                        single_news.setComments(currentChild.getTextContent());
                        flag++;
                    }

                    if (currentChild.getNodeName().equalsIgnoreCase("pubDate")){
                        single_news.setPubDate(currentChild.getTextContent());
                        flag++;
                    }

                    if (currentChild.getNodeName().equalsIgnoreCase("media:content")){
                        String url = currentChild.getAttributes().item(2).getTextContent();
                        single_news.setThumbnail(url);
                        flag++;
                    }
                }
                NewsList.add(single_news);
            }
        }

    }
}


