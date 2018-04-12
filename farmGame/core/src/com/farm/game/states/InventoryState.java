package com.farm.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.farm.game.Assets;
import com.farm.game.FarmGameMain;

public class InventoryState extends State {
    private Stage $stage;
    private Rectangle $menuBounds;

    public InventoryState(GameStateManager gsm) {
        super(gsm);

        $stage = new Stage(new ScreenViewport());
        Skin skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));

        Label title = new Label("Inventory", skin);
        title.setFontScale(8);

        Image coinsImage = new Image(Assets.coinsTexture);
        coinsImage.setScaling(Scaling.fit);
        Label coins = new Label(String.valueOf(FarmGameMain.inventory.getCoins()), skin);
        coins.setFontScale(5);

        Image GrainSeedsImage = new Image(Assets.grainSeedsTexture);
        GrainSeedsImage.setScaling(Scaling.fit);
        Label GrainSeeds = new Label(String.valueOf(FarmGameMain.inventory.getAmountOfGrainSeeds()), skin);
        GrainSeeds.setFontScale(5);
        Image GrainSackImage = new Image(Assets.grainSackTexture);
        GrainSackImage.setScaling(Scaling.fit);
        Label GrainSacks = new Label(String.valueOf(FarmGameMain.inventory.getAmountOfGrainSacks()), skin);
        GrainSacks.setFontScale(5);

        Table scrollTable = new Table();
        scrollTable.defaults().pad(10).width(128).height(128);

        scrollTable.add(coinsImage);
        scrollTable.add(coins).center();
        scrollTable.row();
        scrollTable.add(GrainSeedsImage);
        scrollTable.add(GrainSeeds).center();
        scrollTable.add(GrainSackImage);
        scrollTable.add(GrainSacks).center();

        ScrollPane scroller = new ScrollPane(scrollTable);

        Table table = new Table();
        table.setPosition(FarmGameMain.WIDTH/2, FarmGameMain.HEIGHT/2 + 50);
        table.defaults().pad(10);
        table.add(title).center().height(100);
        table.row();
        table.add(scroller).height(450).width(1000);

        $stage.addActor(table);
        Gdx.input.setInputProcessor($stage);
        $menuBounds = new Rectangle(256, FarmGameMain.HEIGHT - 896, 1280, 640);
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            if (!$menuBounds.contains(Gdx.input.getX(), Gdx.input.getY())) {
                $gsm.pop();
            }
        }
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(Assets.plateTexture, 256, 256, 1280, 640);
        sb.end();

        $stage.draw();
        $stage.act();
    }

    @Override
    public void dispose() {
        $stage.dispose();
    }
}
