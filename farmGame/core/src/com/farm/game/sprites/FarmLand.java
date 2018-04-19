package com.farm.game.sprites;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.farm.game.Assets;
import com.farm.game.FarmGameMain;
import com.farm.game.states.GameStateManager;

/**
 * The class representing a farmLand/acre (for planting seeds)
 */
public class FarmLand extends FarmObject{
    // status will change over time
    private FarmLandStatusEnum $status;
    private FarmLandTypeEnum $type;
    private long $plantTime;

    public FarmLand(){
        super( 1, Assets.farmLandUnplantedTexture);
        resetLand();
    }

    @Override
    public void handleTouch(GameStateManager gsm) {
        switch ($status) {
            case Unplanted:
                // show planting menu
                break;
            case Growing:
                // show time left menu
                break;
            case FullyGrown:
                switch ($type){
                    case Grain:
                        FarmGameMain.inventory.addGrain(2);
                        resetLand();
                        break;
                    case Carrot:
                        FarmGameMain.inventory.addCarrot(5);
                        resetLand();
                        break;
                    case Potato:
                        FarmGameMain.inventory.addPotato(4);
                        resetLand();
                        break;
                    case Eggplant:
                        FarmGameMain.inventory.addStrawberry(1);
                        resetLand();
                        break;
                    case Strawberry:
                        FarmGameMain.inventory.addEggplant(1);
                        resetLand();
                        break;
                }
                break;
            case Rotten:
                resetLand();
                break;
        }
    }

    public void plant(FarmLandTypeEnum type) {
        $type = type;
        $status = FarmLandStatusEnum.Growing;
        $plantTime = System.currentTimeMillis();
    }

    private void resetLand() {
        $type = FarmLandTypeEnum.Unplanted;
        $status = FarmLandStatusEnum.Unplanted;
        $plantTime = 0L;
    }

    private void checkTimer() {
        long additionTime = 0;
        switch ($status) {
            case Growing:
                switch ($type){
                    case Grain:
                        additionTime = (18*60*1000);                // Minutes = 18
                        break;
                    case Carrot:
                        additionTime = (22*60*1000);
                        break;
                    case Potato:
                        additionTime = (26*60*1000);
                        break;
                    case Eggplant:
                        additionTime = (36*60*1000);
                        break;
                    case Strawberry:
                        additionTime = (34*60*1000);
                        break;
                }
                if($plantTime + additionTime >= System.currentTimeMillis()) {
                    $status = FarmLandStatusEnum.FullyGrown;
                }
                break;
            case FullyGrown:
                switch ($type){
                    case Grain:
                        additionTime = (18*60*1000) + (36*60*1000); // GrowingTime + 2*GrowingTime
                        break;
                    case Carrot:
                        additionTime = (22*60*1000) + (44*60*1000);
                        break;
                    case Potato:
                        additionTime = (26*60*1000) + (52*60*1000);
                        break;
                    case Eggplant:
                        additionTime = (36*60*1000) + (72*60*1000);
                        break;
                    case Strawberry:
                        additionTime = (34*60*1000) + (68*60*1000);
                        break;
                }
                if($plantTime + additionTime >= System.currentTimeMillis()) {
                    $status = FarmLandStatusEnum.Rotten;
                }
                break;
        }
    }

    /**
     * Change the texture according to the status & type
     * (Change textures if created)
     */
    private void changeTexture() {
        checkTimer();
        switch ($status) {
            case Unplanted:
                $texture = Assets.farmLandUnplantedTexture;
                break;
            case Growing:
                switch ($type){
                    case Grain:
                        $texture = Assets.farmLandGrainGrowingTexture;
                        break;
                    case Carrot:
                        $texture = Assets.farmLandCarrotGrowingTexture;
                        break;
                    case Potato:
                        $texture = Assets.farmLandPotatoGrowingTexture;
                        break;
                    case Eggplant:
                        $texture = Assets.farmLandEggplantGrowingTexture;
                        break;
                    case Strawberry:
                        $texture = Assets.farmLandStrawberryGrowingTexture;
                        break;
                }
                break;
            case FullyGrown:
                switch ($type){
                    case Grain:
                        $texture = Assets.farmLandGrainReadyTexture;
                        break;
                    case Carrot:
                        $texture = Assets.farmLandCarrotReadyTexture;
                        break;
                    case Potato:
                        $texture = Assets.farmLandPotatoReadyTexture;
                        break;
                    case Eggplant:
                        $texture = Assets.farmLandEggplantReadyTexture;
                        break;
                    case Strawberry:
                        $texture = Assets.farmLandStrawberryReadyTexture;
                        break;
                }
                break;
            case Rotten:
                switch ($type){
                    case Grain:
                        $texture = Assets.farmLandGrainRottenTexture;
                        break;
                    case Carrot:
                        $texture = Assets.farmLandCarrotRottenTexture;
                        break;
                    case Potato:
                        $texture = Assets.farmLandPotatoRottenTexture;
                        break;
                    case Eggplant:
                        $texture = Assets.farmLandEggplantRottenTexture;
                        break;
                    case Strawberry:
                        $texture = Assets.farmLandStrawberryRottenTexture;
                        break;
                }
                break;
        }
    }

    @Override
    public void updateTexture() {
        changeTexture();
    }

    @Override
    public void write(Json json) {
        json.writeValue("status", $status);
        json.writeValue("type", $type);
        json.writeValue("time", $plantTime);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        $status = FarmLandStatusEnum.valueOf(jsonData.getString("status"));
        $type = FarmLandTypeEnum.valueOf(jsonData.getString("type"));
        $plantTime = jsonData.getLong("time");
        changeTexture();
    }
}
