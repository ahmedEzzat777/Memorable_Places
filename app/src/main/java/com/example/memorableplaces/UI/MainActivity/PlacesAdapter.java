package com.example.memorableplaces.UI.MainActivity;

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

import com.example.memorableplaces.UI.MapsActivity.MapsActivity;
import com.example.memorableplaces.Model.Places;
import com.example.memorableplaces.R;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder> {
    private Activity _activity;
    private Places m_places;

    public PlacesAdapter(Activity context, Places places)
    {
        _activity = context;
        m_places = places;
    }
    @NonNull
    @Override
    public PlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_place,parent,false);
        return new PlacesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PlacesViewHolder holder, int position) {
        if(position == getItemCount()-1) {
            holder.textView.setText("Add Places");
            holder.textView.setTextColor(Color.GREEN);
            holder.deleteBtn.setVisibility(View.INVISIBLE);
            holder.parentLayout.setOnClickListener(v -> {
                Intent intent = new Intent(_activity, MapsActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                _activity.startActivityForResult(intent,1);
            });
        } else {
            holder.textView.setText(m_places.getPlaces().get(position).Address);
            holder.textView.setTextColor(Color.BLUE);
            final int i = position;
            holder.parentLayout.setOnClickListener(v -> {
                Intent intent = new Intent(_activity, MapsActivity.class);
                intent.putExtra("lat", m_places.getPlaces().get(i).Lat);
                intent.putExtra("long", m_places.getPlaces().get(i).Long);
                intent.putExtra("address", m_places.getPlaces().get(i).Address);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                _activity.startActivityForResult(intent,1);
            });
            if(!m_places.getPlaces().get(position).Modified) {
                holder.deleteBtn.setVisibility(View.VISIBLE);
                holder.deleteBtn.setOnClickListener(v -> ((MainActivity) _activity).deletePlace(m_places.getPlaces().get(i)));
            } else{
                holder.deleteBtn.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return m_places.getPlaces().size()+1;
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
