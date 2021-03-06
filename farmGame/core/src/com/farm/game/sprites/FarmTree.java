package com.farm.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Scaling;
import com.farm.game.Assets;
import com.farm.game.FarmGameMain;
import com.farm.game.spriteData.FarmTreeFruitTypeEnum;
import com.farm.game.spriteData.FarmTreeStatusEnum;
import com.farm.game.spriteData.FarmTreeTypeEnum;
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
    private boolean $usedFertilizer;

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

    public FarmTreeFruitTypeEnum getFruitType() {
        return $fruitType;
    }

    @Override
    public void handleTouch(GameStateManager gsm) {
        switch ($status) {
            case Growing:
                String title;
                if ($type == FarmTreeTypeEnum.Tree) {
                    title = "Boom groeit";
                } else {
                    title = "Struik groeit";
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
                        if($usedFertilizer)
                            additionTime = (treeGrowTime*60*1000) / 2;
                        else
                            additionTime = (treeGrowTime*60*1000);
                        break;
                    case Bush:
                        if($usedFertilizer)
                            additionTime = (bushGrowTime*60*1000) / 2;
                        else
                            additionTime = (bushGrowTime*60*1000);
                        break;
                }
                if($timer + additionTime - System.currentTimeMillis() <= 0) {
                    $status = FarmTreeStatusEnum.GrowingFruit;
                    $usedFertilizer = false;
                    // don't change timer otherwise -> day goes by -> app starts -> timer = when app starts
                }
                break;
            case GrowingFruit:
                switch ($fruitType){
                    case Apple:
                        if($usedFertilizer)
                            additionTime = ((treeGrowTime*60*1000) + (appleGrowTime*60*1000)) / 2;
                        else
                            additionTime = (treeGrowTime*60*1000) + (appleGrowTime*60*1000);
                        break;
                    case Raspberry:
                        if($usedFertilizer)
                            additionTime = ((bushGrowTime*60*1000) + (raspberryGrowTime*60*1000)) / 2;
                        else
                            additionTime = (bushGrowTime*60*1000) + (raspberryGrowTime*60*1000);
                        break;
                }
                if($timer + additionTime - System.currentTimeMillis() <= 0) {
                    $status = FarmTreeStatusEnum.Ready;
                    $usedFertilizer = false;
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
                        if($usedFertilizer)
                            additionTime = (treeGrowTime*60*1000) / 2;
                        else
                            additionTime = (treeGrowTime*60*1000);
                        break;
                    case Bush:
                        texture = Assets.farmBushGrown;
                        if($usedFertilizer)
                            additionTime = (bushGrowTime*60*1000) / 2;
                        else
                            additionTime = (bushGrowTime*60*1000);
                        break;
                }
                break;
            case GrowingFruit:
                switch ($fruitType){
                    case Apple:
                        texture = Assets.appleTexture;
                        if($usedFertilizer)
                            additionTime = ((treeGrowTime*60*1000) + (appleGrowTime*60*1000)) / 2;
                        else
                            additionTime = (treeGrowTime*60*1000) + (appleGrowTime*60*1000);
                        break;
                    case Raspberry:
                        texture = Assets.raspberryTexture;
                        if($usedFertilizer)
                            additionTime = ((bushGrowTime*60*1000) + (raspberryGrowTime*60*1000)) / 2;
                        else
                            additionTime = (bushGrowTime*60*1000) + (raspberryGrowTime*60*1000);
                        break;
                }
                break;
        }

        gsm.push(new TimeLeftMenuState(gsm, texture, $timer, additionTime , title, this));
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
    public void confirmDelete(GameStateManager gsm, int rowIndex, int columnIndex) {
        if($type == FarmTreeTypeEnum.Tree) {
            gsm.push(new MenuState(gsm, confirmTable(gsm, rowIndex, columnIndex), "Confirmatie", null));
        } else {
            gsm.push(new MenuState(gsm, confirmTable(gsm, rowIndex, columnIndex), "Confirmatie", null));
        }
    }

    private Table confirmTable(final GameStateManager gsm, final int rowIndex, final int columnIndex) {
        Skin skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));

        Table scrollTable = new Table();
        scrollTable.defaults().pad(10).width(128).height(128);

        Label infoLabel = new Label("Ben je zeker dat je deze", skin);
        Label info2Label = new Label("boom wilt verwijderen?", skin);
        if($type == FarmTreeTypeEnum.Bush) {
            info2Label.setText("struik wilt verwijderen?");
        }
        infoLabel.setFontScale(1.5f);
        info2Label.setFontScale(1.5f);

        // Accept
        Image acceptImage = new Image(Assets.acceptTexture);
        acceptImage.setScaling(Scaling.fit);
        acceptImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gsm.pop();
                FarmGameMain.landscape.deleteIndexes(rowIndex, columnIndex);
                FarmGameMain.settings.saveToJSON();
                event.handle(); //the Stage will stop trying to handle this event
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });
        // Cancel
        Image cancelImage = new Image(Assets.cancelTexture);
        cancelImage.setScaling(Scaling.fit);
        cancelImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gsm.pop();
                event.handle(); //the Stage will stop trying to handle this event
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });

        scrollTable.add(infoLabel).left();
        scrollTable.row();
        scrollTable.add(info2Label).left();
        scrollTable.row();
        scrollTable.row();
        scrollTable.row();
        scrollTable.add(acceptImage);
        scrollTable.add();scrollTable.add();scrollTable.add();
        scrollTable.add(cancelImage);
        scrollTable.row();

        return scrollTable;
    }

    public boolean isFertilized() {
        return $usedFertilizer;
    }

    public void useFertilizer() {
        $usedFertilizer = true;
    }

    @Override
    public void update() {
        changeTexture();
    }

    @Override
    public void write(Json json) {
        json.writeValue("status", $status);
        json.writeValue("type", $type);
        json.writeValue("fruitType", $fruitType);
        json.writeValue("time", $timer);
        json.writeValue("fertilizer", $usedFertilizer);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        $status = FarmTreeStatusEnum.valueOf(jsonData.getString("status"));
        $type = FarmTreeTypeEnum.valueOf(jsonData.getString("type"));
        $fruitType = FarmTreeFruitTypeEnum.valueOf(jsonData.getString("fruitType"));
        $timer = jsonData.getLong("time");
        $usedFertilizer = jsonData.getBoolean("fertilizer");
        changeTexture();
    }
}
