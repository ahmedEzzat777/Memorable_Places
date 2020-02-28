package com.example.memorableplaces.Data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "places")
public class Place {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "address")
    public String address;
    @ColumnInfo(name = "latitude")
    public double latitude;
    @ColumnInfo(name = "longitude")
    public double longitude;

    public Place(int id,String address,double latitude,double longitude){
        this.id =id;
        this.address=address;
        this.latitude=latitude;
        this.longitude=longitude;
    }
    @Ignore
    public Place(String address,double latitude,double longitude){
        this.address=address;
        this.latitude=latitude;
        this.longitude=longitude;
    }
}
