package com.farm.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.farm.game.Grid;
import com.farm.game.sprites.FarmBuilding;
import com.farm.game.sprites.FarmLandStatusEnum;
import com.farm.game.sprites.FarmLand;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * This class contains all objects on the landscape of the farm.
 */
public class FarmLandscape {
    private FarmBuilding farmBuilding;
    private ArrayList<FarmLand> farmLands;
    private Grid $grid;

    public FarmLandscape() {
        farmLands = new ArrayList<FarmLand>();
        $grid = new Grid();
    }

    public void drawObjects(SpriteBatch sb) {
        $grid.drawObjects(sb);
    }

    public void loadFromJSON() {
        $grid.insertIntoPosition(new FarmLand(FarmLandStatusEnum.Unplanted), 3, 4);
        $grid.insertIntoPosition(new FarmLand(FarmLandStatusEnum.Unplanted), 3, 5);
        $grid.insertIntoPosition(new FarmLand(FarmLandStatusEnum.Unplanted), 3, 6);
        $grid.insertIntoPosition(new FarmLand(FarmLandStatusEnum.Unplanted), 4, 4);
        $grid.insertIntoPosition(new FarmLand(FarmLandStatusEnum.Unplanted), 4, 5);
        $grid.insertIntoPosition(new FarmLand(FarmLandStatusEnum.Unplanted), 4, 6);
        $grid.insertIntoPosition(new FarmLand(FarmLandStatusEnum.Unplanted), 5, 4);
        $grid.insertIntoPosition(new FarmLand(FarmLandStatusEnum.Unplanted), 5, 5);
        $grid.insertIntoPosition(new FarmLand(FarmLandStatusEnum.Unplanted), 5, 6);
        $grid.insertIntoPosition(new FarmBuilding(), 8, 4);
    }

    public void saveToJSON() {
        Gson gson = new Gson();
        StringBuilder sb = new StringBuilder();
        for(FarmLand farmland: farmLands) {
            sb.append(gson.toJson(farmland));
        }
    }
}
