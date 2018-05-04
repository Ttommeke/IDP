package com.farm.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.farm.game.FarmGameMain;

public class RemoveState extends State {
    private Rectangle inventoryButtonBounds, buildButtonBounds, moveButtonBounds,
            deleteButtonBounds, settingsButtonBounds, mapButtonBounds;

    public RemoveState(GameStateManager gsm) {
        super(gsm);
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
                $gsm.pop();
                System.out.println("Show build menu -> place item");
            } else if (moveButtonBounds.contains(Gdx.input.getX(), Gdx.input.getY())) {
                $gsm.pop();
                System.out.println("Show move state");
            } else if (deleteButtonBounds.contains(Gdx.input.getX(), Gdx.input.getY())) {
                $gsm.pop();
            } else if (settingsButtonBounds.contains(Gdx.input.getX(), Gdx.input.getY())) {
                System.out.println("Show settings menu");
            } else if (mapButtonBounds.contains(Gdx.input.getX(), Gdx.input.getY())) {
                $gsm.pop();
                $gsm.set(new MapState($gsm));
            } else {
                FarmGameMain.landscape.handleDelete(Gdx.input.getX(), Gdx.input.getY(), $gsm);
            }
        }
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        Gdx.gl.glClearColor(0, 125/255f, 0, 1);

        // Info on screen
        String text = "Klik om te verwijderen";
        GlyphLayout gl = new GlyphLayout();
        gl.setText(FarmGameMain.redFont, text);
        float textWidth = FarmGameMain.WIDTH / 2 - gl.width / 2;
        FarmGameMain.redFont.draw(sb, text, textWidth, FarmGameMain.HEIGHT - 5);

        sb.end();
    }

    @Override
    public void dispose() { }
}
