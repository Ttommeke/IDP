package com.farm.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.farm.game.Assets;
import com.farm.game.FarmGameMain;

public class MapState extends State {
    private Rectangle farmButtonBounds;

    public MapState(GameStateManager gsm) {
        super(gsm);

        farmButtonBounds = new Rectangle(FarmGameMain.WIDTH - 138, FarmGameMain.HEIGHT - 138, 128, 128);
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            if (farmButtonBounds.contains(Gdx.input.getX(), Gdx.input.getY())) {
                $gsm.set(new FarmState($gsm));
            }
        }

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        Gdx.gl.glClearColor(0, 0, 125/255f, 1); // I'm blue dabediedabeday...

        // Menus
        sb.draw(Assets.farmFieldUninhabitedTexture, FarmGameMain.WIDTH - 138, 10, 128, 128);

        sb.end();
    }

    @Override
    public void dispose() {

    }
}
