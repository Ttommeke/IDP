package com.farm.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Scaling;
import com.farm.game.Assets;
import com.farm.game.spriteData.FarmAnimal;
import com.farm.game.spriteData.FarmFieldStatusEnum;
import com.farm.game.spriteData.FarmFieldTypeEnum;
import com.farm.game.states.GameStateManager;
import com.farm.game.states.MenuState;

import java.util.ArrayList;

/**
 * The class representing a farmField(weide, For growing animals)
 */
public class FarmField extends FarmObject {
    // status & type will change over time (with the use of a Timer or initialPlantTime or something like that)
    private FarmFieldStatusEnum $status;
    private FarmFieldTypeEnum $type;
    private ArrayList<FarmAnimal> $farmAnimals;

    public FarmField(){
        super( 2, Assets.farmFieldUninhabitedTexture);

        $status = FarmFieldStatusEnum.Uninhabited;
        $type = FarmFieldTypeEnum.Uninhabited;
        $farmAnimals = new ArrayList<>();
    }

    // Used on Default farmLandscape
    public FarmField(FarmFieldStatusEnum status, FarmFieldTypeEnum type, ArrayList<FarmAnimal> farmAnimals){
        super( 2, Assets.farmFieldUninhabitedTexture);

        $status = status;
        $type = type;
        $farmAnimals = farmAnimals;

        changeTexture();
    }

    /**
     * Change the texture according to the status & type
     * (Change textures if created)
     */
    private void changeTexture() {
        switch ($status) {
            case Uninhabited:
                $texture = Assets.farmFieldUninhabitedTexture;
                break;
            case Children:
                switch ($type) {
                    case Chicken:
                        $texture = Assets.farmFieldUninhabitedTexture;
                        break;
                    case Pig:
                        $texture = Assets.farmFieldUninhabitedTexture;
                        break;
                    case Cow:
                        $texture = Assets.farmFieldUninhabitedTexture;
                        break;
                }
                break;
            case Adults:
                switch ($type) {
                    case Chicken:
                        $texture = Assets.farmFieldChickenTexture;
                        break;
                    case Pig:
                        $texture = Assets.farmFieldUninhabitedTexture;
                        break;
                    case Cow:
                        $texture = Assets.farmFieldUninhabitedTexture;
                        break;
                }
                break;
        }
    }

    @Override
    public void handleTouch(GameStateManager gsm) {
        gsm.push(new MenuState(gsm, getTable(), "Field"));
    }

    /**
     * Add table functions and apply them to the right choice
     */
    private Table getTable() {
        Table scrollTable = new Table();
        switch ($status) {
            case Uninhabited:
                scrollTable = getAdultChickenTable();
                break;
            case Children:
                switch ($type) {
                    case Chicken:
                        scrollTable = getAdultChickenTable();
                        break;
                    case Pig:
                        scrollTable = getAdultChickenTable();
                        break;
                    case Cow:
                        scrollTable = getAdultChickenTable();
                        break;
                }
                break;
            case Adults:
                switch ($type) {
                    case Chicken:
                        scrollTable = getAdultChickenTable();
                        break;
                    case Pig:
                        scrollTable = getAdultChickenTable();
                        break;
                    case Cow:
                        scrollTable = getAdultChickenTable();
                        break;
                }
                break;
        }

        return scrollTable;
    }

    private Table getAdultChickenTable() {
        Skin skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));

        Image chickenImage = new Image(Assets.chickenTexture);
        chickenImage.setScaling(Scaling.fit);
        Label animalAmount = new Label(String.valueOf($farmAnimals.size()), skin);
        animalAmount.setFontScale(5);

        Table scrollTable = new Table();
        scrollTable.defaults().pad(10).width(128).height(128);

        scrollTable.add(chickenImage);
        scrollTable.add(animalAmount).center();

        return scrollTable;
    }

    public void addAnimal() {

    }

    public void removeOneAnimal() {

    }

    @Override
    public void write(Json json) {
        json.writeValue("status", $status);
        json.writeValue("type", $type);
        json.writeValue("animals", $farmAnimals);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        $status = FarmFieldStatusEnum.valueOf(jsonData.getString("status"));
        $type = FarmFieldTypeEnum.valueOf(jsonData.getString("type"));
        changeTexture();
    }
}
