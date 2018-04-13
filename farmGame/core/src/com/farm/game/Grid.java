package com.farm.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.farm.game.sprites.FarmObject;
import com.farm.game.sprites.GridSquare;
import com.farm.game.sprites.InvisibleChildSquare;
import com.farm.game.sprites.InvisibleGridSquare;
import com.farm.game.states.GameStateManager;

public class Grid {
    private FarmObject[][] $grid;
    private Rectangle[][] $gridRectangle;
    private final int $cellSize = 128;

    public Grid() {
        $grid = new FarmObject[8][13];
        $gridRectangle = new Rectangle[8][13];

        for(int i=0; i<$grid.length; i++) {
            for(int j=0; j<$grid[i].length; j++) {
                $grid[i][j] = new GridSquare();
                $gridRectangle[i][j] = new Rectangle(128*j, (FarmGameMain.HEIGHT-128) - 128*i , 128, 128);
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
                sb.draw($grid[i][j].getTexture(), j*$cellSize, i*$cellSize,
                        $grid[i][j].getAmountOfCells() * $cellSize, $grid[i][j].getAmountOfCells() * $cellSize);
            }
        }
    }

    public void handleTouch(float x, float y, GameStateManager gsm) {
        for(int i=0; i<$gridRectangle.length; i++) {
            for(int j=0; j<$gridRectangle[i].length; j++) {
                if($gridRectangle[i][j].contains(x, y)) {
                    $grid[i][j].handleTouch(gsm);
                }
            }
        }
    }

    public void gridIndexesTouched(int rowIndex, int columnIndex, GameStateManager gsm) {
        $grid[rowIndex][columnIndex].handleTouch(gsm);
    }
}
