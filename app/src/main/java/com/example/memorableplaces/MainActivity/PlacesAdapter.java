package com.example.memorableplaces.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memorableplaces.Data.MainDbTask;
import com.example.memorableplaces.MapsActivity.MapsActivity;
import com.example.memorableplaces.Model.Places;
import com.example.memorableplaces.R;

import java.util.ArrayList;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder> {
    private Activity _activity;
    private ArrayList<Places.Place> _places;

    public PlacesAdapter(Activity context, ArrayList<Places.Place> places)
    {
        _activity = context;
        _places = places;
    }
    @NonNull
    @Override
    public PlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_place,parent,false);
        PlacesViewHolder placesViewHolder = new PlacesViewHolder(v);
        return placesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlacesViewHolder holder, int position) {
        if(position == getItemCount()-1) {
            holder.textView.setText("Add Places");
            holder.textView.setTextColor(Color.GREEN);
            holder.deleteBtn.setVisibility(View.INVISIBLE);
            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(_activity, MapsActivity.class);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    _activity.startActivityForResult(intent,1);
                }
            });
        } else {
            holder.textView.setText(_places.get(position).Address);
            holder.textView.setTextColor(Color.BLUE);
            final int i = position;
            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(_activity, MapsActivity.class);
                    intent.putExtra("lat",_places.get(i).Lat);
                    intent.putExtra("long",_places.get(i).Long);
                    intent.putExtra("address",_places.get(i).Address);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    _activity.startActivityForResult(intent,1);
                }
            });
            if(!_places.get(position).Modified) {
                holder.deleteBtn.setVisibility(View.VISIBLE);
                holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MainActivity) _activity).deletePlace(_places.get(i));
                    }
                });
            } else{
                holder.deleteBtn.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return _places.size()+1;
    }


    public class PlacesViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout parentLayout;
        TextView textView;
        ImageView deleteBtn;
        public PlacesViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.placeParentaLayout);
            textView = itemView.findViewById(R.id.placeTextView);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }
}
