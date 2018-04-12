package com.farm.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import javax.xml.soap.Text;

public class Assets {
    public static Texture backgroundTexture;
    public static Texture buttonTexture;
    public static Texture titleTexture;
    public static Texture gridSquareTexture;
    public static Texture invisibleTexture;

    // Playing textures
    public static Texture farmFieldUnplantedTexture;
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
        farmFieldUnplantedTexture = new Texture("farmFieldUnplanted.png");
        farmFieldAdultChickensTexture = new Texture("farmFieldAdultChickens.png");
        // FarmFieldTextures
        farmFieldUninhabitedTexture = new Texture("farmFieldUninhabited.png");
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
        farmFieldUnplantedTexture.dispose();
        farmFieldAdultChickensTexture.dispose();
        farmFieldUninhabitedTexture.dispose();
        farmBuildingTexture.dispose();

        // Button textures
        inventoryTexture.dispose();
    }
}
