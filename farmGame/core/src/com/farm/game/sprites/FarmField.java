package com.farm.game.sprites;

import com.farm.game.Assets;

/**
 * The class representing a farmField(weide, For growing animals)
 */
public class FarmField extends FarmObject {
    // status & type will change over time (with the use of a Timer or initialPlantTime or something like that)
    private FarmFieldStatusEnum $status;
    private FarmFieldTypeEnum $type;

    public FarmField(){
        super( 2, Assets.farmFieldUninhabitedTexture);

        $status = FarmFieldStatusEnum.Uninhabited;
        $type = FarmFieldTypeEnum.Uninhabited;
    }

    public FarmField(FarmFieldStatusEnum status, FarmFieldTypeEnum type){
        super( 2, Assets.farmFieldUninhabitedTexture);

        $status = status;
        $type = type;

        changeTexture();
    }

    /**
     * Change the texture according to the status & type
     * (Change textures if created)
     */
    private void changeTexture() {
        switch ($status) {
            case Uninhabited:
                $texture = Assets.farmFieldUninhabitedTexture;
                break;
            case Children:
                switch ($type) {
                    case Chicken:
                        $texture = Assets.farmFieldUninhabitedTexture;
                        break;
                    case Pig:
                        $texture = Assets.farmFieldUninhabitedTexture;
                        break;
                    case Cow:
                        $texture = Assets.farmFieldUninhabitedTexture;
                        break;
                }
                break;
            case Adults:
                switch ($type) {
                    case Chicken:
                        $texture = Assets.farmFieldAdultChickensTexture;
                        break;
                    case Pig:
                        $texture = Assets.farmFieldUninhabitedTexture;
                        break;
                    case Cow:
                        $texture = Assets.farmFieldUninhabitedTexture;
                        break;
                }
                break;
        }
    }
}
