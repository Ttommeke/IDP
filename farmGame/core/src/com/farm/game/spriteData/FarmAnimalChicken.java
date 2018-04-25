package com.farm.game.spriteData;

import com.farm.game.Assets;
import com.farm.game.FarmGameMain;

public class FarmAnimalChicken extends FarmAnimal {
    private final int growTime = 40; //In minutes
    private final int eggTimeNeeded = 12; //In minutes

    public FarmAnimalChicken() {
        $texture = Assets.chickTexture;
        $timer = System.currentTimeMillis();
        $adult = false;
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
        if($adult && !$productReady) {
            additionTime = (eggTimeNeeded*60*1000);
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
