package com.example.anshuman_hp.technews;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.net.Network;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ImageView top;
    TextView top_headline,published_at;
    RecyclerView news;
    com.android.volley.Network network;
     String url="https://newsapi.org/v1/articles?source=techcrunch&sortBy=latest&apiKey=135d26b0e1fa4e15b346046602f81e48";
    Cache cache;
    RequestQueue requestQueue;
    LinearLayoutManager linearLayoutManager;
    articles article;
    ProgressDialog pd;
    newsAdapter adapter;
    List<articles> data=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        final SwipeRefreshLayout swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe);
        news=(RecyclerView)findViewById(R.id.news_recycler);
        linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        news.setLayoutManager(linearLayoutManager);
        network=new BasicNetwork(new HurlStack());
        cache=new DiskBasedCache(getCacheDir(),1024*1024);
        requestQueue=new RequestQueue(cache,network);
        requestQueue.start();
        pd=new ProgressDialog(this);
        pd.setMessage("Please Wait");
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                data.clear();
                getjsondata();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        getjsondata();

        pd.show();
        DividerItemDecoration itemDecoration=new DividerItemDecoration(news.getContext(),linearLayoutManager.getOrientation());
        itemDecoration.setDrawable(new ColorDrawable(34));
        news.addItemDecoration(itemDecoration);
//        data.add(new articles("gshng","ajhg","afhg","hgJHSF","jhvdafsj","aghjfsbg"));
//        data.add(new articles("gshng","ajhg","afhg","hgJHSF","jhvdafsj","aghjfsbg"));
//        data.add(new articles("gshng","ajhg","afhg","hgJHSF","jhvdafsj","aghjfsbg"));
//        data.add(new articles("gshng","ajhg","afhg","hgJHSF","jhvdafsj","aghjfsbg"));
        adapter=new newsAdapter(this,data);
        adapter.notifyDataSetChanged();
        news.setAdapter(adapter);


    }
    public void getjsondata(){
        AsyncTask<Integer,Void,Void> task=new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... params) {
                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray=response.getJSONArray("articles");
                            for(int i=0;i<jsonArray.length();++i){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                /*article.setAuthor("author");
                                article.setDescription("description");
                                article.setPublishedAt("publishedAt");
                                article.setTitle("title");
                                article.setUrl("url");
                                article.setUrlToImage("urlToImage");*/
                                article=new articles(jsonObject.getString("author"),jsonObject.getString("title"),jsonObject.getString("description"),
                                       jsonObject.getString("url"), jsonObject.getString("urlToImage"),jsonObject.getString("publishedAt"));
                                Log.v("aaa",jsonObject.toString());
                                data.add(article);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        pd.cancel();
                        news.setAdapter(adapter);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });requestQueue.add(jsonObjectRequest);


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
            }
        };task.execute();
    }
}
