package com.farm.game;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        RestService restService = new RestService("http://192.168.0.196/");
        Log.d("[TOKEN]", "onTokenRefresh: " + FirebaseInstanceId.getInstance().getToken());
        Log.d("[TOKEN]", restService.putRequest("/api/notificationservice/updatemyfarmdeviceid/", "{ \"deviceId\": \"" + FirebaseInstanceId.getInstance().getToken() + "\" }"));
    }
}
