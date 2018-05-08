package com.farm.game;

import android.content.Context;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;
import com.google.android.gms.maps.model.LatLng;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class HTTPConnection {
    private static final String GOOGLE_BASE_URL = "https://roads.googleapis.com/v1/";
    private static final String GOOGLE_API_KEY = "AIzaSyAu5tIl8fgC0nEAE4Da38DRw831Xxxeuls";
    //private static final String BASE_URL = "http://192.168.0.213"; //HOME
    private static final String BASE_URL = "http://192.168.16.117"; //EDM
    private AsyncHttpClient client;
    private Context context;

    public HTTPConnection(Context context){
        this.client = new AsyncHttpClient();
        this.context = context;
    }

    public void getPointsSnappedToRoads(ArrayList<LatLng> points, Callback callback){
        String url = generateNearestRoadsUrl(points);

        if(url != null){
            get(url, callback);
        }
    }

    private String generateNearestRoadsUrl(ArrayList<LatLng> points){
        String finalUrl = null;
        if(points != null && points.size() > 0){
            finalUrl = GOOGLE_BASE_URL + "nearestRoads?points=";
            for(int i = 0; i < points.size(); i++){
                finalUrl += points.get(i).latitude + "," + points.get(i).longitude;

                if(i + 1 != points.size()){
                    finalUrl += "|";
                }
            }
            finalUrl += "&key=" + GOOGLE_API_KEY;
        }

        return finalUrl;
    }

    public void setTokenHeader(String token) {
        this.client.removeHeader("token");
        this.client.addHeader("token", token);
    }

    public void login(String email, String password, Callback callback){
        JSONObject json_params = new JSONObject();
        StringEntity entity = null;
        try{
            json_params.put("email", email);
            json_params.put("password", password);
            entity = new StringEntity(json_params.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            System.out.println("whoopydiewhoop");
        } catch (Exception ex){
            callback.taskCompleted(999, null);
        }

        post(BASE_URL + "/api/accountservice/login", entity, callback);
    }

    public void saveState(final Callback callback) {
        JSONObject json_params = new JSONObject();
        StringEntity entity = null;
        try{
            json_params.put("ownerId", FarmGameMain.settings.getUserId());
            Json json = new Json();

            // Save farmLandscape & inventory
            JSONArray jsons = new JSONArray();
            jsons.put(0, FarmGameMain.landscape.getGrid());
            jsons.put(1, FarmGameMain.inventory);
            /*jsons.put(0, json.prettyPrint(FarmGameMain.landscape.getGrid()));
            jsons.put(1, json.prettyPrint(FarmGameMain.inventory));*/
            Preferences prefs = Gdx.app.getPreferences("My Preferences");
            jsons.put(2, prefs.getString("savedResources"));

            json_params.put("savegame", jsons);
            entity = new StringEntity(json_params.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        } catch (Exception ex){
            callback.taskCompleted(999, null);
        }

        post(BASE_URL + "/api/savegameservice/addsavegame", entity, callback);
    }

    public void loadState(final Callback callback) {
        String id = FarmGameMain.settings.getUserId();
        get(BASE_URL + "/api/savegameservice/getsavegame/ownerId" + id, callback);
    }

    private void post(String url, HttpEntity entity, final Callback callback){
        client.post(this.context, url, entity, "application/json", new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                callback.taskCompleted(statusCode, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if(errorResponse != null)
                    callback.taskCompleted(statusCode, errorResponse);
                else
                    callback.taskCompleted(statusCode, null);
            }
        });
    }

    private void get(String url, final Callback callback) {
        client.get(this.context,url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println("whoopydiewhoop");
                callback.taskCompleted(statusCode, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if(errorResponse != null)
                    callback.taskCompleted(statusCode, errorResponse);
                else
                    callback.taskCompleted(statusCode, null);
            }
        });
    }

    private void put(String url, HttpEntity entity, final Callback callback){
        client.put(this.context, url, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                callback.taskCompleted(statusCode, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if(errorResponse != null)
                    callback.taskCompleted(statusCode, errorResponse);
                else
                    callback.taskCompleted(statusCode, null);
            }
        });
    }
}

