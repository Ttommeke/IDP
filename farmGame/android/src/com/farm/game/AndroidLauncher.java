package com.farm.game;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.utils.Json;
import com.farm.game.DataClasses.User;
import com.farm.game.states.FarmState;
import com.farm.game.states.GameStateManager;

import org.json.JSONArray;
import org.json.JSONObject;

public class AndroidLauncher extends AndroidApplication implements AndroidEnvironmentCallback {
    private HTTPConnection httpConnection;

    @Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        httpConnection = new HTTPConnection(null);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new FarmGameMain(this), config);
	}

    @Override
    public void startMapsActivity() {
        Intent intent = new Intent(this, MapsActivity.class);
        Preferences prefs = Gdx.app.getPreferences("My Preferences");
        String json = prefs.getString("savedResources");
        intent.putExtra("savedResources", json);
        this.startActivity(intent);
    }

    @Override
    public void login(String username, String password) {
        Intent intent = new Intent(this, RequestHandlingActivity.class);
        intent.putExtra("action", "login");
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        this.startActivity(intent);
    }

    @Override
    public void setToken(String token) {
        httpConnection.setTokenHeader(token);
    }

    @Override
    public void saveState() {
        Intent intent = new Intent(this, RequestHandlingActivity.class);
        intent.putExtra("action", "saveState");
        this.startActivity(intent);
    }

    @Override
    public void loadState() {
        Intent intent = new Intent(this, RequestHandlingActivity.class);
        intent.putExtra("action", "loadState");
        this.startActivity(intent);
    }


}
