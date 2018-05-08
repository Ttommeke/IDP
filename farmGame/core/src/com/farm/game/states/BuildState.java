package com.farm.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Scaling;
import com.farm.game.Assets;
import com.farm.game.FarmGameMain;
import com.farm.game.spriteData.FarmTreeFruitTypeEnum;
import com.farm.game.sprites.FarmField;
import com.farm.game.sprites.FarmLand;
import com.farm.game.sprites.FarmObject;
import com.farm.game.sprites.FarmTree;

public class BuildState extends State {
    private Rectangle inventoryButtonBounds, buildButtonBounds, moveButtonBounds,
            deleteButtonBounds, settingsButtonBounds, mapButtonBounds,
            cancenButtonBounds, acceptButtonBounds;
    private FarmObject farmObject;
    private boolean justBought;

    public BuildState(GameStateManager gsm, FarmObject object) {
        super(gsm);
        $camera.setToOrtho(true, FarmGameMain.WIDTH, FarmGameMain.HEIGHT);
        FarmGameMain.landscape.setBackup();
        farmObject = object;
        justBought = true;

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
                $gsm.push(new MenuState($gsm, FarmGameMain.inventory.getScrollTable(), "Goederen", null));
            } else if (buildButtonBounds.contains(Gdx.input.getX(), Gdx.input.getY())) {
                FarmGameMain.landscape.restoreBackup();
                $gsm.pop();
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
                FarmGameMain.androidEnvironmentCallback.startMapsActivity();
                //$gsm.set(new MapState($gsm));
            } else if (acceptButtonBounds.contains(Gdx.input.getX(), Gdx.input.getY())) {
                payForObject();
                FarmGameMain.landscape.setBackup();
                FarmGameMain.settings.saveToJSON();
                $gsm.pop();
            } else if (cancenButtonBounds.contains(Gdx.input.getX(), Gdx.input.getY())) {
                FarmGameMain.landscape.restoreBackup();
                $gsm.pop();
            } else {
                if(justBought) {
                    justBought = !FarmGameMain.landscape.moveNewIntoPosition(Gdx.input.getX(), Gdx.input.getY(), farmObject);
                } else {
                    FarmGameMain.landscape.moveIntoPosition(Gdx.input.getX(), Gdx.input.getY(), farmObject);
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

        sb.draw(Assets.acceptTexture, FarmGameMain.WIDTH - 138, FarmGameMain.HEIGHT - 10 - (128*5), 128, 128);
        sb.draw(Assets.cancelTexture, FarmGameMain.WIDTH - 138, FarmGameMain.HEIGHT - 10 - (128*6), 128, 128);

        // Info on screen
        String text = "Klik om te plaatsen";
        GlyphLayout gl = new GlyphLayout();
        gl.setText(FarmGameMain.blueFont, text);
        float textWidth = FarmGameMain.WIDTH / 2 - gl.width / 2;
        FarmGameMain.blueFont.draw(sb, text, textWidth, FarmGameMain.HEIGHT - 5);

        sb.end();

        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        FarmGameMain.landscape.drawPlacebleRect(farmObject.getAmountOfCells(), shapeRenderer);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    @Override
    public void dispose() { }

    private void payForObject() {
        if (farmObject.getClass() == FarmLand.class) {
            FarmGameMain.inventory.buySomething(4);
        } else if (farmObject.getClass() == FarmField.class) {
            FarmGameMain.inventory.buySomething(6);
        } else if (farmObject.getClass() == FarmTree.class) {
            FarmTree tree = (FarmTree)farmObject;
            if(tree.getFruitType() == FarmTreeFruitTypeEnum.Apple) {
                FarmGameMain.inventory.buySomething(61);
            } else if (tree.getFruitType() == FarmTreeFruitTypeEnum.Raspberry) {
                FarmGameMain.inventory.buySomething(42);
            }
        }
    }

    public static Table getBuildingsMenu(final GameStateManager gsm) {
        Skin skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));

        Table scrollTable = new Table();
        scrollTable.defaults().pad(10).width(256).height(256);

        // FarmLand
        Image farmLandImage = new Image(Assets.farmLandUnplantedTexture);
        farmLandImage.setScaling(Scaling.fit);
        TextButton buyFarmLand = new TextButton("Koop   4", skin);
        buyFarmLand.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(FarmGameMain.inventory.getCoins() >= 4) {
                    gsm.pop();
                    gsm.push(new BuildState(gsm, new FarmLand()));
                }
            }
        });

        // farmField
        Image farmFieldImage = new Image(Assets.farmFieldUninhabitedTexture);
        farmFieldImage.setScaling(Scaling.fit);
        TextButton buyFarmField = new TextButton("Koop   6", skin);
        buyFarmField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(FarmGameMain.inventory.getCoins() >= 6) {
                    gsm.pop();
                    gsm.push(new BuildState(gsm, new FarmField()));
                }
            }
        });

        // appleTree
        Image appleTreeImage = new Image(Assets.farmTreeApplesReady);
        appleTreeImage.setScaling(Scaling.fit);
        TextButton buyAppleTree = new TextButton("Koop   61", skin);
        buyAppleTree.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(FarmGameMain.inventory.getCoins() >= 61) {
                    gsm.pop();
                    gsm.push(new BuildState(gsm, new FarmTree(FarmTreeFruitTypeEnum.Apple)));
                }
            }
        });

        // raspberryBush
        Image raspberryBushImage = new Image(Assets.farmBushRaspberriesReady);
        raspberryBushImage.setScaling(Scaling.fit);
        TextButton buyRaspberryBush = new TextButton("Koop   42", skin);
        buyRaspberryBush.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(FarmGameMain.inventory.getCoins() >= 42) {
                    gsm.pop();
                    gsm.push(new BuildState(gsm, new FarmTree(FarmTreeFruitTypeEnum.Raspberry)));
                }
            }
        });

        scrollTable.add(farmLandImage);
        scrollTable.add(farmFieldImage);
        scrollTable.add(appleTreeImage);
        scrollTable.add(raspberryBushImage);
        scrollTable.row();
        scrollTable.add(buyFarmLand);
        scrollTable.add(buyFarmField);
        scrollTable.add(buyAppleTree);
        scrollTable.add(buyRaspberryBush);
        scrollTable.row();

        return scrollTable;
    }
}
