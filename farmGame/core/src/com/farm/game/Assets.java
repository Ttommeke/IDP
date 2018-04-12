package com.farm.game;

import com.badlogic.gdx.graphics.Texture;

public class Assets {
    public static Texture backgroundTexture;
    public static Texture buttonTexture;
    public static Texture titleTexture;
    public static Texture gridSquareTexture;
    public static Texture invisibleTexture;

    // Playing textures
    public static Texture farmLandUnplantedTexture;
    public static Texture farmFieldAdultChickensTexture;
    public static Texture farmFieldUninhabitedTexture;
    public static Texture farmBuildingTexture;

    // Button textures
    public static Texture inventoryTexture;

    public static void load() {
        backgroundTexture = new Texture("background.jpg");
        buttonTexture = new Texture("button.png");
        titleTexture = new Texture("title.png");
        gridSquareTexture = new Texture("gridSquare.png");
        invisibleTexture = new Texture("invisibleGridSquare.png");

        // Playing textures
        // FarmLandTexture
        farmLandUnplantedTexture = new Texture("farmLandUnplanted.png");
        // FarmFieldTextures
        farmFieldUninhabitedTexture = new Texture("farmFieldUninhabited.png");
        farmFieldAdultChickensTexture = new Texture("farmFieldAdultChickens.png");
        // Other
        farmBuildingTexture = new Texture("farmBuilding.png");

        // Button textures
        inventoryTexture = new Texture("inventory.png");
    }

    public static void dispose() {
        backgroundTexture.dispose();
        buttonTexture.dispose();
        titleTexture.dispose();
        gridSquareTexture.dispose();
        invisibleTexture.dispose();

        // Playing textures
        // FarmLandTexture
        farmLandUnplantedTexture.dispose();
        // FarmFieldTextures
        farmFieldUninhabitedTexture.dispose();
        farmFieldAdultChickensTexture.dispose();
        // Other
        farmBuildingTexture.dispose();

        // Button textures
        inventoryTexture.dispose();
    }
}
