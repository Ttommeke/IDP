package com.farm.game;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Resource {
    private ResourceType type;
    private Location location;

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Double latitude, Double longitude) {
        this.location = new Location();
        this.location.latitude = latitude;
        this.location.longitude = longitude;
    }

    public static Resource fromJSON(JSONObject json){
        Resource resource = null;
        try{
            resource = new Resource();
            resource.setType(ResourceType.getType(json.getInt("resourceType")));
            resource.setLocation(json.getDouble("latitude"), json.getDouble("longitude"));
        } catch(JSONException ex){
            Log.e("Resource", ex.getMessage());
        }
        return resource;
    }
}
