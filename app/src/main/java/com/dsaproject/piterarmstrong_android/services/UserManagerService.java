package com.dsaproject.piterarmstrong_android.services;

import com.dsaproject.piterarmstrong_android.models.User;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserManagerService {

    @GET("/usermanager/login")
    Call<User> login(User u);


}
