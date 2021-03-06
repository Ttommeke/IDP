package com.farm.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Scaling;
import com.farm.game.Assets;
import com.farm.game.FarmGameMain;
import com.farm.game.spriteData.FarmLandStatusEnum;
import com.farm.game.spriteData.FarmLandTypeEnum;
import com.farm.game.states.GameStateManager;
import com.farm.game.states.MenuState;
import com.farm.game.states.TimeLeftMenuState;

/**
 * The class representing a farmLand/acre (for planting seeds)
 */
public class FarmLand extends FarmObject{
    // status will change over time
    private FarmLandStatusEnum $status;
    private FarmLandTypeEnum $type;
    private long $plantTime;
    private boolean $usedFertilizer;

    private final int grainGrowTime = 18; //In minutes
    private final int carrotGrowTime = 22; //In minutes
    private final int potatoGrowTime = 26; //In minutes
    private final int eggplantGrowTime = 36; //In minutes
    private final int strawberryGrowTime = 34; //In minutes

    public FarmLand(){
        super( 1, Assets.farmLandUnplantedTexture);
        resetLand();
    }

    public FarmLandStatusEnum getStatus() {
        return $status;
    }

    @Override
    public void handleTouch(GameStateManager gsm) {
        switch ($status) {
            case Unplanted:
                gsm.push(new MenuState(gsm, getUnplantedMenu(gsm), "Kies zaadje", null));
                break;
            case Growing:
                pushTimeLeftMenu(gsm, "Zaadje groeit");
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
                FarmGameMain.settings.saveToJSON();
                break;
            case Rotten:
                resetLand();
                break;
        }
    }

    private void plant(FarmLandTypeEnum type) {
        $type = type;
        $status = FarmLandStatusEnum.Growing;
        $plantTime = System.currentTimeMillis();
        int cost = 0;
        switch ($type){
            case Grain:
                cost = 1;
                break;
            case Carrot:
                cost = 1;
                break;
            case Potato:
                cost = 2;
                break;
            case Eggplant:
                cost = 6;
                break;
            case Strawberry:
                cost = 4;
                break;
        }
        FarmGameMain.inventory.buySomething(cost);
        FarmGameMain.settings.saveToJSON();
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
                        if($usedFertilizer)
                            additionTime = (grainGrowTime*60*1000) / 2;
                        else
                            additionTime = (grainGrowTime*60*1000);
                        break;
                    case Carrot:
                        if($usedFertilizer)
                            additionTime = (carrotGrowTime*60*1000) / 2;
                        else
                            additionTime = (carrotGrowTime*60*1000);
                        break;
                    case Potato:
                        if($usedFertilizer)
                            additionTime = (potatoGrowTime*60*1000) / 2;
                        else
                            additionTime = (potatoGrowTime*60*1000);
                        break;
                    case Eggplant:
                        if($usedFertilizer)
                            additionTime = (eggplantGrowTime*60*1000) / 2;
                        else
                            additionTime = (eggplantGrowTime*60*1000);
                        break;
                    case Strawberry:
                        if($usedFertilizer)
                            additionTime = (strawberryGrowTime*60*1000) / 2;
                        else
                            additionTime = (strawberryGrowTime*60*1000);
                        break;
                }
                if($plantTime + additionTime - System.currentTimeMillis() <= 0) {
                    $status = FarmLandStatusEnum.FullyGrown;
                    $usedFertilizer = false;
                }
                break;
            case FullyGrown:
                switch ($type){
                    case Grain:
                        additionTime = (grainGrowTime*60*1000) + (grainGrowTime*2*60*1000); // GrowingTime + 2*GrowingTime
                        break;
                    case Carrot:
                        additionTime = (carrotGrowTime*60*1000) + (carrotGrowTime*2*60*1000);
                        break;
                    case Potato:
                        additionTime = (potatoGrowTime*60*1000) + (potatoGrowTime*2*60*1000);
                        break;
                    case Eggplant:
                        additionTime = (eggplantGrowTime*60*1000) + (eggplantGrowTime*2*60*1000);
                        break;
                    case Strawberry:
                        additionTime = (strawberryGrowTime*60*1000) + (strawberryGrowTime*2*60*1000);
                        break;
                }
                if($plantTime + additionTime - System.currentTimeMillis() <= 0) {
                    $status = FarmLandStatusEnum.Rotten;
                }
                break;
        }
    }

    private Table getUnplantedMenu(final GameStateManager gsm) {
        Skin skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));

        Table scrollTable = new Table();
        scrollTable.defaults().pad(10).width(256).height(256);

        // Grain
        Image grainImage = new Image(Assets.grainTexture);
        grainImage.setScaling(Scaling.fit);
        TextButton buyGrain = new TextButton("Koop   1", skin);
        buyGrain.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(FarmGameMain.inventory.getCoins() >= 1) {
                    plant(FarmLandTypeEnum.Grain);
                    gsm.pop();
                }
            }
        });

        // Carrot
        Image carrotImage = new Image(Assets.carrotTexture);
        carrotImage.setScaling(Scaling.fit);
        TextButton buyCarrot = new TextButton("Koop   1", skin);
        buyCarrot.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(FarmGameMain.inventory.getCoins() >= 1) {
                    plant(FarmLandTypeEnum.Carrot);
                    gsm.pop();
                }
            }
        });

        // Potato
        Image potatoImage = new Image(Assets.potatoTexture);
        potatoImage.setScaling(Scaling.fit);
        TextButton buyPotato = new TextButton("Koop   2", skin);
        buyPotato.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(FarmGameMain.inventory.getCoins() >= 2) {
                    plant(FarmLandTypeEnum.Potato);
                    gsm.pop();
                }
            }
        });

        // Strawberry
        Image strawberryImage = new Image(Assets.strawberryTexture);
        strawberryImage.setScaling(Scaling.fit);
        TextButton buyStrawberry = new TextButton("Koop   4", skin);
        buyStrawberry.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(FarmGameMain.inventory.getCoins() >= 4) {
                    plant(FarmLandTypeEnum.Strawberry);
                    gsm.pop();
                }
            }
        });

        // Eggplant
        Image eggplantImage = new Image(Assets.eggplantTexture);
        eggplantImage.setScaling(Scaling.fit);
        TextButton buyEggplant = new TextButton("Koop   6", skin);
        buyEggplant.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(FarmGameMain.inventory.getCoins() >= 6) {
                    plant(FarmLandTypeEnum.Eggplant);
                    gsm.pop();
                }
            }
        });

        scrollTable.add(grainImage);
        scrollTable.add(carrotImage);
        scrollTable.add(potatoImage);
        scrollTable.add(strawberryImage);
        scrollTable.add(eggplantImage);
        scrollTable.row();
        scrollTable.add(buyGrain);
        scrollTable.add(buyCarrot);
        scrollTable.add(buyPotato);
        scrollTable.add(buyStrawberry);
        scrollTable.add(buyEggplant);
        scrollTable.row();

        return scrollTable;
    }

    private void pushTimeLeftMenu(GameStateManager gsm, String title) {
        long additionTime = 0L;
        Texture texture = Assets.grainTexture;
        switch ($type){
            case Grain:
                texture = Assets.grainTexture;
                if($usedFertilizer)
                    additionTime = (grainGrowTime*60*1000) / 2;
                else
                    additionTime = (grainGrowTime*60*1000);
                break;
            case Carrot:
                texture = Assets.carrotTexture;
                if($usedFertilizer)
                    additionTime = (carrotGrowTime*60*1000) / 2;
                else
                    additionTime = (carrotGrowTime*60*1000);
                break;
            case Potato:
                texture = Assets.potatoTexture;
                if($usedFertilizer)
                    additionTime = (potatoGrowTime*60*1000) / 2;
                else
                    additionTime = (potatoGrowTime*60*1000);
                break;
            case Eggplant:
                texture = Assets.eggplantTexture;
                if($usedFertilizer)
                    additionTime = (eggplantGrowTime*60*1000) / 2;
                else
                    additionTime = (eggplantGrowTime*60*1000);
                break;
            case Strawberry:
                texture = Assets.strawberryTexture;
                if($usedFertilizer)
                    additionTime = (strawberryGrowTime*60*1000) / 2;
                else
                    additionTime = (strawberryGrowTime*60*1000);
                break;
        }

        gsm.push(new TimeLeftMenuState(gsm, texture, $plantTime, additionTime , title, this));
    }

    @Override
    public void confirmDelete(GameStateManager gsm, int rowIndex, int columnIndex) {
        gsm.push(new MenuState(gsm, confirmTable(gsm, rowIndex, columnIndex), "Confirmatie", null));
    }

    private Table confirmTable(final GameStateManager gsm, final int rowIndex, final int columnIndex) {
        Skin skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));

        Table scrollTable = new Table();
        scrollTable.defaults().pad(10).width(128).height(128);

        Label infoLabel = new Label("Ben je zeker dat je dit", skin);
        Label info2Label = new Label("akker wilt verwijderen?", skin);
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
        json.writeValue("time", $plantTime);
        json.writeValue("fertilizer", $usedFertilizer);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        $status = FarmLandStatusEnum.valueOf(jsonData.getString("status"));
        $type = FarmLandTypeEnum.valueOf(jsonData.getString("type"));
        $plantTime = jsonData.getLong("time");
        $usedFertilizer = jsonData.getBoolean("fertilizer");
        changeTexture();
    }
}
