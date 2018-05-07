package com.farm.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.farm.game.Assets;
import com.farm.game.FarmGameMain;
import com.farm.game.spriteData.FarmAnimal;
import com.farm.game.spriteData.FarmAnimalPig;
import com.farm.game.sprites.FarmField;

import java.util.ArrayList;

public class FieldMenuState extends State {
    private Stage $stage;
    private Rectangle $menuBounds, $exitBounds;
    private ArrayList<Long> $additionTimes;
    private ArrayList<Long> $initialTimers;
    private ArrayList<Label> $timeLefts;
    private FarmField $field;
    private int sellPrice = 0;

    public FieldMenuState(GameStateManager gsm, FarmField field) {
        super(gsm);
        $stage = new Stage(new ScreenViewport());
        Texture childTexture = null, adultTexture = null, productTexture = null;

        $timeLefts = new ArrayList<>();
        $additionTimes = new ArrayList<>();
        $initialTimers = new ArrayList<>();
        $field = field;

        Skin skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));

        Label titleLabel = new Label("Veld", skin);
        titleLabel.setFontScale(3);

        Table scrollTable = new Table();
        scrollTable.defaults().pad(10).width(128).height(128);

        switch ($field.getType()) {
            case Chicken:
                childTexture = Assets.chickTexture;
                adultTexture = Assets.chickenTexture;
                productTexture = Assets.eggTexture;
                sellPrice = 41;
                break;
            case Cow:
                childTexture = Assets.calfTexture;
                adultTexture = Assets.cowTexture;
                productTexture = Assets.milkTexture;
                sellPrice = 162;
                break;
            case Pig:
                childTexture = Assets.pigletTexture;
                adultTexture = Assets.pigTexture;
                sellPrice = 142;
                break;
        }

        if(!$field.isFull()) {
            Image buyNewAnimal = new Image(Assets.buyTexture);
            buyNewAnimal.setScaling(Scaling.fit);
            buyNewAnimal.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(FarmGameMain.inventory.getCoins() >= FarmField.getCostForType($field.getType())) {
                        $field.addAnimal($field.getType());
                        $gsm.pop();
                        $gsm.push(new FieldMenuState($gsm, $field));
                    }
                    return true; //the input multiplexer will stop trying to handle this touch
                }
            });
            scrollTable.add(buyNewAnimal);
        } else {
            scrollTable.add();
        }

        boolean aProductIsReady = false;
        for(final FarmAnimal farmAnimal: $field.getFarmAnimals()) {
            if (farmAnimal.isProductReady()) { // if a product is ready -> show collect all button
                aProductIsReady = true;
            }
        }

        if(aProductIsReady) {
            Image collectAllImage = new Image(productTexture);
            collectAllImage.setScaling(Scaling.fit);
            collectAllImage.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    for(final FarmAnimal farmAnimal: $field.getFarmAnimals()) {
                        if (farmAnimal.isProductReady()) {
                            farmAnimal.collectProduct();
                        }
                    }
                    FarmGameMain.settings.saveToJSON();
                    $gsm.pop();
                    $gsm.push(new FieldMenuState($gsm, $field));
                    return true; //the input multiplexer will stop trying to handle this touch
                }
            });
            scrollTable.add(collectAllImage);
        } else {
            scrollTable.add();
        }

        scrollTable.add();
        Image addFoodImage = new Image(Assets.feedTexture);
        addFoodImage.setScaling(Scaling.fit);
        addFoodImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                $gsm.push(new MenuState($gsm, getFoodMenu(), "Gebruik", $field));
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });
        scrollTable.add(addFoodImage);

        Label foodStorageLabel = new Label(String.valueOf($field.getAmountOfFoodStorage()), skin);
        foodStorageLabel.setFontScale(2);
        scrollTable.add(foodStorageLabel);
        scrollTable.row();

        for(final FarmAnimal farmAnimal: $field.getFarmAnimals()) {
            Image typeImage = new Image();
            long additionTime = 0;
            if(farmAnimal.isAdult()) {
                typeImage.setDrawable(new TextureRegionDrawable(new TextureRegion(adultTexture)));
                additionTime = farmAnimal.getProductTimeNeededInMillis();
            } else if (!farmAnimal.isAdult()) {
                typeImage.setDrawable(new TextureRegionDrawable(new TextureRegion(childTexture)));
                additionTime = farmAnimal.getGrowTimeInMillis();
            }
            typeImage.setScaling(Scaling.fit);

            long fullSecondsLeft = (farmAnimal.getTimer() + additionTime - System.currentTimeMillis())/1000;;
            long minutesLeft = fullSecondsLeft/60;
            long secondsLeft = fullSecondsLeft%60;
            Label timeLeft = new Label(String.valueOf(minutesLeft)
                    + ":" + ("00" + String.valueOf(secondsLeft)).substring(String.valueOf(secondsLeft).length()), skin);
            timeLeft.setFontScale(2);

            $initialTimers.add(farmAnimal.getTimer());
            $additionTimes.add(additionTime);
            $timeLefts.add(timeLeft);

            scrollTable.add(typeImage).width(128).height(128);

            if (farmAnimal.isProductReady()) { // if product is ready -> show product -> when clicked collect it
                Image productImage = new Image(productTexture);
                productImage.setScaling(Scaling.fit);
                productImage.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        farmAnimal.collectProduct();
                        FarmGameMain.settings.saveToJSON();
                        $gsm.pop();
                        $gsm.push(new FieldMenuState($gsm, $field));
                        return true; //the input multiplexer will stop trying to handle this touch
                    }
                });
                scrollTable.add(productImage);
                scrollTable.add();
                scrollTable.add();
                scrollTable.add();
            } else if(farmAnimal.isAdult()) { // if animal is adult -> show sell button
                if (!farmAnimal.isEating()) { // If animal is not eating -> show feed menu opener which will feed the animal else show timer
                    if(farmAnimal.getClass() != FarmAnimalPig.class)
                    {
                        Image feedImage = new Image(Assets.feedTexture);
                        feedImage.setScaling(Scaling.fit);
                        feedImage.addListener(new ClickListener() {
                            @Override
                            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                if($field.getFoodFromStorage()) {
                                    farmAnimal.feedAnimal();
                                }
                                return true; //the input multiplexer will stop trying to handle this touch
                            }
                        });
                        scrollTable.add(feedImage);
                    }
                } else {
                    scrollTable.add(timeLeft).right();
                }

                Image sellImage = new Image(Assets.sellTexture);
                sellImage.setScaling(Scaling.fit);
                sellImage.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        FarmGameMain.inventory.earnSomething(sellPrice);
                        $field.removeAnimal(farmAnimal);
                        FarmGameMain.settings.saveToJSON();
                        $gsm.pop();
                        if($field.getFarmAnimals().size() > 0) {
                            $gsm.push(new FieldMenuState($gsm, $field));
                        }
                        return true; //the input multiplexer will stop trying to handle this touch
                    }
                });
                scrollTable.add();
                scrollTable.add();
                scrollTable.add(sellImage);
            } else {
                scrollTable.add(timeLeft).right();
            }
            scrollTable.row();
        }

        ScrollPane scroller = new ScrollPane(scrollTable);

        Table table = new Table();
        table.setPosition(FarmGameMain.WIDTH/2, FarmGameMain.HEIGHT/2 + 50);
        table.defaults().pad(10);
        table.add(titleLabel).center().height(100);
        table.row();
        table.add(scroller).height(576).width(1152);

        $stage.addActor(table);
        Gdx.input.setInputProcessor($stage);

        $menuBounds = new Rectangle(192, FarmGameMain.HEIGHT - 960, 1408, 768);
        $exitBounds = new Rectangle(1408+192-143, FarmGameMain.HEIGHT - 960, 143, 143);
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            if (!$menuBounds.contains(Gdx.input.getX(), Gdx.input.getY())
                    || $exitBounds.contains(Gdx.input.getX(), Gdx.input.getY())) {
                $gsm.pop();
            }
        }
    }

    @Override
    public void update(float dt) {
        int counter = -1;
        for(FarmAnimal animal: $field.getFarmAnimals()) {
            ++counter;

            if((!animal.isAdult() || animal.isEating())) {
                if($initialTimers.get(counter) + $additionTimes.get(counter) - System.currentTimeMillis() <= 0) {
                    $field.update();
                    $gsm.pop();
                    $gsm.push(new FieldMenuState($gsm, $field));
                }
            }

            long fullSecondsLeft = ($initialTimers.get(counter) + $additionTimes.get(counter) - System.currentTimeMillis())/1000;
            long minutesLeft = fullSecondsLeft/60;
            long secondsLeft = fullSecondsLeft%60;

            $timeLefts.get(counter).setText(String.valueOf(minutesLeft)
                    + ":" + ("00" + String.valueOf(secondsLeft)).substring(String.valueOf(secondsLeft).length()));
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(Assets.plateTexture, 192, 192, 1408, 768);
        sb.draw(Assets.cancelTexture, 1408+192-143, 768+192-143, 128, 128);
        sb.end();

        $stage.draw();
        $stage.act();
    }

    @Override
    public void dispose() {
        $stage.dispose();
    }

    private Table getFoodMenu() {
        Skin skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));

        Table scrollTable = new Table();
        scrollTable.defaults().pad(10).width(128).height(128);

        // Grain
        Image grainImage = new Image(Assets.grainTexture);
        grainImage.setScaling(Scaling.fit);
        final Label grain = new Label(String.valueOf(FarmGameMain.inventory.getAmountOfGrain()), skin);
        grain.setFontScale(2);
        Image useGrainImage = new Image(Assets.feedTexture);
        useGrainImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(FarmGameMain.inventory.useGrain()){
                    grain.setText(String.valueOf(FarmGameMain.inventory.getAmountOfGrain()));
                    $field.addFoodToStorage(1);
                    FarmGameMain.settings.saveToJSON();
                }
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });

        scrollTable.add(grainImage);
        scrollTable.add(grain).center();
        scrollTable.add(useGrainImage);
        scrollTable.row();

        // Carrot
        Image carrotImage = new Image(Assets.carrotTexture);
        carrotImage.setScaling(Scaling.fit);
        final Label carrot = new Label(String.valueOf(FarmGameMain.inventory.getAmountOfCarrot()), skin);
        carrot.setFontScale(2);
        Image useCarrotImage = new Image(Assets.feedTexture);
        useCarrotImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(FarmGameMain.inventory.useCarrot()) {
                    carrot.setText(String.valueOf(FarmGameMain.inventory.getAmountOfCarrot()));
                    $field.addFoodToStorage(1);
                    FarmGameMain.settings.saveToJSON();
                }
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });

        scrollTable.add(carrotImage);
        scrollTable.add(carrot).center();
        scrollTable.add(useCarrotImage);
        scrollTable.row();

        // Potato
        Image potatoImage = new Image(Assets.potatoTexture);
        potatoImage.setScaling(Scaling.fit);
        final Label potato = new Label(String.valueOf(FarmGameMain.inventory.getAmountOfPotato()), skin);
        potato.setFontScale(2);
        Image usePotatoImage = new Image(Assets.feedTexture);
        usePotatoImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(FarmGameMain.inventory.usePotato()) {
                    potato.setText(String.valueOf(FarmGameMain.inventory.getAmountOfPotato()));
                    $field.addFoodToStorage(1);
                    FarmGameMain.settings.saveToJSON();
                }
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });

        scrollTable.add(potatoImage);
        scrollTable.add(potato).center();
        scrollTable.add(usePotatoImage);
        scrollTable.row();

        // Strawberry
        Image strawberryImage = new Image(Assets.strawberryTexture);
        strawberryImage.setScaling(Scaling.fit);
        final Label strawberry = new Label(String.valueOf(FarmGameMain.inventory.getAmountOfStrawberry()), skin);
        strawberry.setFontScale(2);
        Image useStrawberryImage = new Image(Assets.feedTexture);
        useStrawberryImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(FarmGameMain.inventory.useStrawberry()) {
                    strawberry.setText(String.valueOf(FarmGameMain.inventory.getAmountOfStrawberry()));
                    $field.addFoodToStorage(1);
                    FarmGameMain.settings.saveToJSON();
                }
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });

        scrollTable.add(strawberryImage);
        scrollTable.add(strawberry).center();
        scrollTable.add(useStrawberryImage);
        scrollTable.row();

        // Eggplant
        Image eggplantImage = new Image(Assets.eggplantTexture);
        eggplantImage.setScaling(Scaling.fit);
        final Label eggplant = new Label(String.valueOf(FarmGameMain.inventory.getAmountOfEggplant()), skin);
        eggplant.setFontScale(2);
        Image useEggplantImage = new Image(Assets.feedTexture);
        useEggplantImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(FarmGameMain.inventory.useEggplant()){
                    eggplant.setText(String.valueOf(FarmGameMain.inventory.getAmountOfEggplant()));
                    $field.addFoodToStorage(1);
                    FarmGameMain.settings.saveToJSON();
                }
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });

        scrollTable.add(eggplantImage);
        scrollTable.add(eggplant).center();
        scrollTable.add(useEggplantImage);
        scrollTable.row();

        // superGrain
        Image superGrainImage = new Image(Assets.superGrainTexture);
        superGrainImage.setScaling(Scaling.fit);
        final Label superGrain = new Label(String.valueOf(FarmGameMain.inventory.getAmountOfSuperGrain()), skin);
        superGrain.setFontScale(2);
        Image useSuperGrainImage = new Image(Assets.feedTexture);
        useSuperGrainImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(FarmGameMain.inventory.useSuperGrain()){
                    superGrain.setText(String.valueOf(FarmGameMain.inventory.getAmountOfSuperGrain()));
                    $field.addFoodToStorage(5);
                    FarmGameMain.settings.saveToJSON();
                }
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });

        scrollTable.add(superGrainImage);
        scrollTable.add(superGrain).center();
        scrollTable.add(useSuperGrainImage);
        scrollTable.row();

        return scrollTable;
    }
}
