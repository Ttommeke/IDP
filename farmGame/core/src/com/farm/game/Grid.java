package com.farm.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.farm.game.sprites.FarmObject;
import com.farm.game.sprites.GridSquare;
import com.farm.game.sprites.InvisibleChildSquare;
import com.farm.game.sprites.InvisibleGridSquare;

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

    public void insertIntoPosition(FarmObject farmObject, int row, int column) {
        int amountOfCells = farmObject.getAmountOfCells();
        for(int i=0; i<amountOfCells; i++){
            for (int j=0; j<amountOfCells; j++) {
                $grid[row+i][column+j] = new InvisibleChildSquare(row, column);
            }
        }
        $grid[row][column] = farmObject;
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
