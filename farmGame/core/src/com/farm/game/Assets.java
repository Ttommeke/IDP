package com.farm.game;

import com.badlogic.gdx.graphics.Texture;

public class Assets {
    public static Texture backgroundTexture;
    public static Texture buttonTexture;
    public static Texture titleTexture;
    public static Texture plateTexture;

    //Icons
    public static Texture coinsTexture;
    public static Texture grainTexture;
    public static Texture carrotTexture;
    public static Texture potatoTexture;
    public static Texture strawberryTexture;
    public static Texture eggplantTexture;
    public static Texture chickenTexture;
    public static Texture chickTexture;
    public static Texture cowTexture;
    public static Texture calfTexture;
    public static Texture pigTexture;
    public static Texture pigletTexture;
    public static Texture appleTexture;
    public static Texture raspberryTexture;
    public static Texture eggTexture;
    public static Texture milkTexture;
    public static Texture superFertilizerTexture;
    public static Texture superGrainTexture;
    public static Texture marketTexture;
    public static Texture timerTexture;
    public static Texture buyTexture;
    public static Texture sellTexture;
    public static Texture buildTexture;
    public static Texture feedTexture;
    public static Texture removeTexture;
    public static Texture acceptTexture;

    // Grid textures
    // Farmland textures
    public static Texture farmLandUnplantedTexture;
    public static Texture farmLandCarrotGrowingTexture;
    public static Texture farmLandCarrotReadyTexture;
    public static Texture farmLandCarrotRottenTexture;
    public static Texture farmLandEggplantGrowingTexture;
    public static Texture farmLandEggplantReadyTexture;
    public static Texture farmLandEggplantRottenTexture;
    public static Texture farmLandGrainGrowingTexture;
    public static Texture farmLandGrainReadyTexture;
    public static Texture farmLandGrainRottenTexture;
    public static Texture farmLandPotatoGrowingTexture;
    public static Texture farmLandPotatoReadyTexture;
    public static Texture farmLandPotatoRottenTexture;
    public static Texture farmLandStrawberryGrowingTexture;
    public static Texture farmLandStrawberryReadyTexture;
    public static Texture farmLandStrawberryRottenTexture;
    // FarmField textures
    public static Texture farmFieldUninhabitedTexture;
    public static Texture farmFieldCalfTexture;
    public static Texture farmFieldCowTexture;
    public static Texture farmFieldCowAndCalfTexture;
    public static Texture farmFieldChickTexture;
    public static Texture farmFieldChickenTexture;
    public static Texture farmFieldChickenAndChickTexture;
    public static Texture farmFieldPigletTexture;
    public static Texture farmFieldPigTexture;
    public static Texture farmFieldPigAndPigletTexture;
    // FarmTree textures
    public static Texture farmTreeGrowing;
    public static Texture farmTreeGrown;
    public static Texture farmTreeApplesReady;
    public static Texture farmBushGrowing;
    public static Texture farmBushGrown;
    public static Texture farmBushRaspberriesReady;
    // other textures
    public static Texture farmBuildingTexture;
    public static Texture gridSquareTexture;
    public static Texture invisibleTexture;

    // Button textures
    public static Texture inventoryTexture;
    public static Texture mapTexture;
    public static Texture moveTexture;
    public static Texture settingsTexture;
    public static Texture cancelTexture;

    public static void load() {
        backgroundTexture = new Texture("background.jpg");
        buttonTexture = new Texture("button.png");
        titleTexture = new Texture("title.png");
        plateTexture = new Texture("plate.png");

        // Icons
        coinsTexture = new Texture("coins.png");
        grainTexture = new Texture("icons/icons8-wheat-100.png");
        carrotTexture = new Texture("icons/icons8-carrot-100.png");
        potatoTexture = new Texture("icons/icons8-potato-100.png");
        strawberryTexture = new Texture("icons/icons8-strawberry-100.png");
        eggplantTexture = new Texture("icons/icons8-eggplant-100.png");
        chickenTexture = new Texture("icons/icons8-chicken-100.png");
        chickTexture = new Texture("icons/chick.png");
        cowTexture = new Texture("icons/icons8-cow-100.png");
        calfTexture = new Texture("icons/calf.png");
        pigTexture = new Texture("icons/pig.png");
        pigletTexture = new Texture("icons/piglet.png");
        appleTexture = new Texture("icons/icons8-apple-100.png");
        raspberryTexture = new Texture("icons/icons8-raspberry-100.png");
        eggTexture = new Texture("icons/icons8-eggs-filled-100.png");
        milkTexture = new Texture("icons/icons8-milk-bottle-100.png");
        superFertilizerTexture = new Texture("icons/superfertilizer.png");
        superGrainTexture = new Texture("icons/supergrain.png");
        marketTexture = new Texture("icons/icons8-market-100.png");
        timerTexture = new Texture("icons/icons8-time-100.png");
        buyTexture = new Texture("icons/buy.png");
        sellTexture = new Texture("icons/sell.png");
        buildTexture = new Texture("icons/hammer.png");
        feedTexture = new Texture("icons/animal-feed.png");
        removeTexture = new Texture("icons/rubbish.png");
        acceptTexture = new Texture("icons/check.png");

        // Grid textures
        // Farmland textures
        farmLandUnplantedTexture = new Texture("farms/land");
        farmLandCarrotGrowingTexture = new Texture("farms/land-carrot-growing");
        farmLandCarrotReadyTexture = new Texture("farms/land-carrot-ready.png");
        farmLandCarrotRottenTexture = new Texture("farms/land-carrot-rotten.png");
        farmLandEggplantGrowingTexture = new Texture("farms/land-eggplant-growing");
        farmLandEggplantReadyTexture = new Texture("farms/land-eggplant-ready");
        farmLandEggplantRottenTexture = new Texture("farms/land-eggplant-rotten");
        farmLandGrainGrowingTexture = new Texture("farms/land-grain-growing.png");
        farmLandGrainReadyTexture = new Texture("farms/land-grain-ready");
        farmLandGrainRottenTexture = new Texture("farms/land-grain-rotten");
        farmLandPotatoGrowingTexture = new Texture("farms/land-potato-growing");
        farmLandPotatoReadyTexture = new Texture("farms/land-potato-ready");
        farmLandPotatoRottenTexture = new Texture("farms/land-potato-rotten");
        farmLandStrawberryGrowingTexture = new Texture("farms/land-strawberry-growing");
        farmLandStrawberryReadyTexture = new Texture("farms/land-strawberry-ready");
        farmLandStrawberryRottenTexture = new Texture("farms/land-strawberry-rotten");
        // FarmFieldTextures
        farmFieldUninhabitedTexture = new Texture("fields/field");
        farmFieldCalfTexture = new Texture("fields/field-calf");
        farmFieldCowTexture = new Texture("fields/field-cow");
        farmFieldCowAndCalfTexture = new Texture("fields/field-cow-and-calf");
        farmFieldChickTexture = new Texture("fields/field-chicks");
        farmFieldChickenTexture = new Texture("fields/field-chicken");
        farmFieldChickenAndChickTexture = new Texture("fields/field-chicken-and-chicks");
        farmFieldPigletTexture = new Texture("fields/field-piglet");
        farmFieldPigTexture = new Texture("fields/field-pig");
        farmFieldPigAndPigletTexture = new Texture("fields/field-pig-and-piglet");
        // FarmTree textures
        farmTreeGrowing = new Texture("trees/appletree-growing");
        farmTreeGrown = new Texture("trees/appletree-grown");
        farmTreeApplesReady = new Texture("trees/appletree-ready");
        farmBushGrowing = new Texture("bushes/raspberrybush-growing");
        farmBushGrown = new Texture("bushes/raspberrybush-grown");
        farmBushRaspberriesReady = new Texture("bushes/raspberrybush-ready");
        // Other
        farmBuildingTexture = new Texture("farmBuilding.png");
        gridSquareTexture = new Texture("gridSquare.png");
        invisibleTexture = new Texture("invisibleGridSquare.png");

        // Button textures
        inventoryTexture = new Texture("inventory.png");
        mapTexture = new Texture("icons/icons8-world-map-100.png");
        moveTexture = new Texture("icons/icons8-move-100.png");
        settingsTexture = new Texture("icons/icons8-settings-500.png");
        cancelTexture = new Texture("icons/icons8-cancel-500.png");
    }

    public static void dispose() {
        backgroundTexture.dispose();
        buttonTexture.dispose();
        titleTexture.dispose();
        plateTexture.dispose();

        // Icons
        coinsTexture.dispose();
        grainTexture.dispose();
        carrotTexture.dispose();
        potatoTexture.dispose();
        strawberryTexture.dispose();
        eggplantTexture.dispose();
        chickenTexture.dispose();
        chickTexture.dispose();
        cowTexture.dispose();
        calfTexture.dispose();
        pigTexture.dispose();
        pigletTexture.dispose();
        appleTexture.dispose();
        raspberryTexture.dispose();
        eggTexture.dispose();
        milkTexture.dispose();
        superFertilizerTexture.dispose();
        superGrainTexture.dispose();
        marketTexture.dispose();
        timerTexture.dispose();
        buyTexture.dispose();
        sellTexture.dispose();
        buildTexture.dispose();
        feedTexture.dispose();
        removeTexture.dispose();
        acceptTexture.dispose();

        // Grid textures
        // Farmland textures
        farmLandUnplantedTexture.dispose();
        farmLandCarrotGrowingTexture.dispose();
        farmLandCarrotReadyTexture.dispose();
        farmLandCarrotRottenTexture.dispose();
        farmLandEggplantGrowingTexture.dispose();
        farmLandEggplantReadyTexture.dispose();
        farmLandEggplantRottenTexture.dispose();
        farmLandGrainGrowingTexture.dispose();
        farmLandGrainReadyTexture.dispose();
        farmLandGrainRottenTexture.dispose();
        farmLandPotatoGrowingTexture.dispose();
        farmLandPotatoReadyTexture.dispose();
        farmLandPotatoRottenTexture.dispose();
        farmLandStrawberryGrowingTexture.dispose();
        farmLandStrawberryReadyTexture.dispose();
        farmLandStrawberryRottenTexture.dispose();
        // FarmFieldTextures
        farmFieldUninhabitedTexture.dispose();
        farmFieldCalfTexture.dispose();
        farmFieldCowTexture.dispose();
        farmFieldCowAndCalfTexture.dispose();
        farmFieldChickTexture.dispose();
        farmFieldChickenTexture.dispose();
        farmFieldChickenAndChickTexture.dispose();
        farmFieldPigletTexture.dispose();
        farmFieldPigTexture.dispose();
        farmFieldPigAndPigletTexture.dispose();
        // FarmTree textures
        farmTreeGrowing.dispose();
        farmTreeGrown.dispose();
        farmTreeApplesReady.dispose();
        farmBushGrowing.dispose();
        farmBushGrown.dispose();
        farmBushRaspberriesReady.dispose();
        // Other
        farmBuildingTexture.dispose();
        gridSquareTexture.dispose();
        invisibleTexture.dispose();

        // Button textures
        inventoryTexture.dispose();
        mapTexture.dispose();
        moveTexture.dispose();
        settingsTexture.dispose();
        cancelTexture.dispose();
    }
}
