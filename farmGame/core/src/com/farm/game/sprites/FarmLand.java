package com.farm.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.farm.game.Assets;
import com.farm.game.FarmGameMain;

import java.util.Random;

public class FarmLand {
    private Vector3 $position;
    private EnumFarmLandStatus $status;
    private Texture $field;

    public FarmLand(){
        Random rand = new Random();

        if($status == EnumFarmLandStatus.Unplanted) {
            $field = Assets.unplantedFieldTexture;
        } else if ($status == EnumFarmLandStatus.Growing) {
            $field = Assets.unplantedFieldTexture;
        } else if ($status == EnumFarmLandStatus.FullyGrown) {
            $field = Assets.unplantedFieldTexture;
        } else if ($status == EnumFarmLandStatus.Rotten) {
            $field = Assets.unplantedFieldTexture;
        }

        $position = new Vector3(rand.nextInt(FarmGameMain.WIDTH/2) + 128, FarmGameMain.HEIGHT/2, 0);
    }

    public Vector3 getPosition() {
        return $position;
    }

    public Texture getTexture() {
        return $field;
    }
}
