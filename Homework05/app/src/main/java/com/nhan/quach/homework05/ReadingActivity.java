package com.nhan.quach.homework05;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class ReadingActivity extends AppCompatActivity {

    TextView content, articalTitle, articleAuthor, articlePubdate;
    ImageView imageView;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final News single_news = getIntent().getExtras().getParcelable("one_news");
        String title = getIntent().getStringExtra("TitleBar");
        this.setTitle(title);


        imageView = (ImageView) findViewById(R.id.imgThumbnail);
        articalTitle = (TextView) findViewById(R.id.ArticleTitle);
        articleAuthor = (TextView) findViewById(R.id.ArticleAuthor);
        articlePubdate = (TextView) findViewById(R.id.ArticlePub);

        articalTitle.setText(single_news.getTitle());
        articleAuthor.setText(single_news.getCreator());
        articlePubdate.setText(single_news.getPubDate());


        try {
            imageView.setImageBitmap(LoadImage.loadBitmap(single_news.getThumbnail()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String des = single_news.getDescription();
        String s = des.substring(des.indexOf("<img"), des.indexOf("/>")+2);
        des = des.replaceAll(s, "");
        s = des.substring(des.indexOf("<a href=\"http://www.simplyrecipes.com"));
        des = des.substring(0, des.indexOf(s));
        Log.d("TAG", "s: "+s);
        Log.d("TAG", "find: " + des.indexOf(s));
        Log.d("TAG", "des: "+des);

        content = (TextView) findViewById(R.id.content);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            content.setText(Html.fromHtml(des, Html.FROM_HTML_MODE_COMPACT));
        }else {
            content.setText(Html.fromHtml(des));
        }

        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(single_news.getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
