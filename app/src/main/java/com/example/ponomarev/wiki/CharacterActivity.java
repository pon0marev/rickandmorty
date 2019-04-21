package com.example.ponomarev.wiki;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ponomarev.wiki.JsonData.Episode;
import com.example.ponomarev.wiki.JsonData.Episodes;
import com.example.ponomarev.wiki.JsonData.ResponseServer;
import com.example.ponomarev.wiki.JsonData.Result;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CharacterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Bundle arguments = getIntent().getExtras();
        final Result result ;
        final Episodes episodes;
        if(arguments!=null) {
            result = (Result) arguments.getSerializable(Result.class.getSimpleName());
            episodes = (Episodes) arguments.getSerializable(Episodes.class.getSimpleName());
            ImageView imageView=(ImageView)findViewById(R.id.imageView);
            TextView text=(TextView)findViewById(R.id.text);


            Picasso.get().load(result.getImage()).into(imageView);
            String sText="Name: "+result.getName();
            sText+="\nStatus: "+result.getStatus();
            sText+="\nSpecies: "+result.getSpecies();
            sText+="\nGender: "+result.getGender();
            sText+="\nOrigin: "+result.getOrigin().getName();
            sText+="\nLast location: "+result.getLocation().getName();
            sText+="\nEpisodes: ";
            for (int i=0;i<episodes.getEpisodes().size();i++){
                sText+="\n\t◉ "+episodes.getEpisodes().get(i).getName()+
                        "\t Air data: "+ episodes.getEpisodes().get(i).getAirDate()+
                        "\n\t\t Created: "+DateConvertor.convert(episodes.getEpisodes().get(i).getCreated());
            }
            text.setText(sText);
        }
    }

}
