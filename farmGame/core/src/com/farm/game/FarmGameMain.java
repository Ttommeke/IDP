package com.farm.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.farm.game.states.GameStateManager;
import com.farm.game.states.LoginState;

public class FarmGameMain extends ApplicationAdapter {
	public static int WIDTH = 1500;
	public static int HEIGHT = 1000;

    public static FarmLandscape landscape;
    public static Inventory inventory;
    public static Settings settings;

	public static BitmapFont font;
	public static BitmapFont redFont;
	public static BitmapFont blueFont;

	private GameStateManager $gsm;
	private SpriteBatch $batch;
	
	@Override
	public void create () {
        $batch = new SpriteBatch();
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("Xpressive Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 75;
        param.borderWidth = 2;
        param.borderColor = Color.BLACK;
        font = gen.generateFont(param);

        param.size = 100;
        param.color = Color.RED;
        redFont = gen.generateFont(param);

        param.color = Color.BLUE;
        blueFont = gen.generateFont(param);
        gen.dispose();

        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();

        Assets.load();

        landscape = new FarmLandscape();
        inventory = new Inventory();
        settings = new Settings();

        $batch = new SpriteBatch();
        $gsm = new GameStateManager();
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        $gsm.push(new LoginState($gsm));
	}

	@Override
	public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        $gsm.handleInput();
        $gsm.update(Gdx.graphics.getDeltaTime());
        $gsm.render($batch);
	}
	
	@Override
	public void dispose () {
        Assets.dispose();
        $batch.dispose();
	}
}
