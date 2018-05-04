package com.farm.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GameStateManager {
    private Stack<State> $states;
    // firstRender is to compensate that justTouched will be true when the buttons on a MenuState are clicked.
    // Without it the object behind the button will be triggered.
    private boolean firstRender;

    public GameStateManager() {
        $states = new Stack<State>();
    }

    public void reset(State state) {
        firstRender = true;
        $states.clear();
        $states.push(state);
    }

    public void push(State state) {
        firstRender = true;
        $states.push(state);
    }

    public void pop() {
        firstRender = true;
        $states.pop().dispose();
    }

    public void set(State state) {
        firstRender = true;
        $states.pop().dispose();
        $states.push(state);
    }
    
    public void handleInput() {
        if(firstRender) {
            firstRender = false;
        } else {
            $states.peek().handleInput();
        }
    }

    public void update(float dt) {
        $states.peek().update(dt);
    }

    public void render(SpriteBatch sb) {
        for(State state: $states) {
            state.render(sb);
        }
    }
}
