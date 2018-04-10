package com.farm.game;

import com.badlogic.gdx.graphics.Texture;

public class Assets {
    public static Texture backgroundTexture;
    public static Texture buttonTexture;
    public static Texture titleTexture;

    // Playing textures
    public static Texture unplantedFieldTexture;

    // Button textures
    public static Texture inventoryTexture;

    public static void load() {
        backgroundTexture = new Texture("background.jpg");
        buttonTexture = new Texture("button.png");
        titleTexture = new Texture("title.png");

        // Playing textures
        unplantedFieldTexture = new Texture("fieldUnplanted.png");

        // Button textures
        inventoryTexture = new Texture("warehouse.png");
    }

    public static void dispose() {
        backgroundTexture.dispose();
        buttonTexture.dispose();
        titleTexture.dispose();

        // Playing textures
        unplantedFieldTexture.dispose();

        // Button textures
        inventoryTexture.dispose();
    }
}
