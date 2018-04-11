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
    public static Texture unplantedFieldTexture;
    public static Texture farmBuildingTexture;

    // Button textures
    public static Texture inventoryTexture;

    public static void load() {
        backgroundTexture = new Texture("background.jpg");
        buttonTexture = new Texture("button.png");
        titleTexture = new Texture("title.png");
        gridSquareTexture = new Texture("gridSquare.png");
        invisibleTexture = new Texture("invisibleSquare.png");

        // Playing textures
        unplantedFieldTexture = new Texture("fieldUnplanted.png");
        farmBuildingTexture = new Texture("farm-topview.png");

        // Button textures
        inventoryTexture = new Texture("warehouse.png");
    }

    public static void dispose() {
        backgroundTexture.dispose();
        buttonTexture.dispose();
        titleTexture.dispose();
        gridSquareTexture.dispose();
        invisibleTexture.dispose();

        // Playing textures
        unplantedFieldTexture.dispose();
        farmBuildingTexture.dispose();

        // Button textures
        inventoryTexture.dispose();
    }
}
