package com.farm.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;
import com.farm.game.DataClasses.User;

public class Settings {
    private static boolean $soundEnabled = true;
    private static RestService $restService;
    private static Json $json = new Json();

    public static void load() {
        try {
            $restService = new RestService();
            Preferences prefs = Gdx.app.getPreferences("My Preferences");

            $soundEnabled = prefs.getBoolean("soundEnabled");
        } catch (Throwable e) {
            Gdx.app.log("error loading file", e.getMessage());
        }
    }

    private static void save () {
        try {
            Preferences prefs = Gdx.app.getPreferences("My Preferences");

            prefs.putBoolean("soundEnabled", $soundEnabled);

            prefs.flush();
        } catch (Throwable e) {
            Gdx.app.log("error saving file", e.getMessage());
        }
    }

    public static boolean isSoundEnabled() {
        return $soundEnabled;
    }

    public static void flipSound() {
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

    public static boolean login(String username, String password) {
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
