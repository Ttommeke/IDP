package com.farm.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.farm.game.states.GameStateManager;
import com.farm.game.states.LoginState;

public class FarmGameMain extends ApplicationAdapter {
	public static int WIDTH = 1500;
	public static int HEIGHT = 1000;

	public static BitmapFont font;
	public static BitmapFont fnt;

	private GameStateManager $gsm;
	private SpriteBatch $batch;
	
	@Override
	public void create () {
        $batch = new SpriteBatch();
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("Xpressive Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 100;
        param.borderWidth = 2;
        param.borderColor = Color.BLACK;
        font = gen.generateFont(param);

        param.size = 50;
        fnt = gen.generateFont(param);
        gen.dispose();

        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();
        Assets.load();
        Settings.load();

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

    public static void drawButton(SpriteBatch sb, String text, float x, float y, float width, float height) {
        GlyphLayout gl = new GlyphLayout();
        gl.setText(font, text);
        float textWidth = width / 2 - gl.width / 2;
        float textHeight = height / 2 + gl.height / 2;
        sb.draw(Assets.buttonTexture, x, y, width, height);
        font.draw(sb, text, x + textWidth, y + textHeight);
    }
}
