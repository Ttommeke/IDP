package com.farm.game.sprites;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.farm.game.Assets;

/**
 * The class representing a farmLand/acre (for planting seeds)
 */
public class FarmLand extends FarmObject{
    // status will change over time (with the use of a Timer or initialPlantTime or something like that)
    private FarmLandStatusEnum $status;
    private FarmLandTypeEnum $type;

    public FarmLand(){
        super( 1, Assets.farmFieldUnplantedTexture);

        $status = FarmLandStatusEnum.Unplanted;
        $type = FarmLandTypeEnum.Unplanted;
    }

    public FarmLand(FarmLandStatusEnum status, FarmLandTypeEnum type){
        super( 1, Assets.farmFieldUnplantedTexture);

        $status = status;
        $type = type;

        changeTexture();
    }

    /**
     * Change the texture according to the status & type
     * (Change textures if created)
     */
    private void changeTexture() {
        switch ($status) {
            case Unplanted:
                $texture = Assets.farmFieldUnplantedTexture;
                break;
            case Growing:
                switch ($type){
                    case Grain:
                        $texture = Assets.farmFieldUnplantedTexture;
                        break;
                    case Carrot:
                        $texture = Assets.farmFieldUnplantedTexture;
                        break;
                    case Potato:
                        $texture = Assets.farmFieldUnplantedTexture;
                        break;
                    case Eggplant:
                        $texture = Assets.farmFieldUnplantedTexture;
                        break;
                    case Strawberry:
                        $texture = Assets.farmFieldUnplantedTexture;
                        break;
                }
                break;
            case FullyGrown:
                switch ($type){
                    case Grain:
                        $texture = Assets.farmFieldUnplantedTexture;
                        break;
                    case Carrot:
                        $texture = Assets.farmFieldUnplantedTexture;
                        break;
                    case Potato:
                        $texture = Assets.farmFieldUnplantedTexture;
                        break;
                    case Eggplant:
                        $texture = Assets.farmFieldUnplantedTexture;
                        break;
                    case Strawberry:
                        $texture = Assets.farmFieldUnplantedTexture;
                        break;
                }
                break;
            case Rotten:
                $texture = Assets.farmFieldUnplantedTexture;
                break;
        }
    }

    @Override
    public void write(Json json) {
        json.writeValue("status", $status);
        json.writeValue("type", $type);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        $status = FarmLandStatusEnum.valueOf(jsonData.getString("status"));
        $type = FarmLandTypeEnum.valueOf(jsonData.getString("type"));
        changeTexture();
    }
}
