package com.dsaproject.piterarmstrong_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dsaproject.piterarmstrong_android.models.User;
import com.dsaproject.piterarmstrong_android.services.UserManagerService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SettingsFragment extends Fragment {

    private EditText currentpwdtext;
    private EditText newpwdtext;
    private Button changepwdbutton;
    private Button logoutbutton;
    private Button deleteuser;

    private UserManagerService usersAPI;

    //---------------------------------------------------------API Methods------------------------------------------------------------//
    public void changePassword(final String newpwd){
        //Method updateUser() of the Users API Interface

        Call<Void> call = usersAPI.updateUser(new User(User.getInstance().getUsername(), newpwd, User.getInstance().getHealth(), User.getInstance().getDefense(), User.getInstance().getAttack(), User.getInstance().getMoney(), User.getInstance().getPieces(), User.getInstance().getScreen()), User.getInstance().getUsername());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    //We "fill" the logged User instance
                    User loggedUsr = User.getInstance();
                    loggedUsr.setPassword(newpwd);
                    //CHANGE SHAREDPREFERENCES !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
                    Toast.makeText(getActivity(), "Your password has been changed.", Toast.LENGTH_LONG).show();
                }
                else{
                    if(response.code() == 400)
                        Toast.makeText(getActivity(), "Error changing password: " + response.code() + "\nBad Event (Error in parameter's format)", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getActivity(), "Error changing password: " + response.code() + "\nInternal Server Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void deleteUser(){
        //Method deleteUser() of the Users API Interface

        Call<Void> call = usersAPI.deleteUser(User.getInstance());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), "Your account has been deleted.", Toast.LENGTH_LONG).show();
                    //CHANGE SHAREDPREFERENCES !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
                    //User.getInstance().closeInstance();
                    //ObjetoList.getInstance().closeInstance();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                else{
                    Toast.makeText(getActivity(), "Error deleting User: " + response.code() + "\nInternal Server Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    //--------------------------------------------------------------------------------------------------------------------------------//



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentpwdtext = (EditText) getView().findViewById(R.id.currentPwdEditText);
        newpwdtext = (EditText) getView().findViewById(R.id.newPwdEditText);
        changepwdbutton = (Button) getView().findViewById(R.id.changePwdButton);
        logoutbutton = (Button) getView().findViewById(R.id.logoutButton);
        deleteuser = (Button) getView().findViewById(R.id.deleteUserButton);

        Retrofit retrofitinstance = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/dsaApp/") //Later on we will put the server's IP address, meanwhile in localhost
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        usersAPI = retrofitinstance.create(UserManagerService.class);

        changepwdbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MyActivity", "your current password is:" + User.getInstance().getPassword());
                if(currentpwdtext.getText().toString().equals("") || newpwdtext.getText().toString().equals(""))
                    Toast.makeText(getActivity(), "You must type your old (current) and new password", Toast.LENGTH_LONG).show();
                else if(currentpwdtext.getText().toString().equals(User.getInstance().getPassword())){
                    changePassword(newpwdtext.getText().toString());
                }
                else
                    Toast.makeText(getActivity(), "Error. Your old (current) password is incorrect.", Toast.LENGTH_LONG).show();
            }
        });

        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //CHANGE SHAREDPREFERENCES !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
                    //User.getInstance().closeInstance();
                    //ObjetoList.getInstance().closeInstance();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                catch (Exception e){
                    Toast.makeText(getActivity(), "Error. Couldn't log out", Toast.LENGTH_LONG).show();
                }
            }
        });

        deleteuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentpwdtext.getText().toString().equals(User.getInstance().getPassword())) {
                    deleteUser();
                }
                else
                    Toast.makeText(getActivity(), "Error. Incorrect Password.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
