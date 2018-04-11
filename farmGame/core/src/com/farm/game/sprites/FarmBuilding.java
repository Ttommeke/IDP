package com.farm.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.farm.game.Assets;

/**
 * This class represents the farm-building
 */
public class FarmBuilding extends FarmObject {
    public FarmBuilding(){
        super(2, Assets.farmBuildingTexture);
    }
}
