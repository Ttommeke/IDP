package com.farm.game;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication implements AndroidEnvironmentCallback {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
}
