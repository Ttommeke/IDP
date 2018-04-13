package com.farm.game.sprites;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.farm.game.Assets;
import com.farm.game.states.GameStateManager;

/**
 * The class representing a farmLand/acre (for planting seeds)
 */
public class FarmLand extends FarmObject{
    // status will change over time (with the use of a Timer or initialPlantTime or something like that)
    private FarmLandStatusEnum $status;
    private FarmLandTypeEnum $type;

    public FarmLand(){
        super( 1, Assets.farmLandUnplantedTexture);

        $status = FarmLandStatusEnum.Unplanted;
        $type = FarmLandTypeEnum.Unplanted;
    }

    public FarmLand(FarmLandStatusEnum status, FarmLandTypeEnum type){
        super( 1, Assets.farmLandUnplantedTexture);

        $status = status;
        $type = type;

        changeTexture();
    }

    @Override
    public void handleTouch(GameStateManager gsm) {
        System.out.println("farmLand");
    }

    /**
     * Change the texture according to the status & type
     * (Change textures if created)
     */
    private void changeTexture() {
        switch ($status) {
            case Unplanted:
                $texture = Assets.farmLandUnplantedTexture;
                break;
            case Growing:
                switch ($type){
                    case Grain:
                        $texture = Assets.farmLandUnplantedTexture;
                        break;
                    case Carrot:
                        $texture = Assets.farmLandUnplantedTexture;
                        break;
                    case Potato:
                        $texture = Assets.farmLandUnplantedTexture;
                        break;
                    case Eggplant:
                        $texture = Assets.farmLandUnplantedTexture;
                        break;
                    case Strawberry:
                        $texture = Assets.farmLandUnplantedTexture;
                        break;
                }
                break;
            case FullyGrown:
                switch ($type){
                    case Grain:
                        $texture = Assets.farmLandUnplantedTexture;
                        break;
                    case Carrot:
                        $texture = Assets.farmLandUnplantedTexture;
                        break;
                    case Potato:
                        $texture = Assets.farmLandUnplantedTexture;
                        break;
                    case Eggplant:
                        $texture = Assets.farmLandUnplantedTexture;
                        break;
                    case Strawberry:
                        $texture = Assets.farmLandUnplantedTexture;
                        break;
                }
                break;
            case Rotten:
                $texture = Assets.farmLandUnplantedTexture;
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
