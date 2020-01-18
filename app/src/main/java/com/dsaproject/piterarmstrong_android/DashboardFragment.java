package com.dsaproject.piterarmstrong_android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.dsaproject.piterarmstrong_android.models.User;

public class DashboardFragment extends Fragment {

    //private User loggedusr;

/*    public DashboardFragment(){
        //Constructor gets the logged User when invoked
        //this.loggedusr = User.getInstance();
    }*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //Initialize and fill all the Dashboard fields
        TextView usertxt = (TextView) getView().findViewById(R.id.userTextView);
        TextView healthtxt = (TextView) getView().findViewById(R.id.healthTextView);
        TextView defensetxt = (TextView) getView().findViewById(R.id.defenseTextView);
        TextView attacktxt = (TextView) getView().findViewById(R.id.attackTextView);
        TextView piecestxt = (TextView) getView().findViewById(R.id.piecesTextView);
        TextView moneytxt = (TextView) getView().findViewById(R.id.moneyTextView);
        RecyclerView objectsRV = (RecyclerView) getView().findViewById(R.id.objectsRecyclerView);
        Button playbtn = (Button) getView().findViewById(R.id.playButton);

        usertxt.setText(User.getInstance().getUsername());
        healthtxt.setText(User.getInstance().getHealth());
        defensetxt.setText(User.getInstance().getDefense());
        attacktxt.setText(User.getInstance().getAttack());
        piecestxt.setText(User.getInstance().getPieces());
        moneytxt.setText(User.getInstance().getMoney());

        //RecyclerView

        playbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(); GameActivity
            }
        });
    }
}
