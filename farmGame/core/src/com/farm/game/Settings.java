package com.farm.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;
import com.farm.game.DataClasses.User;
import com.farm.game.sprites.FarmObject;
import com.farm.game.states.FarmState;
import com.farm.game.states.GameStateManager;

public class Settings {
    private static boolean $soundEnabled = true;
    private static Json $json = new Json();
    private static User $user;
    private static String $token;

    public Settings() {}

    public void load() {
        try {
            Preferences prefs = Gdx.app.getPreferences("My Preferences");

            $soundEnabled = prefs.getBoolean("soundEnabled");
            loadFromJSON();

        } catch (Throwable e) {
            Gdx.app.log("error loading file", e.getMessage());
        }
    }

    private void save () {
        try {
            Preferences prefs = Gdx.app.getPreferences("My Preferences");
            prefs.putBoolean("soundEnabled", $soundEnabled);

            prefs.flush();

            saveToJSON();

        } catch (Throwable e) {
            Gdx.app.log("error saving file", e.getMessage());
        }
    }

    public void loadFromJSON() {
        System.out.println("test load");
        try {
            //FarmGameMain.androidEnvironmentCallback.loadState();

            Preferences prefs = Gdx.app.getPreferences("My Preferences");

            // Load farmLandscape
            String jsonFarmLandscapeString = prefs.getString("farmLandscape");
            if(jsonFarmLandscapeString  == null || jsonFarmLandscapeString .equals("") || jsonFarmLandscapeString .equals("null")) {
                FarmGameMain.landscape.defaultGrid();
            } else {
                //System.out.println("loaded: " + jsonFarmLandscapeString);
                Grid grid = $json.fromJson(Grid.class, jsonFarmLandscapeString );
                FarmGameMain.landscape.setGrid(grid);
            }
            //FarmGameMain.landscape.defaultGrid();

            // Load inventory
            String jsonInventoryString = prefs.getString("inventory");
            if(jsonInventoryString  == null || jsonInventoryString .equals("") || jsonInventoryString .equals("null")) {
                FarmGameMain.inventory.defaultInventory();
            } else {
                //System.out.println("loaded: " + jsonString);
                FarmGameMain.inventory = $json.fromJson(Inventory.class, jsonInventoryString );
            }
            //FarmGameMain.inventory.defaultInventory();
        } catch (Throwable e) {
            Gdx.app.log("error loading file", e.getMessage());
        }
    }

    public void saveToJSON() {
        System.out.println("test save");
        try {
            //FarmGameMain.androidEnvironmentCallback.saveState();

            Preferences prefs = Gdx.app.getPreferences("My Preferences");

            // Save farmLandscape
            $json.setElementType(Grid.class, "$grid", FarmObject.class);
            String jsonFarmLandscapeString = $json.prettyPrint(FarmGameMain.landscape.getGrid());
            prefs.putString("farmLandscape", jsonFarmLandscapeString );

            // Save inventory
            String jsonInventoryString = $json.prettyPrint(FarmGameMain.inventory);
            prefs.putString("inventory", jsonInventoryString);

            prefs.flush();
        } catch (Throwable e) {
            Gdx.app.log("error saving file", e.getMessage());
        }
    }

    public boolean isSoundEnabled() {
        return $soundEnabled;
    }

    public void flipSound() {
        $soundEnabled = !$soundEnabled;
        if ($soundEnabled) {
            // play general music if any
            // Assets.whelp.play();
        } else {
            // stop general music if any
            // Assets.whelp.stop();
        }
        save();
    }

    public void login(String username, String password, GameStateManager gsm) {
        System.out.println("login in progress");
        try {
            Preferences prefs = Gdx.app.getPreferences("My Preferences");

            // Load user & token
            String jsonUserString = prefs.getString("user");
            String token = prefs.getString("token");

            if((jsonUserString == null || jsonUserString.equals("") || jsonUserString.equals("null")) &&
                    token == null || token.equals("") || token.equals("null")) {
                FarmGameMain.androidEnvironmentCallback.login(username, password, gsm);
            } else {
                $user = $json.fromJson(User.class, jsonUserString);
                $token = token;
                FarmGameMain.androidEnvironmentCallback.setToken($token);
                gsm.set(new FarmState(gsm));
            }
        } catch (Throwable e) {
            Gdx.app.log("error loading file", e.getMessage());
        }
    }

    public void setUser(User user, String token) {
        $user = user;
        $token = token;
        try {
            Preferences prefs = Gdx.app.getPreferences("My Preferences");

            // Save user
            String jsonUserString = $json.prettyPrint(user);
            prefs.putString("user", jsonUserString );

            // Save token
            prefs.putString("token", token);

            prefs.flush();
        } catch (Throwable e) {
            Gdx.app.log("error saving file", e.getMessage());
        }
    }

    public String getUserId() {
        return $user.getId();
    }
}
