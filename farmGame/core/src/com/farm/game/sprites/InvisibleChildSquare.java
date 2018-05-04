package com.farm.game.sprites;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.farm.game.Assets;
import com.farm.game.FarmGameMain;
import com.farm.game.states.GameStateManager;
import com.farm.game.states.MenuState;

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
    public void handleTouch(GameStateManager gsm) {
        FarmGameMain.landscape.gridIndexesTouched($parentRow, $parentColumn, gsm);
    }

    @Override
    public void confirmDelete(GameStateManager gsm, int rowIndex, int columnIndex) {
        FarmGameMain.landscape.gridIndexesConfirmForDeletion($parentRow, $parentColumn, gsm);
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
