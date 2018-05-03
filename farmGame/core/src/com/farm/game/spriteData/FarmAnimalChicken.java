package com.farm.game.spriteData;

import com.badlogic.gdx.utils.JsonValue;
import com.farm.game.Assets;
import com.farm.game.FarmGameMain;

public class FarmAnimalChicken extends FarmAnimal {
    public static int chickPrize = 22;

    public FarmAnimalChicken() { // 40, 12
        super(1, 1);
        $texture = Assets.chickTexture;
        $timer = System.currentTimeMillis();
        $adult = false;
        $productReady = false;
    }

    public FarmAnimalChicken(JsonValue value){
        super(1, 1);
        $timer = value.getLong("timer");
        $adult = value.getBoolean("adult");
        $productReady = value.getBoolean("productReady");
        $eating = value.getBoolean("isEating");
        changeTexture();
    }

    @Override
    public void changeTexture() {
        if($adult) {
            $texture = Assets.chickenTexture;
        } else {
            $texture = Assets.chickTexture;
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
        FarmGameMain.inventory.addEgg(1);
        FarmGameMain.settings.saveToJSON();
    }
}
