package com.farm.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class FarmObject {
    Texture $texture;
    private int $amountOfCells;

    FarmObject(int amountOfCells, Texture texture){
        $amountOfCells = amountOfCells;
        $texture = texture;
    }

    public int getAmountOfCells() {
        return $amountOfCells;
    }

    public Texture getTexture() {
        return $texture;
    }
}
