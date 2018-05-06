package com.farm.game;

import java.util.Random;

public enum ResourceType {
    GRAIN(0, "icons8_wheat_100"),
    CARROT(1,"icons8_carrot_100"),
    POTATO(2, "icons8_potato_100"),
    STRAWBERRY(3, "icons8_strawberry_100"),
    EGGPLANT(4, "icons8_eggplant_100"),
    APPLE(5, "icons8_apple_100"),
    RASPBERRY(6, "icons8_raspberry_100"),
    FERTILIZER(7, "superfertilizer"),
    SUPERGRAIN(8, "supergrain"),
    MILK(9, "icons8_milk_bottle_100"),
    EGG(10, "icons8_eggs_filled_100");

    private final int intValue;
    private final String icon;
    private ResourceType(final int intValue, final String icon) {
        this.intValue = intValue;
        this.icon = icon;
    }
    public static ResourceType getRandomResourceType(){
        Random random = new Random();
        int randInt = random.nextInt(ResourceType.values().length);
        return values()[randInt];
    }

    public String getIcon(){
        return this.icon;
    }

    public static ResourceType getType(int number){
        return values()[number];
    }

    public int getValue(){
        return this.intValue;
    }
}
