package com.dsaproject.piterarmstrong_android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dsaproject.piterarmstrong_android.models.Objeto;

import java.util.List;

public class ObjectsRVAdapter extends RecyclerView.Adapter<ObjectsRVAdapter.ObjectsRVViewHolder> {
    //Adapter for the RecyclerView Data to be displayed

    private List<Objeto> objlist;

    public ObjectsRVAdapter(List<Objeto> objlist) {
        this.objlist = objlist;
    }

    @NonNull
    @Override
    public ObjectsRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.objects_rv_item, parent, false);
        return new ObjectsRVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ObjectsRVViewHolder holder, int position) {
        //Sets up the communication between Adapter and View Holder classes
        holder.objecttxt.setText(objlist.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        return objlist.size();
    }

    public class ObjectsRVViewHolder extends RecyclerView.ViewHolder {
        //Views of each RecyclerView item
        TextView objecttxt;

        public ObjectsRVViewHolder(@NonNull View itemView) {
            super(itemView);
            objecttxt = itemView.findViewById(R.id.objectTextView);
        }
    }
}
