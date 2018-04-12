package com.farm.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;
import com.farm.game.DataClasses.User;
import com.farm.game.sprites.FarmObject;

public class Settings {
    private static boolean $soundEnabled = true;
    private static RestService $restService;
    private static Json $json = new Json();

    public Settings() {
        load();
    }

    public void load() {
        try {
            $restService = new RestService();
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
        try {
            Preferences prefs = Gdx.app.getPreferences("My Preferences");

            // Load farmLandscape
            String jsonFarmLandscapeString = prefs.getString("farmLandscape");
            if(jsonFarmLandscapeString  == null || jsonFarmLandscapeString .equals("") || jsonFarmLandscapeString .equals("null")) {
                FarmGameMain.landscape.defaultGrid();
            } else {
                //System.out.println("loaded: " + jsonString);
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
        try {
            Preferences prefs = Gdx.app.getPreferences("My Preferences");

            // Save farmLandscape
            $json.setElementType(Grid.class, "$grid", FarmObject.class);
            String jsonFarmLandscapeString = $json.prettyPrint(FarmGameMain.landscape.getGrid());
            prefs.putString("farmLandscape", jsonFarmLandscapeString );

            // Save inventory
            String jsonInventoryString = $json.prettyPrint(FarmGameMain.inventory);
            prefs.putString("inventory", jsonInventoryString );

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
        if($soundEnabled) {
            // play general music if any
            // Assets.whelp.play();
        } else {
            // stop general music if any
            // Assets.whelp.stop();
        }
        save();
    }

    public boolean login(String username, String password) {
        /*String input = "{\"email\":\"" + username + "\",\"password\":\""+ password +"\"}";
        String output = $restService.postRequest("plogin", input);
        if(output == null || output.equals("Invalid Credentials")) {
            return false;
        } else {
            User user = $json.fromJson(User.class, output);

            return true;
        }*/
        return true;
    }
}
