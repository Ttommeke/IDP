package com.farm.game.sprites;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.farm.game.Assets;
import com.farm.game.FarmGameMain;

public class InvisibleChildSquare extends FarmObject {
    private int $parentRow;
    private int $parentColumn;

    public InvisibleChildSquare() {
        super(1, Assets.invisibleTexture);
    }

    public InvisibleChildSquare(int parentRow, int parentColumn) {
        super(1, Assets.invisibleTexture);

        $parentRow = parentRow;
        $parentColumn = parentColumn;
    }

    @Override
    public void handleTouch() {
        FarmGameMain.landscape.gridIndexesTouched($parentRow, $parentColumn);
    }

    @Override
    public void write(Json json) {
        json.writeValue("parentRow", $parentRow);
        json.writeValue("parentColumn", $parentColumn);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        $parentRow = jsonData.getInt("parentRow");
        $parentColumn = jsonData.getInt("parentColumn");
    }
}
