package com.farm.game;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;

public class HTTPConnection {
    private static final String BASE_URL = "https://roads.googleapis.com/v1/";
    private static final String API_KEY = "AIzaSyAu5tIl8fgC0nEAE4Da38DRw831Xxxeuls";
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
            finalUrl = BASE_URL + "nearestRoads?points=";
            for(int i = 0; i < points.size(); i++){
                finalUrl += points.get(i).latitude + "," + points.get(i).longitude;

                if(i + 1 != points.size()){
                    finalUrl += "|";
                }
            }
            finalUrl += "&key=" + API_KEY;
        }

        return finalUrl;
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

    public void get(String url, final Callback callback) {
        client.get(this.context, url, new JsonHttpResponseHandler() {
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

