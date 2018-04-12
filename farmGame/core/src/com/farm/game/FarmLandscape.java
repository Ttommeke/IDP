package com.farm.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.farm.game.sprites.FarmBuilding;
import com.farm.game.sprites.FarmField;
import com.farm.game.sprites.FarmFieldStatusEnum;
import com.farm.game.sprites.FarmFieldTypeEnum;
import com.farm.game.sprites.FarmLand;
import com.farm.game.sprites.FarmObject;

/**
 * This class contains all objects on the landscape of the farm.
 */
public class FarmLandscape {
    private Grid $grid;

    public FarmLandscape() {
        $grid = new Grid();
    }

    public void drawObjects(SpriteBatch sb) {
        $grid.drawObjects(sb);
    }

    public void loadFromJSON() {
        try {
            Preferences prefs = Gdx.app.getPreferences("My Preferences");

            Json json = new Json();
            String jsonString = prefs.getString("farmLandscape");
            if(jsonString == null || jsonString.equals("") || jsonString.equals("null")) {
                defaultGrid();
            } else {
                //System.out.println("loaded: " + jsonString);
                $grid = json.fromJson(Grid.class, jsonString);
            }
            //defaultGrid();
        } catch (Throwable e) {
            Gdx.app.log("error loading file", e.getMessage());
        }
    }

    public void saveToJSON() {
        try {
            Preferences prefs = Gdx.app.getPreferences("My Preferences");

            Json json = new Json();
            json.setElementType(Grid.class, "$grid", FarmObject.class);
            String jsonString = json.prettyPrint($grid);

            prefs.putString("farmLandscape", jsonString);

            prefs.flush();
        } catch (Throwable e) {
            Gdx.app.log("error saving file", e.getMessage());
        }
    }

    private void defaultGrid() {
        System.out.println("defaultGrid");

        $grid.insertIntoPosition(new FarmLand(), 3, 4);
        $grid.insertIntoPosition(new FarmLand(), 3, 5);
        $grid.insertIntoPosition(new FarmLand(), 3, 6);
        $grid.insertIntoPosition(new FarmLand(), 4, 4);
        $grid.insertIntoPosition(new FarmLand(), 4, 5);
        $grid.insertIntoPosition(new FarmLand(), 4, 6);
        $grid.insertIntoPosition(new FarmLand(), 5, 4);
        $grid.insertIntoPosition(new FarmLand(), 5, 5);
        $grid.insertIntoPosition(new FarmLand(), 5, 6);
        $grid.insertIntoPosition(new FarmBuilding(), 8, 4);
        $grid.insertIntoPosition(new FarmField(FarmFieldStatusEnum.Adults, FarmFieldTypeEnum.Chicken), 8, 2);

        saveToJSON();
    }
}
