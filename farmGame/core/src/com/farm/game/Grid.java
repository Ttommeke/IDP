package com.farm.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.farm.game.sprites.FarmLand;
import com.farm.game.sprites.FarmObject;
import com.farm.game.sprites.GridSquare;
import com.farm.game.sprites.InvisibleChildSquare;
import com.farm.game.states.GameStateManager;

public class Grid {
    private FarmObject[][] $grid;
    private FarmObject[][] $backup;
    private Rectangle[][] $gridRectangle;
    private final int $cellSize = 128;
    private int previousRowIndex, previousColumnIndex;

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

    public void setBackup() {
        $backup = new FarmObject[8][13];

        for(int i=0; i<$grid.length; i++) {
            System.arraycopy($grid[i], 0, $backup[i], 0, $grid[i].length);
        }
    }

    public void restoreBackup() {
        System.out.println("reset");
        $grid = new FarmObject[8][13];

        for(int i=0; i<$backup.length; i++) {
            System.arraycopy($backup[i], 0, $grid[i], 0, $backup[i].length);
        }
    }

    public FarmObject getObject(float x, float y) {
        for(int i=0; i<$gridRectangle.length; i++) {
            for(int j=0; j<$gridRectangle[i].length; j++) {
                if($gridRectangle[i][j].contains(x, y)) {
                    if($grid[i][j].getClass() == InvisibleChildSquare.class) {
                        InvisibleChildSquare child = (InvisibleChildSquare) $grid[i][j];
                        previousRowIndex = child.getParentRow();
                        previousColumnIndex = child.getParentColumn();
                    } else {
                        previousRowIndex = i;
                        previousColumnIndex = j;
                    }
                    return $grid[previousRowIndex][previousColumnIndex];
                }
            }
        }
        return new GridSquare();
    }

    public boolean insertIntoPosition(FarmObject farmObject, int row, int column, boolean def) {
        int amountOfCells = farmObject.getAmountOfCells();
        boolean clear = true;

        if(row+amountOfCells > $grid.length || column+amountOfCells > $grid[0].length) {
            clear = false;
        } else {
            for (int i = 0; i < amountOfCells; i++) {
                for (int j = 0; j < amountOfCells; j++) {
                    if ($grid[row + i][column + j].getClass() != GridSquare.class) {
                        clear = false;
                    }
                }
            }
        }

        if(clear) {
            for(int i=0; i<amountOfCells; i++){
                for (int j=0; j<amountOfCells; j++) {
                    $grid[row+i][column+j] = new InvisibleChildSquare(row, column);
                }
            }
            $grid[row][column] = farmObject;
        }

        if(clear) {
            if(!def) {
                deleteFromPosition(previousRowIndex, previousColumnIndex);
            }
            previousRowIndex = row;
            previousColumnIndex = column;
        }

        return clear;
    }

    public boolean moveIntoPosition(float x, float y, FarmObject farmObject, boolean bought) {
        for(int i=0; i<$gridRectangle.length; i++) {
            for(int j=0; j<$gridRectangle[i].length; j++) {
                if($gridRectangle[i][j].contains(x, y)) {
                    return insertIntoPosition(farmObject, i, j, bought);
                }
            }
        }
        return false;
    }

    public void updateGrid() {
        for(int i=0; i<$grid.length; i++) {
            for(int j=0; j<$grid[i].length; j++) {
                $grid[i][j].update();
            }
        }
    }

    public void drawObjects(SpriteBatch sb) {
        for(int i=0; i<$grid.length; i++) {
            for(int j=0; j<$grid[i].length; j++) {
                sb.draw($grid[i][j].getTexture(), j*$cellSize, i*$cellSize,
                        $grid[i][j].getAmountOfCells() * $cellSize, $grid[i][j].getAmountOfCells() * $cellSize);

                if($grid[i][j].getClass() == FarmLand.class) {
                    FarmLand farmLand = (FarmLand) $grid[i][j];
                    switch (farmLand.getStatus()) {
                        case Growing:
                            sb.draw(Assets.timerTexture, j*$cellSize + 85, i*$cellSize + 85,40, 40);
                            break;
                        case FullyGrown:
                            sb.draw(Assets.acceptTexture, j*$cellSize + 85, i*$cellSize + 85,40, 40);
                            break;
                        case Rotten:
                            sb.draw(Assets.cancelTexture, j*$cellSize + 85, i*$cellSize + 85,40, 40);
                            break;
                    }
                }
            }
        }
    }

    public void drawPlacebleRect(int cells, ShapeRenderer shapeRenderer) {
        for(int i=0; i<$grid.length; i++) {
            for(int j=0; j<$grid[i].length; j++) {

                boolean clear = true;
                if(i+cells > $grid.length || j+cells > $grid[0].length) {
                    clear = false;
                } else {
                    for (int row = 0; row < cells; row++) {
                        for (int column = 0; column < cells; column++) {
                            if ($grid[row + i][column + j].getClass() != GridSquare.class) {
                                clear = false;
                            }
                        }
                    }
                }

                if(!clear) {
                    shapeRenderer.setColor(125/255f,0,0,0.3f);
                    shapeRenderer.rect(j*$cellSize, i*$cellSize, $cellSize, $cellSize);
                }
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

    public void handleDelete(float x, float y, GameStateManager gsm) {
        for(int i=0; i<$gridRectangle.length; i++) {
            for(int j=0; j<$gridRectangle[i].length; j++) {
                if($gridRectangle[i][j].contains(x, y)) {
                    $grid[i][j].confirmDelete(gsm, i, j);
                }
            }
        }
    }

    public void gridIndexesTouched(int rowIndex, int columnIndex, GameStateManager gsm) {
        $grid[rowIndex][columnIndex].handleTouch(gsm);
    }

    public void gridIndexesConfirmForDeletion(int rowIndex, int columnIndex, GameStateManager gsm) {
        $grid[rowIndex][columnIndex].confirmDelete(gsm, rowIndex, columnIndex);
    }

    public void deleteFromPosition(int rowIndex, int columnIndex) {
        FarmObject farmObject = $grid[rowIndex][columnIndex];
        int amountOfCells = farmObject.getAmountOfCells();
        for(int i=0; i<amountOfCells; i++){
            for (int j=0; j<amountOfCells; j++) {
                $grid[rowIndex+i][columnIndex+j] = new GridSquare();
            }
        }
    }
}
