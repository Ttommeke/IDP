package com.farm.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class FarmObject implements Json.Serializable{
    protected Texture $texture;
    protected int $amountOfCells;

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

    public void handleTouch() { }

    @Override
    public void write(Json json) { }

    @Override
    public void read(Json json, JsonValue jsonData) { }
}
