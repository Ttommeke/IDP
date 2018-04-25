package com.farm.game.spriteData;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public abstract class FarmAnimal implements Json.Serializable{
    protected Texture $texture;
    protected boolean $adult;
    protected boolean $productReady;
    protected long $timer;      // when not an adult -> time child purchased | when an adult -> time for production

    private final int maxAmountOfAnimals = 5;

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
        System.out.println("test write");
        json.writeValue("timer", $timer);
        json.writeValue("adult", $adult);
        json.writeValue("productReady", $productReady);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        System.out.println("test read");
        $timer = jsonData.getLong("time");
        $adult = jsonData.getBoolean("adult");
        $productReady = jsonData.getBoolean("productReady");
        changeTexture();
    }
}
