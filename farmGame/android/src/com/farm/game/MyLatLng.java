package com.farm.game;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class MyLatLng {
    private Location location;
    private String placeId;
    private int originalIndex;

    public Location getLocation(){
        return this.location;
    }

    public void setLocation(Location location){
        this.location = location;
    }

    public String getPlaceId() {
        return this.placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public int getOriginalIndex() {
        return this.originalIndex;
    }

    public void setOriginalIndex(int originalIndex) {
        this.originalIndex = originalIndex;
    }

    public static MyLatLng fromJSONObject(JSONObject json){
        MyLatLng myLatLng = new MyLatLng();

        try{
            Location location = new Location();
            location.longitude = json.getJSONObject("location").getDouble("longitude");
            location.latitude = json.getJSONObject("location").getDouble("latitude");

            myLatLng.setLocation(location);
            myLatLng.setOriginalIndex(json.getInt("originalIndex"));
            myLatLng.setPlaceId(json.getString("placeId"));

        } catch(JSONException ex){
            Log.e("MyLatLng", ex.getMessage());
        }

        return myLatLng;
    }
}
