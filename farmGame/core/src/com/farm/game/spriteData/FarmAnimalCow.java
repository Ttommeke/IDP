package com.farm.game.spriteData;

import com.badlogic.gdx.utils.JsonValue;
import com.farm.game.Assets;
import com.farm.game.FarmGameMain;

public class FarmAnimalCow extends FarmAnimal {
    private final int growTime = 120; //In minutes
    private final int milkTimeNeeded = 10; //In minutes

    public FarmAnimalCow() {
        $texture =  Assets.calfTexture;
        $timer = System.currentTimeMillis();
        $adult = false;
    }

    public FarmAnimalCow(JsonValue value){
        $timer = value.getLong("timer");
        $adult = value.getBoolean("adult");
        $productReady = value.getBoolean("productReady");
        changeTexture();
    }

    @Override
    public void changeTexture() {
        if($adult) {
            $texture = Assets.cowTexture;
        } else {
            $texture = Assets.calfTexture;
        }
    }

    @Override
    protected void checkTimer() {
        long additionTime = 0;
        if($adult && !$productReady) {
            additionTime = (milkTimeNeeded*60*1000);
            if($timer + additionTime - System.currentTimeMillis() <= 0) {
                $productReady = true;
                FarmGameMain.settings.saveToJSON();
            }
        } else if (!$adult){
            additionTime = (growTime*60*1000);
            if($timer + additionTime - System.currentTimeMillis() <= 0) {
                $adult = true;
                FarmGameMain.settings.saveToJSON();
            }
        }
    }
}
