package com.developer.maheshwari.itunes.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.developer.maheshwari.itunes.R;
import com.developer.maheshwari.itunes.response.ResponseClass;
import com.developer.maheshwari.itunes.utils.ImageTrans_CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity
{
    CoordinatorLayout parentLayout;
    ImageView profile_img;
    TextView nameTextView,details,trackName,collectionName,price;
    VideoView preview;
    AppBarLayout app_bar_layout;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsing_toolbar;

    ArrayList<ResponseClass.results> list = ListingActivity.list;

    Bundle bundle = new Bundle();
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getViewById();
        bundle = getIntent().getExtras();
        position = bundle.getInt("position");
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Michael Jackson");
        collapsing_toolbar.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        app_bar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0)
                {
                    collapsing_toolbar.setTitle("Michael Jackson");
                    isShow = true;
                }
                else if(isShow)
                {
                    collapsing_toolbar.setTitle("Michael Jackson");
                    isShow = false;
                }
            }
        });
        getDetails();
    }

    private void getDetails()
    {
        nameTextView.setText(list.get(position).getArtistName());
        details.setText("Genere : "+list.get(position).getPrimaryGenreName());
        trackName.setText(list.get(position).getTrackCensoredName());
        collectionName.setText(list.get(position).getCollectionCensoredName());
        price.setText(list.get(position).getTrackPrice()+" "+list.get(position).getCurrency());
        Picasso.with(DetailsActivity.this)
                .load(list.get(position).getArtworkUrl100())
                .transform(new ImageTrans_CircleTransform())
                .into(profile_img);
        try
        {
            MediaController mediacontroller = new MediaController(DetailsActivity.this);
            mediacontroller.setAnchorView(preview);
            String path = list.get(position).getPreviewUrl();
            System.out.println("PreviewUrl from server--"+path);
            Uri video = Uri.parse(path);
            preview.setMediaController(mediacontroller);
            preview.setVideoURI(video);
            preview.start();
        }
        catch (Exception e)
        {
            System.out.println("Video Exception--"+e.getMessage());
            e.printStackTrace();
        }
    }

    private void getViewById()
    {
        app_bar_layout = (AppBarLayout)findViewById(R.id.app_bar_layout);
        collapsing_toolbar = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        parentLayout = (CoordinatorLayout)findViewById(R.id.parentLayout);
        profile_img = (ImageView)findViewById(R.id.profile_img);
        nameTextView = (TextView)findViewById(R.id.nameTextView);
        details =(TextView)findViewById(R.id.details);
        trackName = (TextView)findViewById(R.id.trackName);
        collectionName = (TextView)findViewById(R.id.collectionName);
        price = (TextView)findViewById(R.id.price);
        preview = (VideoView)findViewById(R.id.preview);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id)
        {
            case android.R.id.home:
            {
                Intent in = new Intent(DetailsActivity.this,ListingActivity.class);
                startActivity(in);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
