package com.farm.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by bjorn on 3-5-2017.
 */

public abstract class State {
    protected OrthographicCamera $camera;
    protected Vector3 $mouse;
    protected GameStateManager $gsm;

    public State(GameStateManager gsm) {
        $gsm = gsm;
        $camera = new OrthographicCamera();
        $mouse = new Vector3();
    }

    public abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();
}
