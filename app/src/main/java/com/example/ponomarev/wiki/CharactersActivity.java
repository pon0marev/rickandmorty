package com.example.ponomarev.wiki;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.ponomarev.wiki.Adapter.RecyclerAdapter;
import com.example.ponomarev.wiki.JsonData.Api;
import com.example.ponomarev.wiki.JsonData.Episode;
import com.example.ponomarev.wiki.JsonData.Episodes;
import com.example.ponomarev.wiki.JsonData.ResponseServer;
import com.example.ponomarev.wiki.JsonData.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CharactersActivity extends AppCompatActivity implements RecyclerAdapter.RecyclerAdapterListener,RecyclerAdapter.OnLoadMoreListener {

    private RecyclerView recyclerView;
    private ResponseServer responseServer ;
    private RecyclerAdapter rAdapter;
    private int pageNumber;
    private static String url = "https://rickandmortyapi.com/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Bundle arguments = getIntent().getExtras();

        pageNumber=2;
        if(arguments!=null) {
            responseServer = (ResponseServer) arguments.getSerializable(ResponseServer.class.getSimpleName());
            recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
            GridLayoutManager manager;
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                manager = new GridLayoutManager(this, 2);
            }
            else {
                manager = new GridLayoutManager(this, 3);
            }

            recyclerView.setLayoutManager(manager);
            rAdapter=new RecyclerAdapter(getApplicationContext(),responseServer.getResults());
            rAdapter.setOnItemClickListener(this);
            rAdapter.setOnLoadMoreListener(this);
            recyclerView.setAdapter(rAdapter);


        }
    }

    @Override
    public void onItemClick(int position, View v) {
        final Intent intent=new Intent(CharactersActivity.this,CharacterActivity.class);
        intent.putExtra(Result.class.getSimpleName(), responseServer.getResults().get(position));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        final String last =responseServer.getResults().get(position).getEpisode().
                get(responseServer.getResults().get(position).getEpisode().size()-1);
        final Episodes episodes=new Episodes();
        for (final String s:responseServer.getResults().get(position).getEpisode()) {
            String number=s.substring(s.lastIndexOf('/'));
            Call<Episode> responseServerCall = api.responceCallEpisode(number);
            responseServerCall.enqueue(new Callback<Episode>() {

                @Override
                public void onResponse(Call<Episode> call, Response<Episode> response) {
                    if (response.isSuccessful()) {
                        episodes.addEpisode(response.body());
                        if(s.equals(last)) {
                            intent.putExtra(Episodes.class.getSimpleName(), episodes);
                            startActivity(intent);
                        }
                    } else {
                    }
                }

                @Override
                public void onFailure(Call<Episode> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Failure. Check your internet connection", Toast.LENGTH_SHORT).show();
                }

            });

        }
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onLoadMore() {

        if(pageNumber<=responseServer.getInfo().getPages()) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Api api = retrofit.create(Api.class);
            Call<ResponseServer> responseServerCall = api.responceCallPage("" + pageNumber);
            responseServerCall.enqueue(new Callback<ResponseServer>() {

                @Override
                public void onResponse(Call<ResponseServer> call, Response<ResponseServer> response) {
                    if (response.isSuccessful()) {
                        responseServer.getResults().addAll(response.body().getResults());
                        rAdapter.notifyDataSetChanged();
                        rAdapter.endLoading();
                    }
                }

                @Override
                public void onFailure(Call<ResponseServer> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Failure. Check your internet connection", Toast.LENGTH_SHORT).show();
                }
            });
            pageNumber++;
        }
    }
}
