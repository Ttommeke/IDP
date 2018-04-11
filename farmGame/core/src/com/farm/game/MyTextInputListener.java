package com.farm.game;

import com.badlogic.gdx.Input;

/**
 * Created by bjorn on 29-5-2017.
 */

public class MyTextInputListener implements Input.TextInputListener {
    private String $input;

    @Override
    public void input(String text) {
        $input = text;
    }

    @Override
    public void canceled() {
        $input = "Ano";
    }

    public String getInput() {
        return $input;
    }
}
