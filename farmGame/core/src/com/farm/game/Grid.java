package com.farm.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.farm.game.sprites.InvisibleGridSquare;
import com.farm.game.sprites.FarmObject;
import com.farm.game.sprites.GridSquare;

public class Grid {
    private FarmObject[][] $grid;
    private final int $cellSize = 128;

    public Grid() {
        $grid = new FarmObject[13][8];
        for(int i=0; i<$grid.length; i++) {
            for(int j=0; j<$grid[i].length; j++) {
                $grid[i][j] = new GridSquare();
            }
        }
    }

    public void insertIntoPosition(FarmObject farmObject, int column, int row) {
        int amountOfCells = farmObject.getAmountOfCells();
        for(int i=0; i<amountOfCells; i++){
            for (int j=0; j<amountOfCells; j++) {
                $grid[column+i][row+j] = new InvisibleGridSquare();
            }
        }
        $grid[column][row] = farmObject;
    }

    public void drawObjects(SpriteBatch sb) {
        for(int i=0; i<$grid.length; i++) {
            for(int j=0; j<$grid[i].length; j++) {
                sb.draw($grid[i][j].getTexture(), i*$cellSize, j*$cellSize,
                        $grid[i][j].getAmountOfCells() * $cellSize, $grid[i][j].getAmountOfCells() * $cellSize);
            }
        }
    }
}
