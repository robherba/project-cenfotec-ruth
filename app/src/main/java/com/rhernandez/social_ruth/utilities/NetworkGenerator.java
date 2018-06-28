package com.rhernandez.social_ruth.utilities;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rhernandez on 26/6/18.
 */
public class NetworkGenerator {

    public static String URL = "http://TecnoBCRMovil.cloudapp.net:1000/WebApiMASMobileDemo/";

    public static <S> S createService(Class<S> serviceClass) {
        final OkHttpClient httpClient = new OkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.newBuilder().connectTimeout(1, TimeUnit.MINUTES).readTimeout(1, TimeUnit.MINUTES).build())
                .build();
        return retrofit.create(serviceClass);
    }
}
