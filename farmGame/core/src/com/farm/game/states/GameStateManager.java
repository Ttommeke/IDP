package com.farm.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GameStateManager {
    private Stack<State> $states;

    public GameStateManager() {
        $states = new Stack<State>();
    }

    public void reset(State state) {
        $states.clear();
        $states.push(state);
    }

    public void push(State state) {
        $states.push(state);
    }

    public void pop() {
        $states.pop().dispose();
    }

    public void set(State state) {
        $states.pop().dispose();
        $states.push(state);
    }
    
    public void handleInput() { $states.peek().handleInput(); }

    public void update(float dt) {
        $states.peek().update(dt);
    }

    public void render(SpriteBatch sb) {
        $states.peek().render(sb);
    }
}
