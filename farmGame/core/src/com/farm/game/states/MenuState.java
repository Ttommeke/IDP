package com.farm.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.farm.game.Assets;
import com.farm.game.FarmGameMain;
import com.farm.game.sprites.FarmField;

public class MenuState extends State {
    private Stage $stage;
    private Rectangle $menuBounds, $exitBounds;
    private FarmField $thatFeedMenuThough;

    public MenuState(GameStateManager gsm, Table scrollTable, String title, FarmField thatFeedMenuThough) {
        super(gsm);

        $thatFeedMenuThough = thatFeedMenuThough;
        $stage = new Stage(new ScreenViewport());
        Skin skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));

        Label titleLabel = new Label(title, skin);
        titleLabel.setFontScale(8);

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
                if($thatFeedMenuThough != null) {
                    $gsm.pop();
                    $gsm.push(new FieldMenuState($gsm, $thatFeedMenuThough));
                }
            }
        }
    }

    @Override
    public void update(float dt) {

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