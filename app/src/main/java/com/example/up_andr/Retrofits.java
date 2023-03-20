package com.example.up_andr;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Retrofits {
    @POST("user/login")
    Call<User_Mask> cUser (@Body UserM userM);
}
