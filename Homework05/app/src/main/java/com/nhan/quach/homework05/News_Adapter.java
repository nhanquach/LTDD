package com.nhan.quach.homework05;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by nhan on 4/26/17.
 */

public class News_Adapter extends RecyclerView.Adapter<News_Adapter.MyViewHolder> {

    private List<News> NewsList;

    public News_Adapter(List<News> newsList) {
        NewsList = newsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item, parent, false);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        News single_news = NewsList.get(position);
        holder.title.setText(single_news.getTitle());
        holder.author.setText(single_news.getCreator());
        try {
            holder.thumbnail.setImageBitmap(LoadImage.loadBitmap(single_news.getThumbnail()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return NewsList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title, author;
        public ImageView thumbnail;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.news_title);
            author = (TextView) itemView.findViewById(R.id.news_author);
            thumbnail = (ImageView) itemView.findViewById(R.id.imgThumbnail);
        }
    }
}


