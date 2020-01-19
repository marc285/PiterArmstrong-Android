package com.dsaproject.piterarmstrong_android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dsaproject.piterarmstrong_android.models.User;

import java.util.List;

public class UsersRVAdapter extends RecyclerView.Adapter<UsersRVAdapter.UsersRVViewHolder> {
    //Adapter for the RecyclerView Data to be displayed

    private List<User> usrlist;

    public UsersRVAdapter(List<User> usrlist) {
        this.usrlist = usrlist;
    }

    @NonNull
    @Override
    public UsersRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_rv_item, parent, false);
        return new UsersRVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersRVViewHolder holder, int position) {
        //Sets up the communication between Adapter and View Holder classes
        holder.usrname.setText(usrlist.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return usrlist.size();
    }

    public class UsersRVViewHolder extends RecyclerView.ViewHolder {
        //Views of each RecyclerView item
        TextView usrname;

        public UsersRVViewHolder(@NonNull View itemView) {
            super(itemView);
            usrname = itemView.findViewById(R.id.RVuserTextView);
        }
    }
}
