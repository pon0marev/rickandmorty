package com.example.ponomarev.wiki.JsonData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    @GET("character/")
    Call<ResponseServer> responceCallPage(@Query("page")String number);

    @GET("episode/{number}")
    Call<Episode> responceCallEpisode(@Path("number")String number);
}
