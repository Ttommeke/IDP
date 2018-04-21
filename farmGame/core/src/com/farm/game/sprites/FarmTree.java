package com.farm.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Scaling;
import com.farm.game.Assets;
import com.farm.game.FarmGameMain;
import com.farm.game.states.GameStateManager;
import com.farm.game.states.MenuState;
import com.farm.game.states.TimeLeftMenuState;

/**
 * The class representing a farmTree/Bush
 */
public class FarmTree extends FarmObject {
    private FarmTreeStatusEnum $status;
    private FarmTreeFruitTypeEnum $fruitType;
    private FarmTreeTypeEnum $type;
    private long $timer;

    private final int treeGrowTime = 15; //In minutes
    private final int bushGrowTime = 15; //In minutes
    private final int appleGrowTime = 25; //In minutes
    private final int raspberryGrowTime = 30; //In minutes

    // Needed for JSON creator, texture will change base on status & types
    public FarmTree() {
        super(1, Assets.farmBushGrowing);
    }

    public FarmTree(FarmTreeFruitTypeEnum fruitType) {
        super(1, Assets.farmBushGrowing);
        $status = FarmTreeStatusEnum.Growing;
        $fruitType = fruitType;
        if($fruitType == FarmTreeFruitTypeEnum.Apple) {     // add all tree types here
            $texture = Assets.farmTreeGrowing;
            $type = FarmTreeTypeEnum.Tree;
        } else {
            $type = FarmTreeTypeEnum.Bush;
        }
        $timer = System.currentTimeMillis();
    }

    @Override
    public void handleTouch(GameStateManager gsm) {
        switch ($status) {
            case Growing:
                String title;
                if ($type == FarmTreeTypeEnum.Tree) {
                    title = "Boom groeid";
                } else {
                    title = "Struik groeid";
                }
                pushTimeLeftMenu(gsm, title);
                break;
            case GrowingFruit:
                pushTimeLeftMenu(gsm, "Fruit groeit");
                break;
            case Ready:
                switch ($fruitType){
                    case Apple:
                        FarmGameMain.inventory.addApples(4);
                        resetTree();
                        break;
                    case Raspberry:
                        FarmGameMain.inventory.addRaspberries(1);
                        resetTree();
                        break;
                }
                FarmGameMain.settings.saveToJSON();
                break;
        }
    }

    private void resetTree() {
        $status = FarmTreeStatusEnum.GrowingFruit;
        $timer = System.currentTimeMillis();
    }

    private void checkTimer() {
        long additionTime = 0;
        switch ($status) {
            case Growing:
                switch ($type){
                    case Tree:
                        additionTime = (treeGrowTime*60*1000);
                        break;
                    case Bush:
                        additionTime = (bushGrowTime*60*1000);
                        break;
                }
                if($timer + additionTime - System.currentTimeMillis() <= 0) {
                    $status = FarmTreeStatusEnum.GrowingFruit;
                    // don't change timer otherwise -> day goes by -> app starts -> timer = when app starts
                }
                break;
            case GrowingFruit:
                switch ($fruitType){
                    case Apple:
                        additionTime = (treeGrowTime*60*1000) + (appleGrowTime*60*1000);
                        break;
                    case Raspberry:
                        additionTime = (bushGrowTime*60*1000) + (raspberryGrowTime*60*1000);
                        break;
                }
                if($timer + additionTime - System.currentTimeMillis() <= 0) {
                    $status = FarmTreeStatusEnum.Ready;
                }
                break;
        }
    }

    private void pushTimeLeftMenu(GameStateManager gsm, String title) {
        long additionTime = 0L;
        Texture texture = Assets.appleTexture;
        switch ($status){
            case Growing:
                switch ($type){
                    case Tree:
                        texture = Assets.farmTreeGrown;
                        additionTime = (treeGrowTime*60*1000);
                        break;
                    case Bush:
                        texture = Assets.farmBushGrown;
                        additionTime = (bushGrowTime*60*1000);
                        break;
                }
                break;
            case GrowingFruit:
                switch ($fruitType){
                    case Apple:
                        texture = Assets.appleTexture;
                        additionTime = (treeGrowTime*60*1000) + (appleGrowTime*60*1000);
                        break;
                    case Raspberry:
                        texture = Assets.raspberryTexture;
                        additionTime = (bushGrowTime*60*1000) + (raspberryGrowTime*60*1000);
                        break;
                }
                break;
        }

        gsm.push(new TimeLeftMenuState(gsm, texture, $timer, additionTime , title));
    }

    /**
     * Change the texture according to the status & type
     * (Change textures if created)
     */
    private void changeTexture() {
        checkTimer();
        switch ($status) {
            case Growing:
                switch ($type){
                    case Tree:
                        $texture = Assets.farmTreeGrowing;
                        break;
                    case Bush:
                        $texture = Assets.farmBushGrowing;
                        break;
                }
                break;
            case GrowingFruit:
                switch ($type){
                    case Tree:
                        $texture = Assets.farmTreeGrown;
                        break;
                    case Bush:
                        $texture = Assets.farmBushGrown;
                        break;
                }
                break;
            case Ready:
                switch ($fruitType){
                    case Apple:
                        $texture = Assets.farmTreeApplesReady;
                        break;
                    case Raspberry:
                        $texture = Assets.farmBushRaspberriesReady;
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
        json.writeValue("fruitType", $fruitType);
        json.writeValue("time", $timer);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        $status = FarmTreeStatusEnum.valueOf(jsonData.getString("status"));
        $type = FarmTreeTypeEnum.valueOf(jsonData.getString("type"));
        $fruitType = FarmTreeFruitTypeEnum.valueOf(jsonData.getString("fruitType"));
        $timer = jsonData.getLong("time");
        changeTexture();
    }
}
