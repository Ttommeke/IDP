package com.farm.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.farm.game.spriteData.FarmAnimal;
import com.farm.game.spriteData.FarmAnimalChicken;
import com.farm.game.sprites.FarmBuilding;
import com.farm.game.sprites.FarmField;
import com.farm.game.spriteData.FarmFieldStatusEnum;
import com.farm.game.spriteData.FarmFieldTypeEnum;
import com.farm.game.sprites.FarmLand;
import com.farm.game.sprites.FarmObject;
import com.farm.game.sprites.FarmTree;
import com.farm.game.spriteData.FarmTreeFruitTypeEnum;
import com.farm.game.states.GameStateManager;

import java.util.ArrayList;

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

    public void handleDelete(float x, float y, GameStateManager gsm) {
        $grid.handleDelete(x, y, gsm);
    }

    public void gridIndexesTouched(int rowIndex, int columnIndex, GameStateManager gsm) {
        $grid.gridIndexesTouched(rowIndex, columnIndex, gsm);
    }

    public void gridIndexesConfirmForDeletion(int rowIndex, int columnIndex, GameStateManager gsm) {
        $grid.gridIndexesConfirmForDeletion(rowIndex, columnIndex, gsm);
    }

    public void deleteIndexes(int rowIndex, int columnIndex) {
        $grid.deleteFromPosition(rowIndex, columnIndex);
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
        $grid.insertIntoPosition(new FarmTree(FarmTreeFruitTypeEnum.Apple), 1, 2);
        $grid.insertIntoPosition(new FarmTree(FarmTreeFruitTypeEnum.Apple), 1, 3);
        $grid.insertIntoPosition(new FarmTree(FarmTreeFruitTypeEnum.Raspberry), 1, 4);
        ArrayList<FarmAnimal> farmAnimals = new ArrayList<>();
        farmAnimals.add(new FarmAnimalChicken());
        farmAnimals.add(new FarmAnimalChicken());
        farmAnimals.add(new FarmAnimalChicken());

        //$grid.insertIntoPosition(new FarmField(FarmFieldStatusEnum.Children, FarmFieldTypeEnum.Chicken, farmAnimals), 2, 8);
        $grid.insertIntoPosition(new FarmField(), 2, 8);

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
