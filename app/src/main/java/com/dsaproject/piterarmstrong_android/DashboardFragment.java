package com.dsaproject.piterarmstrong_android;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dsaproject.piterarmstrong_android.models.Objeto;
import com.dsaproject.piterarmstrong_android.models.User;
import com.dsaproject.piterarmstrong_android.services.UserManagerService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashboardFragment extends Fragment {

    SwipeRefreshLayout updaterefresh;
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
    private Context context;

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
                else{
                    if(response.code() == 404)
                        Toast.makeText(context, "Error getting User statistics: " + response.code() + "\nInternal Server Error", Toast.LENGTH_LONG).show();
                }
                //showProgress(false);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                //showProgress(false);
            }
        });

        /*Call<Objeto> call2 = usersAPI.getUserObjects(User.getInstance().getUsername());
        call2.enqueue(new Callback<Objeto>() {
            @Override
            public void onResponse(Call<Objeto> call, Response<Objeto> response) {

            }

            @Override
            public void onFailure(Call<Objeto> call, Throwable t) {

            }
        }); */
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
        updaterefresh = (SwipeRefreshLayout) getView().findViewById(R.id.statsSwipeRefreshLayout);
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

        updateStats(User.getInstance().getUsername());

        updaterefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        updateStats(User.getInstance().getUsername());
                        updaterefresh.setRefreshing(false);
                    }
                }
        );

        playbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(); GameActivity
            }
        });
    }
}
