package com.farm.game;

import org.json.JSONObject;

public interface Callback {
    void taskCompleted(int status_code, JSONObject json);
}
