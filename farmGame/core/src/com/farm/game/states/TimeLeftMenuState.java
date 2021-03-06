package com.farm.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.farm.game.Assets;
import com.farm.game.FarmGameMain;
import com.farm.game.sprites.FarmLand;
import com.farm.game.sprites.FarmObject;
import com.farm.game.sprites.FarmTree;

public class TimeLeftMenuState extends State {
    private Stage $stage;
    private Rectangle $menuBounds, $exitBounds;
    private long timer, additionTime;
    private Label timeLeft;

    public TimeLeftMenuState(final GameStateManager gsm, final Texture texture, final long timer, final long additionTime, final String title, final FarmObject farmObject) {
        super(gsm);

        $stage = new Stage(new ScreenViewport());

        FarmLand farmLand = null;
        FarmTree farmTree = null;
        if(farmObject.getClass() == FarmLand.class) {
            farmLand = (FarmLand) farmObject;
        } else {
            farmTree = (FarmTree) farmObject;
        }

        this.timer = timer;
        this.additionTime = additionTime;

        Skin skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));

        Label titleLabel = new Label(title, skin);
        titleLabel.setFontScale(3);

        Table scrollTable = new Table();
        scrollTable.defaults().pad(10).width(128).height(128);

        Image typeImage = new Image(texture);
        typeImage.setScaling(Scaling.fit);
        Image timerImage = new Image(Assets.timerTexture);
        timerImage.setScaling(Scaling.fit);
        scrollTable.add(typeImage).width(256).height(256).colspan(2);

        if(farmTree != null && !farmTree.isFertilized()) {
            Image superFertilizerImage = new Image(Assets.superFertilizerTexture);
            superFertilizerImage.setScaling(Scaling.fit);
            final Label superFertilizer = new Label(String.valueOf(FarmGameMain.inventory.getAmountOfFertilizer()), skin);
            superFertilizer.setFontScale(2);
            final FarmTree finalFarmTree = farmTree;
            superFertilizerImage.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(FarmGameMain.inventory.getAmountOfFertilizer() >= 1) {
                        finalFarmTree.useFertilizer();
                        FarmGameMain.inventory.useFertilizer();
                        gsm.set(new TimeLeftMenuState(gsm, texture, timer, additionTime / 2, title, farmObject));
                        FarmGameMain.settings.saveToJSON();
                    }
                    return true; //the input multiplexer will stop trying to handle this touch
                }
            });
            scrollTable.add();
            scrollTable.add(superFertilizerImage).width(128).height(128).colspan(2);
            scrollTable.add(superFertilizer).center();
        } else if(farmLand != null && !farmLand.isFertilized()) {
            Image superFertilizerImage = new Image(Assets.superFertilizerTexture);
            superFertilizerImage.setScaling(Scaling.fit);
            final Label superFertilizer = new Label(String.valueOf(FarmGameMain.inventory.getAmountOfFertilizer()), skin);
            superFertilizer.setFontScale(2);
            final FarmLand finalFarmLand = farmLand;
            superFertilizerImage.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(FarmGameMain.inventory.getAmountOfFertilizer() >= 1) {
                        finalFarmLand.useFertilizer();
                        FarmGameMain.inventory.useFertilizer();
                        gsm.set(new TimeLeftMenuState(gsm, texture, timer, additionTime/2, title, farmObject));
                        FarmGameMain.settings.saveToJSON();
                    }
                    return true; //the input multiplexer will stop trying to handle this touch
                }
            });
            scrollTable.add();
            scrollTable.add(superFertilizerImage).width(128).height(128).colspan(2);
            scrollTable.add(superFertilizer).center();
        }

        long fullSecondsLeft = (timer + additionTime - System.currentTimeMillis())/1000;
        long minutesLeft = fullSecondsLeft/60;
        long secondsLeft = fullSecondsLeft%60;
        timeLeft = new Label(String.valueOf(minutesLeft)
                + ":" + ("00" + String.valueOf(secondsLeft)).substring(String.valueOf(secondsLeft).length()), skin);
        timeLeft.setFontScale(2);

        scrollTable.row();
        scrollTable.add(timerImage);
        scrollTable.add(timeLeft).center();
        scrollTable.row();

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
        if(timer + additionTime - System.currentTimeMillis() <= 0) {
            $gsm.pop();
        }

        long fullSecondsLeft = (timer + additionTime - System.currentTimeMillis())/1000;
        long minutesLeft = fullSecondsLeft/60;
        long secondsLeft = fullSecondsLeft%60;

        timeLeft.setText(String.valueOf(minutesLeft)
                + ":" + ("00" + String.valueOf(secondsLeft)).substring(String.valueOf(secondsLeft).length()));
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
}