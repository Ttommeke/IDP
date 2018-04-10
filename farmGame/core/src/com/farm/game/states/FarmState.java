package com.farm.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.farm.game.Assets;
import com.farm.game.FarmGameMain;
import com.farm.game.sprites.FarmLand;

import java.util.ArrayList;

public class FarmState extends State {
    private ArrayList<FarmLand> $fields;
    private Rectangle $inventoryButtonBounds;

    public FarmState(GameStateManager gsm) {
        super(gsm);
        $fields = new ArrayList<FarmLand>();
        $camera.setToOrtho(false, FarmGameMain.WIDTH, FarmGameMain.HEIGHT);
        $inventoryButtonBounds = new Rectangle(FarmGameMain.WIDTH - 133, 0, 128, 128);
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            if ($inventoryButtonBounds.contains(Gdx.input.getX(), Gdx.input.getY())) {
                System.out.println("inventory");
                //$gsm.push(inventoryState);
            }
        }
    }

    @Override
    public void update(float dt) {}

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        Gdx.gl.glClearColor(0, 125/255f, 0, 1);
        // Objects

        // Menus
        sb.draw(Assets.inventoryTexture, FarmGameMain.WIDTH - 133, FarmGameMain.HEIGHT - 133, 128, 128);

        sb.end();
    }

    @Override
    public void dispose() {}
}
