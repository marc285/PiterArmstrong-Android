package com.dsaproject.piterarmstrong_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dsaproject.piterarmstrong_android.models.Objeto;
import com.dsaproject.piterarmstrong_android.models.ObjetoList;
import com.dsaproject.piterarmstrong_android.models.User;
import com.dsaproject.piterarmstrong_android.services.UserManagerService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashboardFragment extends Fragment {

    private SwipeRefreshLayout updateobjectsrefresh;
    private TextView usertxt;
    private TextView healthtxt;
    private TextView defensetxt;
    private TextView attacktxt;
    private TextView piecestxt;
    private TextView moneytxt;
    private TextView screentxt;
    private RecyclerView objectsRV;
    private Button playbtn;

    private UserManagerService usersAPI;

    //---------------------------------------------------------API Methods------------------------------------------------------------//
    public void updateStats(String usrname){
        //Method getUser() of the Users API Interface

        Call<User> call = usersAPI.getUser(usrname);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    //We "fill" the logged User instance
                    User loggedUsr = User.getInstance();
                    loggedUsr.setPassword(response.body().getPassword());
                    loggedUsr.setHealth(response.body().getHealth());
                    loggedUsr.setDefense(response.body().getDefense());
                    loggedUsr.setAttack(response.body().getAttack());
                    loggedUsr.setPieces(response.body().getPieces());
                    loggedUsr.setMoney(response.body().getMoney());
                    loggedUsr.setScreen(response.body().getScreen());

                    usertxt.setText(User.getInstance().getUsername());
                    healthtxt.setText(String.valueOf(User.getInstance().getHealth()));
                    defensetxt.setText(String.valueOf(User.getInstance().getDefense()));
                    attacktxt.setText(String.valueOf(User.getInstance().getAttack()));
                    piecestxt.setText(String.valueOf(User.getInstance().getPieces()));
                    moneytxt.setText(String.valueOf(User.getInstance().getMoney()));
                    screentxt.setText(String.valueOf(User.getInstance().getScreen()));
                }
                else
                    Toast.makeText(getActivity(), "Error getting User statistics: " + response.code() + "\nInternal Server Error", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Call<List<Objeto>> call2 = usersAPI.getUserObjects(usrname);
        call2.enqueue(new Callback<List<Objeto>>() {
            @Override
            public void onResponse(Call<List<Objeto>> call, Response<List<Objeto>> response) {
                if(response.isSuccessful()) {
                    //We "fill" the logged User Object List (instance)
                    ObjetoList loggedUsrList = ObjetoList.getInstance();
                    loggedUsrList.setList(response.body());

                    objectsRV.setAdapter(new ObjectsRVAdapter(response.body()));
                }
                else
                    Toast.makeText(getActivity(), "Error getting User Object List: " + response.code() + "\nInternal Server Error", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<Objeto>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    //--------------------------------------------------------------------------------------------------------------------------------//

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Initialize and fill all the Dashboard fields
        updateobjectsrefresh = (SwipeRefreshLayout) getView().findViewById(R.id.statsSwipeRefreshLayout);
        usertxt = (TextView) getView().findViewById(R.id.userTextView);
        healthtxt = (TextView) getView().findViewById(R.id.healthTextView);
        defensetxt = (TextView) getView().findViewById(R.id.defenseTextView);
        attacktxt = (TextView) getView().findViewById(R.id.attackTextView);
        piecestxt = (TextView) getView().findViewById(R.id.piecesTextView);
        moneytxt = (TextView) getView().findViewById(R.id.moneyTextView);
        screentxt = (TextView) getView().findViewById(R.id.screenTextView);
        objectsRV = (RecyclerView) getView().findViewById(R.id.objectsRecyclerView);
        playbtn = (Button) getView().findViewById(R.id.playButton);

        Retrofit retrofitinstance = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/dsaApp/") //Later on we will put the server's IP address, meanwhile in localhost
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        usersAPI = retrofitinstance.create(UserManagerService.class);

        objectsRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        updateStats(User.getInstance().getUsername());

        updateobjectsrefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        updateStats(User.getInstance().getUsername());
                        updateobjectsrefresh.setRefreshing(false);
                    }
                }
        );

        playbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UnityActivity.class);
                startActivity(intent);
            }
        });
    }
}
