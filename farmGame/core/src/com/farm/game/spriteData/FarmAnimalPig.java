package com.farm.game.spriteData;

import com.badlogic.gdx.utils.JsonValue;
import com.farm.game.Assets;
import com.farm.game.FarmGameMain;

public class FarmAnimalPig extends FarmAnimal {
    private final int growTime = 90; //In minutes

    public FarmAnimalPig() {
        $texture = Assets.pigletTexture;
        $timer = System.currentTimeMillis();
        $adult = false;
    }

    public FarmAnimalPig(JsonValue value){
        $timer = value.getLong("timer");
        $adult = value.getBoolean("adult");
        $productReady = value.getBoolean("productReady");
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
            additionTime = (growTime*60*1000);
            if($timer + additionTime - System.currentTimeMillis() <= 0) {
                $adult = true;
                FarmGameMain.settings.saveToJSON();
            }
        }
    }
}
