package com.farm.game.spriteData;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.farm.game.FarmGameMain;

public abstract class FarmAnimal implements Json.Serializable{
    protected Texture $texture;
    protected boolean $adult;
    protected boolean $productReady;
    protected boolean $eating;
    protected long $timer;                  // when not an adult -> time child purchased | when an adult -> time for production
    protected final int growTimeNeeded;     //In minutes
    protected final int productTimeNeeded;  //In minutes

    public FarmAnimal() {
        growTimeNeeded = 0;
        productTimeNeeded = 0;
    }

    protected FarmAnimal(int growTime, int productTime) {
        growTimeNeeded = growTime;
        productTimeNeeded = productTime;
    }

    public Texture getTexture() {
        return $texture;
    }

    protected abstract void changeTexture();

    protected abstract void checkTimer();

    public abstract void collectProduct();

    public boolean isAdult() {
        return $adult;
    }

    public boolean isProductReady() {
        return $productReady;
    }

    public boolean isEating() {
        return $eating;
    }

    public void update() {
        checkTimer();
    }

    public void feedAnimal() {
        $eating = true;
        $timer = System.currentTimeMillis();
        FarmGameMain.settings.saveToJSON();
    }

    public long getTimer() {
        return $timer;
    }

    public int getGrowTimeInMillis() {
        return growTimeNeeded*60*1000;
    }

    public int getProductTimeNeededInMillis() {
        return productTimeNeeded*60*1000;
    }

    @Override
    public void write(Json json) {
        json.writeValue("timer", $timer);
        json.writeValue("adult", $adult);
        json.writeValue("productReady", $productReady);
        json.writeValue("isEating", $eating);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        $timer = jsonData.getLong("time");
        $adult = jsonData.getBoolean("adult");
        $productReady = jsonData.getBoolean("productReady");
        $eating = jsonData.getBoolean("isEating");
        changeTexture();
    }
}
