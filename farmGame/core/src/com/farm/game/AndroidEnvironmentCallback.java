package com.farm.game;

import com.farm.game.states.GameStateManager;

public interface AndroidEnvironmentCallback {
    void startMapsActivity();
    void login(String username, String password, final GameStateManager gsm);
    void setToken(String token);
    void saveState();
    void loadState();
}
