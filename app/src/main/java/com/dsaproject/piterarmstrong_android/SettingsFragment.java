package com.dsaproject.piterarmstrong_android;

import android.content.Context;
import android.os.Bundle;
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

public class SettingsFragment extends Fragment {

    private UserManagerService usersAPI;
    Context context;

    //---------------------------------------------------------API Methods------------------------------------------------------------//
    public void changePassword(final String newpwd){
        //Method updateUser() of the Users API Interface

        Call<Void> call = usersAPI.updateUser(new User(User.getInstance().getUsername(), newpwd, User.getInstance().getHealth(), User.getInstance().getDefense(), User.getInstance().getAttack(), User.getInstance().getMoney(), User.getInstance().getPieces()), User.getInstance().getUsername());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    //We "fill" the logged User instance
                    User loggedUsr = User.getInstance();
                    loggedUsr.setPassword(newpwd);
                    //CHANGE SHAREDPREFERENCES !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
                    Toast.makeText(context, "Your password has been changed.", Toast.LENGTH_LONG).show();
                }
                else{
                    if(response.code() == 400)
                        Toast.makeText(context, "Error changing password: " + response.code() + "\n Bad Event (Error in parameter's format)", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(context, "Error changing password: " + response.code() + "\n Internal Server Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void deleteUser(final String pwd, String usrname){
        //Method deleteUser() of the Users API Interface

        Call<Void> call = usersAPI.deleteUser(pwd, usrname);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "Your account has been deleted.", Toast.LENGTH_LONG).show();
                    //CHANGE SHAREDPREFERENCES !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
                    User.getInstance().closeInstance();
                    getActivity().finish();
                }
                else{
                    Toast.makeText(context, "Error deleting User: " + response.code() + "\n Internal Server Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
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

        final EditText currentpwdtext = (EditText) getView().findViewById(R.id.currentPwdEditText);
        final EditText newpwdtext = (EditText) getView().findViewById(R.id.newPwdEditText);
        final Button changepwdbutton = (Button) getView().findViewById(R.id.changePwdButton);
        Button logoutbutton = (Button) getView().findViewById(R.id.logoutButton);
        Button deleteuser = (Button) getView().findViewById(R.id.deleteUserButton);

        changepwdbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentpwdtext.getText().toString().equals("") || newpwdtext.getText().toString().equals(""))
                    Toast.makeText(context, "You must type your old (current) and new password", Toast.LENGTH_LONG).show();
                else if(currentpwdtext.equals(User.getInstance().getPassword())){
                    changePassword(newpwdtext.getText().toString());
                }
                else
                    Toast.makeText(context, "Error. Your old (current) password is incorrect.", Toast.LENGTH_LONG).show();
            }
        });

        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //CHANGE SHAREDPREFERENCES !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
                    User.getInstance().closeInstance();
                    getActivity().finish();
                }
                catch (Exception e){
                    Toast.makeText(context, "Error. Couldn't log out", Toast.LENGTH_LONG).show();
                }
            }
        });

        deleteuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentpwdtext.equals(User.getInstance().getPassword()))
                    deleteUser(currentpwdtext.getText().toString(), User.getInstance().getUsername());
                else
                    Toast.makeText(context, "Error. Incorrect Password.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
