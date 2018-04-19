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
import com.farm.game.states.GameStateManager;

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

    public void handleTouch(float x, float y, GameStateManager gsm) {
        $grid.handleTouch(x, y, gsm);
    }

    public void gridIndexesTouched(int rowIndex, int columnIndex, GameStateManager gsm) {
        $grid.gridIndexesTouched(rowIndex, columnIndex, gsm);
    }

    public void setGrid(Grid grid) {
        $grid = grid;
    }

    public Grid getGrid() {
        return $grid;
    }

    public void updateGrid() {
        $grid.updateGrid();
    }

    public void defaultGrid() {
        System.out.println("defaultGrid");

        $grid = new Grid();
        $grid.insertIntoPosition(new FarmLand(), 3, 2);
        $grid.insertIntoPosition(new FarmLand(), 3, 3);
        $grid.insertIntoPosition(new FarmLand(), 3, 4);
        $grid.insertIntoPosition(new FarmLand(), 4, 2);
        $grid.insertIntoPosition(new FarmLand(), 4, 3);
        $grid.insertIntoPosition(new FarmLand(), 4, 4);
        $grid.insertIntoPosition(new FarmLand(), 5, 2);
        $grid.insertIntoPosition(new FarmLand(), 5, 3);
        $grid.insertIntoPosition(new FarmLand(), 5, 4);
        $grid.insertIntoPosition(new FarmBuilding(), 4, 8);
        $grid.insertIntoPosition(new FarmField(FarmFieldStatusEnum.Adults, FarmFieldTypeEnum.Chicken, 3), 2, 8);

        saveGridOnlyToJSON();
    }

    private void saveGridOnlyToJSON() {
        try {
            Preferences prefs = Gdx.app.getPreferences("My Preferences");
            Json json = new Json();

            json.setElementType(Grid.class, "$grid", FarmObject.class);
            String jsonFarmLandscapeString = json.prettyPrint(FarmGameMain.landscape.getGrid());
            prefs.putString("farmLandscape", jsonFarmLandscapeString );

            prefs.flush();
        } catch (Throwable e) {
            Gdx.app.log("error saving file", e.getMessage());
        }
    }
}
