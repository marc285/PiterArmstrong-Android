package com.dsaproject.piterarmstrong_android.services;

import com.dsaproject.piterarmstrong_android.models.Objeto;
import com.dsaproject.piterarmstrong_android.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserManagerService {

    @POST("usermanager/login/")
    Call <User> login(@Body User usr);

    @POST("usermanager/register")
    Call <Void> register(@Body User usr);

    @GET("usermanager/users")
    Call <List<User>> getUsers();

    @GET("usermanager/users/{name}")
    Call <User> getUser(@Path("name") String usrname);

    @POST("usermanager/users/{name}/update") //PUT
    Call <Void> updateUser(@Body User usr, @Path("name") String usrname);

    @POST("usermanager/users/{name}/delete") //DELETE
    Call <Void> deleteUser(@Body User us);

    @GET("usermanager/users/{name}/screen")
    Call <String> getUserScreen(@Path("name") String usrname);

    @GET("usermanager/users/{name}/objects")
    Call <List<Objeto>> getUserObjects(@Path("name") String usrname);

    @POST("usermanager/users/{name}/addobject")
    Call <Void> addObjectToUser(@Path("name") String usrname, String obj);
}
