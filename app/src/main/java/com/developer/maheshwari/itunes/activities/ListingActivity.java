package com.developer.maheshwari.itunes.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.developer.maheshwari.itunes.R;
import com.developer.maheshwari.itunes.adapter.ListViewAdapter;
import com.developer.maheshwari.itunes.beanClass.onSingleListItemClicked;
import com.developer.maheshwari.itunes.response.ResponseClass;
import com.developer.maheshwari.itunes.utils.ConnectionDetector;
import com.developer.maheshwari.itunes.webservice.API;
import com.developer.maheshwari.itunes.webservice.RestClient;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class ListingActivity extends AppCompatActivity  implements SwipeRefreshLayout.OnRefreshListener,
        onSingleListItemClicked
{
    ListView listView;
    LinearLayout parentLayout;
    SwipeRefreshLayout swipeLayout;
    ResponseClass responseClass;
    ListViewAdapter adapter;
    public static ArrayList<ResponseClass.results> list = new ArrayList<>();
    ConnectionDetector cd;
    Toolbar toolbar;
    ProgressDialog dialog;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Top 50");
        getViewById();
        callListingWS();
    }

    private void getViewById()
    {
        cd = new ConnectionDetector(this);
        listView = (ListView)findViewById(R.id.listView);
        parentLayout =(LinearLayout)findViewById(R.id.parentLayout);
        swipeLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeResources(
                android.R.color.holo_blue_dark,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_red_dark,
                android.R.color.holo_purple);
    }

    public void onRefresh()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeLayout.setRefreshing(false);
                callListingWS();
            }
        }, 3500);
    }

    private void callListingWS()
    {
        if (cd.isConnectingToInternet())
        {
            final ProgressDialog dialog = ProgressDialog.show(ListingActivity.this, "", "Please wait...");
            dialog.show();
            list.clear();
            OkHttpClient okClient = new OkHttpClient();
            okClient.interceptors().add(new Interceptor()
            {
                public com.squareup.okhttp.Response intercept(Chain chain) throws IOException
                {
                    com.squareup.okhttp.Response response = chain.proceed(chain.request());
                    System.out.println("responseClass---"+response);
                    return response;
                }
            });
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).client(okClient).addConverterFactory(GsonConverterFactory.create()).build();
            API api = RestClient.client.create(API.class);
            Call<ResponseClass> call = api.getResponseData("");
            call.enqueue(new Callback<ResponseClass>()
            {
                public void onResponse(Response<ResponseClass> response)
                {
                    dialog.dismiss();
                    System.out.println("responseClass--"+response.body());
                    list = new ArrayList<>();
                    responseClass = response.body();
                    if (responseClass.getResultCount().equals("50"))
                    {
                        for (int i = 0; i < responseClass.getResults().length; i++)
                        {
                            list.add(responseClass.getResults()[i]);
                            System.out.println("responseClass Data---" + list.get(i).getCollectionId());
                        }
                        setListView();
                    }
                    else
                    {
                        listView.setAdapter(null);
                        Toast.makeText(ListingActivity.this,"Sorry! No data found",Toast.LENGTH_SHORT).show();
                    }
                }

                public void onFailure(Throwable t)
                {
                    dialog.dismiss();
                    t.printStackTrace();
                    System.out.println("onFailure--" +t.getMessage());
                    Toast.makeText(ListingActivity.this,"Time out error! Please swipe down to refresh",Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            dialog.dismiss();
            Toast.makeText(ListingActivity.this,"Please check your data services network.",Toast.LENGTH_SHORT).show();
        }
    }

    private void setListView()
    {
        adapter = new ListViewAdapter(this,list,this);
        listView.setAdapter(adapter);
    }

    public void OnSingleListItemClicked(ListViewAdapter adapter, ArrayList<ResponseClass.results> list, int position)
    {
        Intent in = new Intent(ListingActivity.this,DetailsActivity.class);
        Bundle extras = new Bundle();
        extras.putInt("position",position);
        in.putExtras(extras);
        startActivity(in);
    }
}