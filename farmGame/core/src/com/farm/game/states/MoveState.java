package com.farm.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.farm.game.Assets;
import com.farm.game.FarmGameMain;
import com.farm.game.sprites.FarmObject;
import com.farm.game.sprites.GridSquare;

public class MoveState extends State {
    private Rectangle inventoryButtonBounds, buildButtonBounds, moveButtonBounds,
            deleteButtonBounds, settingsButtonBounds, mapButtonBounds,
            cancenButtonBounds, acceptButtonBounds;
    private boolean moving;
    private FarmObject farmObject;

    public MoveState(GameStateManager gsm) {
        super(gsm);
        $camera.setToOrtho(true, FarmGameMain.WIDTH, FarmGameMain.HEIGHT);
        moving = false;
        FarmGameMain.landscape.setBackup();

        // 10 padding + 128 texture
        inventoryButtonBounds = new Rectangle(FarmGameMain.WIDTH - 138, 10, 128, 128);
        buildButtonBounds = new Rectangle(FarmGameMain.WIDTH - 138, 10 + 128, 128, 128);
        moveButtonBounds = new Rectangle(FarmGameMain.WIDTH - 138, 10 + (128*2), 128, 128);
        deleteButtonBounds = new Rectangle(FarmGameMain.WIDTH - 138, 10 + (128*3), 128, 128);
        settingsButtonBounds = new Rectangle(FarmGameMain.WIDTH - 138, FarmGameMain.HEIGHT - 10 - (128*2), 128, 128);
        mapButtonBounds = new Rectangle(FarmGameMain.WIDTH - 138, FarmGameMain.HEIGHT - 138, 128, 128);

        acceptButtonBounds = new Rectangle(FarmGameMain.WIDTH - 138, 10 + (128*4), 128, 128);
        cancenButtonBounds = new Rectangle(FarmGameMain.WIDTH - 138, 10 + (128*5), 128, 128);
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            if (inventoryButtonBounds.contains(Gdx.input.getX(), Gdx.input.getY())) {
                $gsm.push(new MenuState($gsm, FarmGameMain.inventory.getScrollTable(), "Goederen"));
            } else if (buildButtonBounds.contains(Gdx.input.getX(), Gdx.input.getY())) {
                FarmGameMain.landscape.restoreBackup();
                $gsm.pop();
                $gsm.push(new MenuState($gsm, BuildState.getBuildingsMenu($gsm), "Kies Gebouw"));
            } else if (moveButtonBounds.contains(Gdx.input.getX(), Gdx.input.getY())) {
                FarmGameMain.landscape.restoreBackup();
                $gsm.pop();
            } else if (deleteButtonBounds.contains(Gdx.input.getX(), Gdx.input.getY())) {
                FarmGameMain.landscape.restoreBackup();
                $gsm.pop();
                $gsm.push(new RemoveState($gsm));
            } else if (settingsButtonBounds.contains(Gdx.input.getX(), Gdx.input.getY())) {
                System.out.println("Show settings menu");
            } else if (mapButtonBounds.contains(Gdx.input.getX(), Gdx.input.getY())) {
                FarmGameMain.landscape.restoreBackup();
                $gsm.pop();
                $gsm.set(new MapState($gsm));
            } else {
                if(moving) {
                    if (acceptButtonBounds.contains(Gdx.input.getX(), Gdx.input.getY())) {
                        moving = false;
                        FarmGameMain.landscape.setBackup();
                        FarmGameMain.settings.saveToJSON();
                    } else if (cancenButtonBounds.contains(Gdx.input.getX(), Gdx.input.getY())) {
                        moving = false;
                        FarmGameMain.landscape.restoreBackup();
                    } else {
                        FarmGameMain.landscape.moveIntoPosition(Gdx.input.getX(), Gdx.input.getY(), farmObject);
                    }
                } else {
                    farmObject = FarmGameMain.landscape.objectToMove(Gdx.input.getX(), Gdx.input.getY());
                    moving = farmObject.getClass() != GridSquare.class;
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

        // Info on screen
        String text;
        if(moving) {
            text = "Klik om te verplaatsen";

            sb.draw(Assets.acceptTexture, FarmGameMain.WIDTH - 138, FarmGameMain.HEIGHT - 10 - (128*5), 128, 128);
            sb.draw(Assets.cancelTexture, FarmGameMain.WIDTH - 138, FarmGameMain.HEIGHT - 10 - (128*6), 128, 128);
        } else {
            text = "Klik om een object te kiezen";
        }
        GlyphLayout gl = new GlyphLayout();
        gl.setText(FarmGameMain.blueFont, text);
        float textWidth = FarmGameMain.WIDTH / 2 - gl.width / 2;
        FarmGameMain.blueFont.draw(sb, text, textWidth, FarmGameMain.HEIGHT - 5);

        sb.end();
    }

    @Override
    public void dispose() { }
}
