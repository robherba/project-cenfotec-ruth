package com.rhernandez.social_ruth.utilities;

import com.rhernandez.social_ruth.models.RequestAuthenticate;
import com.rhernandez.social_ruth.models.ResponseAuth;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by jduran on 08/04/2016.
 */
public interface NetworkAPI {
    @POST("User/Authenticate")
    Call<ResponseAuth> authenticate(@Body RequestAuthenticate requestAuthenticate);
}
