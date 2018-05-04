package com.farm.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.farm.game.Assets;
import com.farm.game.FarmGameMain;

public class FarmState extends State implements InputProcessor{
    private Rectangle inventoryButtonBounds, buildButtonBounds, moveButtonBounds,
            deleteButtonBounds, settingsButtonBounds, mapButtonBounds;

    public FarmState(GameStateManager gsm) {
        super(gsm);
        Gdx.input.setInputProcessor(this);
        $camera.setToOrtho(true, FarmGameMain.WIDTH, FarmGameMain.HEIGHT);

        // 10 padding + 128 texture
        inventoryButtonBounds = new Rectangle(FarmGameMain.WIDTH - 138, 10, 128, 128);
        buildButtonBounds = new Rectangle(FarmGameMain.WIDTH - 138, 10 + 128, 128, 128);
        moveButtonBounds = new Rectangle(FarmGameMain.WIDTH - 138, 10 + (128*2), 128, 128);
        deleteButtonBounds = new Rectangle(FarmGameMain.WIDTH - 138, 10 + (128*3), 128, 128);
        settingsButtonBounds = new Rectangle(FarmGameMain.WIDTH - 138, FarmGameMain.HEIGHT - 10 - (128*2), 128, 128);
        mapButtonBounds = new Rectangle(FarmGameMain.WIDTH - 138, FarmGameMain.HEIGHT - 138, 128, 128);
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            if (inventoryButtonBounds.contains(Gdx.input.getX(), Gdx.input.getY())) {
                $gsm.push(new MenuState($gsm, FarmGameMain.inventory.getScrollTable(), "Goederen"));
            } else if (buildButtonBounds.contains(Gdx.input.getX(), Gdx.input.getY())) {
                System.out.println("Show build menu -> place item");
            } else if (moveButtonBounds.contains(Gdx.input.getX(), Gdx.input.getY())) {
                System.out.println("Show move state");
            } else if (deleteButtonBounds.contains(Gdx.input.getX(), Gdx.input.getY())) {
                $gsm.push(new RemoveState($gsm));
            } else if (settingsButtonBounds.contains(Gdx.input.getX(), Gdx.input.getY())) {
                System.out.println("Show settings menu");
            } else if (mapButtonBounds.contains(Gdx.input.getX(), Gdx.input.getY())) {
               $gsm.set(new MapState($gsm));
            } else {
                FarmGameMain.landscape.handleTouch(Gdx.input.getX(), Gdx.input.getY(), $gsm);
            }
        }
    }

    @Override
    public void update(float dt) {
        $camera.update();
        FarmGameMain.landscape.updateGrid();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        Gdx.gl.glClearColor(0, 125/255f, 0, 1);
        // Grid + Objects on grid
        FarmGameMain.landscape.drawObjects(sb);

        // Menus => 10 padding + 128 texture
        sb.draw(Assets.inventoryTexture, FarmGameMain.WIDTH - 138, FarmGameMain.HEIGHT - 138, 128, 128);
        sb.draw(Assets.buildTexture, FarmGameMain.WIDTH - 138, FarmGameMain.HEIGHT - 10 - (128*2), 128, 128);
        sb.draw(Assets.moveTexture, FarmGameMain.WIDTH - 138, FarmGameMain.HEIGHT - 10 - (128*3), 128, 128);
        sb.draw(Assets.removeTexture, FarmGameMain.WIDTH - 138, FarmGameMain.HEIGHT - 10 - (128*4), 128, 128);
        sb.draw(Assets.settingsTexture, FarmGameMain.WIDTH - 138, 10 + 128, 128, 128);
        sb.draw(Assets.mapTexture, FarmGameMain.WIDTH - 138, 10, 128, 128);

        // Inventory info on screen
        sb.draw(Assets.coinsTexture, 0, FarmGameMain.HEIGHT - 69, 64, 64);
        FarmGameMain.font.draw(sb, String.valueOf(FarmGameMain.inventory.getCoins()), 69, FarmGameMain.HEIGHT - 5);

        sb.end();
    }

    @Override
    public void dispose() { }

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
