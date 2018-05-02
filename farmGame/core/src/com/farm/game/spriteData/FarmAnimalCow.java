package com.farm.game.spriteData;

import com.badlogic.gdx.utils.JsonValue;
import com.farm.game.Assets;
import com.farm.game.FarmGameMain;

public class FarmAnimalCow extends FarmAnimal {
    public static int calfPrize = 97;

    public FarmAnimalCow() {
        super(120, 10);
        $texture =  Assets.calfTexture;
        $timer = System.currentTimeMillis();
        $adult = false;
        $productReady = false;
    }

    public FarmAnimalCow(JsonValue value){
        super(120, 10);
        $timer = value.getLong("timer");
        $adult = value.getBoolean("adult");
        $productReady = value.getBoolean("productReady");
        $eating = value.getBoolean("isEating");
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
        if($adult && $eating) {
            additionTime = (productTimeNeeded*60*1000);
            if($timer + additionTime - System.currentTimeMillis() <= 0) {
                $productReady = true;
                $eating = false;
                FarmGameMain.settings.saveToJSON();
            }
        } else if (!$adult){
            additionTime = (growTimeNeeded*60*1000);
            if($timer + additionTime - System.currentTimeMillis() <= 0) {
                $adult = true;
                FarmGameMain.settings.saveToJSON();
            }
        }
    }

    @Override
    public void collectProduct() {
        $productReady = false;
        FarmGameMain.inventory.addMilk(1);
        FarmGameMain.settings.saveToJSON();
    }
}
