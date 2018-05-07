package com.farm.game;

public interface AndroidEnvironmentCallback {
    void startMapsActivity();
    void login(String username, String password);
    void setToken(String token);
    void saveState();
    void loadState();
}
