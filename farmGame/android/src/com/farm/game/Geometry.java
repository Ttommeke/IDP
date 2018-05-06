package com.farm.game;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public class Geometry {
    public static float convertMetersToLatLngDistance(int meter){
        return (float)meter/111111;
    }
    public static float distanceBetweenTwoLatLng(LatLng latLng1, LatLng latLng2){
        float results[] = new float[1];
        Location.distanceBetween(latLng1.latitude,
                latLng1.longitude,
                latLng2.latitude,
                latLng2.longitude,
                results);

        return results[0];
    }
}
