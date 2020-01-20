package com.dsaproject.piterarmstrong_android;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dsaproject.piterarmstrong_android.models.User;
import com.dsaproject.piterarmstrong_android.services.UserManagerService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsersFragment extends Fragment {

    private RecyclerView usersRV;
    private SwipeRefreshLayout updateusersrefresh;

    private UserManagerService usersAPI;

    //---------------------------------------------------------API Methods------------------------------------------------------------//
    public void updateUserList(){
        //Method getUsers() of the Users API Interface

        Call<List<User>> call = usersAPI.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){
                    usersRV.setAdapter(new UsersRVAdapter(response.body()));
                }
                else
                    Toast.makeText(getActivity(), "Error getting Users List: " + response.code() + "\nInternal Server Error", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
    //--------------------------------------------------------------------------------------------------------------------------------//

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_users, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usersRV = (RecyclerView) getView().findViewById(R.id.usersRecyclerView);
        updateusersrefresh = (SwipeRefreshLayout) getView().findViewById(R.id.usersSwipeRefreshLayout);

        Retrofit retrofitinstance = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/dsaApp/") //Later on we will put the server's IP address, meanwhile in localhost
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        usersAPI = retrofitinstance.create(UserManagerService.class);

        usersRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        updateUserList();

        updateusersrefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateUserList();
                updateusersrefresh.setRefreshing(false);
            }
        });
    }
}
