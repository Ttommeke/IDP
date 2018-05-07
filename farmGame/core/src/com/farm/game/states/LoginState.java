package com.farm.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.farm.game.Assets;
import com.farm.game.FarmGameMain;

public class LoginState extends State {
    private String $userName, $password;
    private Stage $stage;

    public LoginState(final GameStateManager gsm) {
        super(gsm);
        $userName = "";
        $password = "";

        $stage = new Stage(new ScreenViewport());

        Skin skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));

        Table table = new Table();
        table.setFillParent(true);
        table.setPosition(0, -300);
        table.defaults().pad(10);

        Label username = new Label("Gebruikersnaam : ", skin);
        username.setFontScale(2);
        final TextField usernameField = new TextField("", skin);

        Label password = new Label("Wachtwoord : ", skin);
        password.setFontScale(2);
        final TextField passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');

        TextButton login = new TextButton("Login", skin);
        login.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                $userName = usernameField.getText();
                $password = passwordField.getText();
                FarmGameMain.settings.login($userName, $password, $gsm);
                //$gsm.set(new FarmState($gsm));
            }
        });

        table.row();
        table.add(username).left();
        table.add(usernameField).width(400).height(50);
        table.row();
        table.add(password).left();
        table.add(passwordField).width(400).height(50);
        table.row();
        table.add();
        table.add(login).width(400).height(50);

        $stage.addActor(table);
        Gdx.input.setInputProcessor($stage);
    }

    @Override
    public void handleInput() { }

    @Override
    public void update(float dt) {
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(Assets.backgroundTexture, 0, 0, FarmGameMain.WIDTH, FarmGameMain.HEIGHT);
        sb.draw(Assets.titleTexture, (FarmGameMain.WIDTH / 2) - (750 / 2), FarmGameMain.HEIGHT - 600, 750, 500);
        sb.end();

        $stage.draw();
        $stage.act();
    }

    @Override
    public void dispose() {
        $stage.dispose();
    }
}
