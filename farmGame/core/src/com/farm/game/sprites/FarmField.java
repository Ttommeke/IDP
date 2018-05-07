package com.farm.game.sprites;

import com.badlogic.gdx.Gdx;
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
import com.farm.game.spriteData.FarmAnimal;
import com.farm.game.spriteData.FarmAnimalChicken;
import com.farm.game.spriteData.FarmAnimalCow;
import com.farm.game.spriteData.FarmAnimalPig;
import com.farm.game.spriteData.FarmFieldStatusEnum;
import com.farm.game.spriteData.FarmFieldTypeEnum;
import com.farm.game.states.FieldMenuState;
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
    private final int maxAmountOfAnimals = 5;
    private int $foodStorage;

    public FarmField(){
        super( 2, Assets.farmFieldUninhabitedTexture);

        $status = FarmFieldStatusEnum.Uninhabited;
        $type = FarmFieldTypeEnum.Uninhabited;
        $farmAnimals = new ArrayList<>();
        $foodStorage = 0;
    }

    // Used on Default farmLandscape
    public FarmField(FarmFieldStatusEnum status, FarmFieldTypeEnum type, ArrayList<FarmAnimal> farmAnimals){
        super( 2, Assets.farmFieldUninhabitedTexture);

        $status = status;
        $type = type;
        $farmAnimals = farmAnimals;
        $foodStorage = 0;

        changeTexture();
    }

    public FarmFieldTypeEnum getType() {
        return $type;
    }

    public FarmFieldStatusEnum getStatus() {
        return $status;
    }

    public ArrayList<FarmAnimal> getFarmAnimals() {
        return $farmAnimals;
    }

    public boolean isFull() {
        return $farmAnimals.size() >= maxAmountOfAnimals;
    }

    public boolean getFoodFromStorage() {
        if($foodStorage >= 1) {
            --$foodStorage;
            return true;
        } else {
            return false;
        }
    }

    public void addFoodToStorage(int amount) {
        $foodStorage += amount;
    }

    public int getAmountOfFoodStorage() {
        return $foodStorage;
    }

    public void removeAnimal(FarmAnimal farmAnimal) {
        $farmAnimals.remove(farmAnimal);
        if($farmAnimals.size() == 0) {
            $type = FarmFieldTypeEnum.Uninhabited;
            $status = FarmFieldStatusEnum.Uninhabited;
        }
    }

    /**
     * Change the texture according to the status & type
     */
    private void changeTexture() {
        switch ($status) {
            case Uninhabited:
                $texture = Assets.farmFieldUninhabitedTexture;
                break;
            case Children:
                switch ($type) {
                    case Chicken:
                        $texture = Assets.farmFieldChickTexture;
                        break;
                    case Pig:
                        $texture = Assets.farmFieldPigletTexture;
                        break;
                    case Cow:
                        $texture = Assets.farmFieldCalfTexture;
                        break;
                }
                break;
            case Adults:
                switch ($type) {
                    case Chicken:
                        $texture = Assets.farmFieldChickenTexture;
                        break;
                    case Pig:
                        $texture = Assets.farmFieldPigTexture;
                        break;
                    case Cow:
                        $texture = Assets.farmFieldCowTexture;
                        break;
                }
                break;
            case ChildrenWithAdults:
                switch ($type) {
                    case Chicken:
                        $texture = Assets.farmFieldChickenAndChickTexture;
                        break;
                    case Pig:
                        $texture = Assets.farmFieldPigAndPigletTexture;
                        break;
                    case Cow:
                        $texture = Assets.farmFieldCowAndCalfTexture;
                        break;
                }
                break;
        }
    }

    @Override
    public void handleTouch(GameStateManager gsm) {
        switch ($status) {
            case Uninhabited:
                gsm.push(new MenuState(gsm, getChooseAnimalTable(gsm), "Veld", null));
                break;
            default:
                gsm.push(new FieldMenuState(gsm, this));
        }
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
        Label info2Label = new Label("veld wilt verwijderen?", skin);
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
     * Add table functions and apply them to the right choice
     */
    private Table getChooseAnimalTable(final GameStateManager gsm) {
        Skin skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));

        Table scrollTable = new Table();
        scrollTable.defaults().pad(10).width(256).height(256);

        // Chicken
        Image chickImage = new Image(Assets.chickTexture);
        chickImage.setScaling(Scaling.fit);
        TextButton buyChick = new TextButton("Koop   22", skin);
        buyChick.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(FarmGameMain.inventory.getCoins() >= FarmAnimalChicken.chickPrize) {
                    addAnimal(FarmFieldTypeEnum.Chicken);
                    gsm.pop();
                }
            }
        });

        // Pig
        Image pigletImage = new Image(Assets.pigletTexture);
        pigletImage.setScaling(Scaling.fit);
        TextButton buyPiglet = new TextButton("Koop   74", skin);
        buyPiglet.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(FarmGameMain.inventory.getCoins() >= FarmAnimalPig.pigletPrize) {
                    addAnimal(FarmFieldTypeEnum.Pig);
                    gsm.pop();
                }
            }
        });

        // Cow
        Image calfImage = new Image(Assets.calfTexture);
        calfImage.setScaling(Scaling.fit);
        TextButton buyCalf = new TextButton("Koop   97", skin);
        buyCalf.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(FarmGameMain.inventory.getCoins() >= FarmAnimalCow.calfPrize) {
                    addAnimal(FarmFieldTypeEnum.Cow);
                    gsm.pop();
                }
            }
        });

        scrollTable.add(chickImage);
        scrollTable.add(pigletImage);
        scrollTable.add(calfImage);
        scrollTable.row();
        scrollTable.add(buyChick);
        scrollTable.add(buyPiglet);
        scrollTable.add(buyCalf);
        scrollTable.row();

        return scrollTable;
    }

    @Override
    public void update() {
        boolean adult = false;
        boolean child = false;

        for(FarmAnimal farmAnimal: $farmAnimals) {
            farmAnimal.update();
            if(farmAnimal.isAdult()) {
                adult = true;
            } else {
                child = true;
            }
        }

        if(adult && child) {
            $status = FarmFieldStatusEnum.ChildrenWithAdults;
        } else if(adult) {
            $status = FarmFieldStatusEnum.Adults;
        } else if(child) {
            $status = FarmFieldStatusEnum.Children;
        }

        changeTexture();
    }

    public void addAnimal(FarmFieldTypeEnum type) {
        if(isFull()) {
            return;
        }

        switch (type){
            case Chicken:
                $farmAnimals.add(new FarmAnimalChicken());
                break;
            case Pig:
                $farmAnimals.add(new FarmAnimalPig());
                break;
            case Cow:
                $farmAnimals.add(new FarmAnimalCow());
                break;
        }

        $type = type;
        FarmGameMain.inventory.buySomething(getCostForType(type));

        if($status == FarmFieldStatusEnum.Adults) {
            $status = FarmFieldStatusEnum.ChildrenWithAdults;
        } else if($status == FarmFieldStatusEnum.Uninhabited){
            $status = FarmFieldStatusEnum.Children;
        }

        changeTexture();
        FarmGameMain.settings.saveToJSON();
    }

    public static int getCostForType(FarmFieldTypeEnum type) {
        int cost = 0;
        switch (type){
            case Chicken:
                cost = FarmAnimalChicken.chickPrize;
                break;
            case Pig:
                cost = FarmAnimalPig.pigletPrize;
                break;
            case Cow:
                cost = FarmAnimalCow.calfPrize;
                break;
        }
        return cost;
    }

    @Override
    public void write(Json json) {
        json.writeValue("status", $status);
        json.writeValue("type", $type);
        json.writeValue("animals", $farmAnimals);
        json.writeValue("foodStorage", $foodStorage);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        $status = FarmFieldStatusEnum.valueOf(jsonData.getString("status"));
        $type = FarmFieldTypeEnum.valueOf(jsonData.getString("type"));
        $foodStorage = jsonData.getInt("foodStorage");
        JsonValue value = jsonData.get("animals");

        for (JsonValue v : value) {
            switch ($type) {
                case Chicken:
                    $farmAnimals.add(new FarmAnimalChicken(v));
                    break;
                case Cow:
                    $farmAnimals.add(new FarmAnimalCow(v));
                    break;
                case Pig:
                    $farmAnimals.add(new FarmAnimalPig(v));
                    break;
            }
        }

        changeTexture();
    }
}
