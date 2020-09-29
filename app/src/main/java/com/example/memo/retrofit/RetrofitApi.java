package com.example.memo.retrofit;


import com.example.senlonase.model.AccueilResponse;
import com.example.senlonase.model.ActuResponse;
import com.example.senlonase.model.PoSResponse;
import com.example.senlonase.model.ProgResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface RetrofitApi {
    @GET("index.php")
    Call<List<AccueilResponse>> getAccueil();

    @GET("actu.php")
    Call<List<ActuResponse>> getActu();

    @GET("programme.php")
    Call<List<ProgResponse>> getProg();

    @GET("pointVente.php")
    Call<List<PoSResponse>> getPoS();


}
