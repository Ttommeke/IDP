package com.farm.game.spriteData;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public abstract class FarmAnimal implements Json.Serializable{
    protected Texture $texture;
    protected boolean $adult;
    protected boolean $productReady;
    protected long $timer;      // when not an adult -> time child purchased | when an adult -> time for production

    FarmAnimal(){}

    public Texture getTexture() {
        return $texture;
    }

    protected abstract void changeTexture();

    protected abstract void checkTimer();

    public void update() {
        checkTimer();
    }

    public void feedAnimal() {
        $timer = System.currentTimeMillis();
    }

    public long getTimer() {
        return $timer;
    }

    @Override
    public void write(Json json) {
        json.writeValue("adult", $adult);
        json.writeValue("timer", $timer);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        $adult = jsonData.getBoolean("adult");
        $timer = jsonData.getLong("time");
        changeTexture();
    }
}
