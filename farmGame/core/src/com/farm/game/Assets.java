package com.farm.game;

import com.badlogic.gdx.graphics.Texture;

public class Assets {
    public static Texture backgroundTexture;
    public static Texture buttonTexture;
    public static Texture titleTexture;
    public static Texture plateTexture;

    //Icons
    public static Texture coinsTexture;
    public static Texture grainSeedsTexture;
    public static Texture grainSackTexture;
    public static Texture chickenTexture;

    // Grid textures
    public static Texture farmLandUnplantedTexture;
    public static Texture farmFieldAdultChickensTexture;
    public static Texture farmFieldUninhabitedTexture;
    public static Texture farmBuildingTexture;
    public static Texture gridSquareTexture;
    public static Texture invisibleTexture;

    // Button textures
    public static Texture inventoryTexture;

    public static void load() {
        backgroundTexture = new Texture("background.jpg");
        buttonTexture = new Texture("button.png");
        titleTexture = new Texture("title.png");
        plateTexture = new Texture("plate.png");

        // Icons
        coinsTexture = new Texture("coins.png");
        grainSeedsTexture = new Texture("grain.png");
        grainSackTexture = new Texture("grainSack.png");
        chickenTexture = new Texture("chicken.png");

        // Grid textures
        // FarmLandTexture
        farmLandUnplantedTexture = new Texture("farmLandUnplanted.png");
        // FarmFieldTextures
        farmFieldUninhabitedTexture = new Texture("farmFieldUninhabited.png");
        farmFieldAdultChickensTexture = new Texture("farmFieldAdultChickens.png");
        // Other
        farmBuildingTexture = new Texture("farmBuilding.png");
        gridSquareTexture = new Texture("gridSquare.png");
        invisibleTexture = new Texture("invisibleGridSquare.png");

        // Button textures
        inventoryTexture = new Texture("inventory.png");
    }

    public static void dispose() {
        backgroundTexture.dispose();
        buttonTexture.dispose();
        titleTexture.dispose();
        plateTexture.dispose();

        // Icons
        coinsTexture.dispose();
        grainSeedsTexture.dispose();
        grainSackTexture.dispose();
        chickenTexture.dispose();

        // Grid textures
        // FarmLandTexture
        farmLandUnplantedTexture.dispose();
        // FarmFieldTextures
        farmFieldUninhabitedTexture.dispose();
        farmFieldAdultChickensTexture.dispose();
        // Other
        farmBuildingTexture.dispose();
        gridSquareTexture.dispose();
        invisibleTexture.dispose();

        // Button textures
        inventoryTexture.dispose();
    }
}
