package com.farm.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.farm.game.Assets;

/**
 * The class representing a farmLand/acre (for planting seeds)
 */
public class FarmLand extends FarmObject{
    // status will change over time (with the use of a Timer or initialPlantTime or something like that)
    private FarmLandStatusEnum $status;

    public FarmLand(FarmLandStatusEnum status){
        super( 1, Assets.unplantedFieldTexture);

        // Change accordingly
        if($status == FarmLandStatusEnum.Unplanted) {
            $texture = Assets.unplantedFieldTexture;
        } else if ($status == FarmLandStatusEnum.Growing) {
            $texture = Assets.unplantedFieldTexture;
        } else if ($status == FarmLandStatusEnum.FullyGrown) {
            $texture = Assets.unplantedFieldTexture;
        } else if ($status == FarmLandStatusEnum.Rotten) {
            $texture = Assets.unplantedFieldTexture;
        }
        $status = status;
    }

    @Override
    public Texture getTexture() {
        return $texture;
    }
}
