package com.farm.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.farm.game.Assets;
import com.farm.game.FarmGameMain;

public class FarmState extends State implements InputProcessor{
    private Rectangle $inventoryButtonBounds;

    public FarmState(GameStateManager gsm) {
        super(gsm);
        Gdx.input.setInputProcessor(this);
        $camera.setToOrtho(true, FarmGameMain.WIDTH, FarmGameMain.HEIGHT);

        $inventoryButtonBounds = new Rectangle(FarmGameMain.WIDTH - 133, 0, 128, 128);
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            if ($inventoryButtonBounds.contains(Gdx.input.getX(), Gdx.input.getY())) {
                $gsm.push(new MenuState($gsm, FarmGameMain.inventory.getScrollTable(), "Inventory"));
            }

            FarmGameMain.landscape.handleTouch(Gdx.input.getX(), Gdx.input.getY(), $gsm);
        }
    }

    @Override
    public void update(float dt) {
        $camera.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        Gdx.gl.glClearColor(0, 125/255f, 0, 1);
        // Grid + Objects on grid
        FarmGameMain.landscape.drawObjects(sb);

        // Menus
        sb.draw(Assets.inventoryTexture, FarmGameMain.WIDTH - 133, FarmGameMain.HEIGHT - 133, 128, 128);

        sb.end();
    }

    @Override
    public void dispose() {}


    /**
     * Section
     * InputProcessor functions
     */
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        float x = Gdx.input.getDeltaX();
        float y = Gdx.input.getDeltaY();

        //System.out.println("x: " + x + "\ny: " + y);
        $camera.translate(-x*5,y*5);
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
