package com.farm.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.farm.game.Assets;
import com.farm.game.states.GameStateManager;

public class FarmObject implements Json.Serializable{
    protected Texture $texture;
    protected int $amountOfCells;

    public FarmObject() {
        $amountOfCells = 1;
        $texture = Assets.gridSquareTexture;
    }

    public FarmObject(int amountOfCells, Texture texture){
        $amountOfCells = amountOfCells;
        $texture = texture;
    }

    public int getAmountOfCells() {
        return $amountOfCells;
    }

    public Texture getTexture() {
        return $texture;
    }

    public void update() {}

    public void handleTouch(GameStateManager gsm) { }

    public void confirmDelete(GameStateManager gsm, int rowIndex, int columnIndex) { }

    @Override
    public void write(Json json) { }

    @Override
    public void read(Json json, JsonValue jsonData) { }
}
