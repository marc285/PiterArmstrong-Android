package com.dsaproject.piterarmstrong_android;
import android.util.Log;
import android.widget.Toast;

import com.dsaproject.piterarmstrong_android.models.User;
import com.dsaproject.piterarmstrong_android.services.UserManagerService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIUnity {

    static Retrofit retrofitinstance = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/dsaApp/") //Later on we will put the server's IP address, meanwhile in localhost
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private  static UserManagerService usersAPI = retrofitinstance.create(UserManagerService.class);
    public static String getPlayerStats() { //le envia stats al unity
        User stats = User.getInstance();
        String res = "";
        res += stats.getHealth() + ",";
        res += stats.getScreen() + ",";
        return res;
    }
    public static void sendStats(String stringStats)
    {
        User stats = User.getInstance();
        String[] trozos = stringStats.split(",");
        stats.setScreen(Integer.parseInt(trozos[0]));
        stats.setHealth(Integer.parseInt(trozos[1]));

        Call<Void> call = usersAPI.updateUser(stats,stats.getUsername());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    //We "fill" the logged User instance

                    Log.i("MyActivity", "your current password is:" + User.getInstance().getHealth());

                }
                else
                    Log.i("MyActivity", "your current password is:" + User.getInstance().getHealth());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }
}
