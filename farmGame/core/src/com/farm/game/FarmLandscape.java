package com.farm.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.farm.game.sprites.FarmBuilding;
import com.farm.game.sprites.FarmField;
import com.farm.game.sprites.FarmFieldStatusEnum;
import com.farm.game.sprites.FarmFieldTypeEnum;
import com.farm.game.sprites.FarmLand;

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

    public void handleTouch(float x, float y) {
        $grid.handleTouch(x, y);
    }

    public void gridIndexesTouched(int rowIndex, int columnIndex) {
        $grid.gridIndexesTouched(rowIndex, columnIndex);
    }

    public void setGrid(Grid grid) {
        $grid = grid;
    }

    public Grid getGrid() {
        return $grid;
    }

    public void defaultGrid() {
        System.out.println("defaultGrid");

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
        $grid.insertIntoPosition(new FarmField(FarmFieldStatusEnum.Adults, FarmFieldTypeEnum.Chicken), 2, 8);

        FarmGameMain.settings.saveToJSON();
    }
}
