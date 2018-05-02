package com.farm.game.spriteData;

import com.badlogic.gdx.utils.JsonValue;
import com.farm.game.Assets;
import com.farm.game.FarmGameMain;

public class FarmAnimalPig extends FarmAnimal {
    public static int pigletPrize = 74;

    public FarmAnimalPig() {
        super(90, 0);
        $texture = Assets.pigletTexture;
        $timer = System.currentTimeMillis();
        $adult = false;
        $productReady = false;
    }

    public FarmAnimalPig(JsonValue value){
        super(90, 0);
        $timer = value.getLong("timer");
        $adult = value.getBoolean("adult");
        $productReady = value.getBoolean("productReady");
        $eating = value.getBoolean("isEating");
        changeTexture();
    }

    @Override
    protected void changeTexture() {
        if($adult) {
            $texture = Assets.pigTexture;
        } else {
            $texture = Assets.pigletTexture;
        }
    }

    @Override
    protected void checkTimer() {
        long additionTime = 0;
        if (!$adult){
            additionTime = (growTimeNeeded*60*1000);
            if($timer + additionTime - System.currentTimeMillis() <= 0) {
                $adult = true;
                $eating = false;
                FarmGameMain.settings.saveToJSON();
            }
        }
    }

    @Override
    public void collectProduct() {
        $productReady = false;
        FarmGameMain.settings.saveToJSON();
    }
}
