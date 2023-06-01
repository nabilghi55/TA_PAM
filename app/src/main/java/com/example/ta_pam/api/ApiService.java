package com.example.ta_pam.api;

import com.example.ta_pam.model.ProvinsiResponse;
import com.example.ta_pam.model.kotaKabupatenResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;


public interface ApiService {
    String BASE_URL = "https://dev.farizdotid.com/api/";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ApiService service = retrofit.create(ApiService.class);

    @GET("daerahindonesia/provinsi")
    Call<ProvinsiResponse> getProvinsi();

    @GET("daerahindonesia/kota?id_provinsi=32")
    Call<kotaKabupatenResponse> getkotaKabupaten();


}


