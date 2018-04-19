package com.farm.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Scaling;

/**
 * This class contains the inventory of the player.
 */
public class Inventory {
    private int coins;

    private int amountOfGrain;
    private int amountOfCarrot;
    private int amountOfPotato;
    private int amountOfStrawberry;
    private int amountOfEggplant;

    private int amountOfMilk;
    private int amountOfEggs;

    private int amountOfApples;
    private int amountOfRaspberries;

    private int amountOfFertilizer;
    private int amountOfSuperGrain;

    public void defaultInventory() {
        System.out.println("defaultInventory");

        coins = 10;
        amountOfGrain = 3;

        saveInventoryOnlyToJSON();
    }

    private void saveInventoryOnlyToJSON() {
        try {
            Preferences prefs = Gdx.app.getPreferences("My Preferences");
            Json json = new Json();

            String jsonInventoryString = json.prettyPrint(this);
            prefs.putString("inventory", jsonInventoryString );

            prefs.flush();
        } catch (Throwable e) {
            Gdx.app.log("error saving file", e.getMessage());
        }
    }

    public Table getScrollTable() {
        Skin skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));

        Image coinsImage = new Image(Assets.coinsTexture);
        coinsImage.setScaling(Scaling.fit);
        final Label coinLabel = new Label(String.valueOf(coins), skin);
        coinLabel.setFontScale(5);

        Table scrollTable = new Table();
        scrollTable.defaults().pad(10).width(128).height(128);

        scrollTable.add(coinsImage);
        scrollTable.add(coinLabel).center();
        scrollTable.row();

        // Grain
        Image GrainImage = new Image(Assets.grainTexture);
        GrainImage.setScaling(Scaling.fit);
        final Label Grain = new Label(String.valueOf(amountOfGrain), skin);
        Grain.setFontScale(5);
        Image buyGrainImage = new Image(Assets.buyTexture);
        buyGrainImage.setScaling(Scaling.fit);
        buyGrainImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buyGrain();
                Grain.setText(String.valueOf(amountOfGrain));
                coinLabel.setText(String.valueOf(coins));
                FarmGameMain.settings.saveToJSON();
                event.handle();//the Stage will stop trying to handle this event
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });
        Image sellGrainImage = new Image(Assets.sellTexture);
        sellGrainImage.setScaling(Scaling.fit);
        sellGrainImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                sellGrain();
                Grain.setText(String.valueOf(amountOfGrain));
                coinLabel.setText(String.valueOf(coins));
                FarmGameMain.settings.saveToJSON();
                event.handle();//the Stage will stop trying to handle this event
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });

        scrollTable.add(GrainImage);
        scrollTable.add(Grain).center();
        scrollTable.add(buyGrainImage);
        scrollTable.add(sellGrainImage);
        scrollTable.row();

        return scrollTable;
    }

    public int getCoins() {
        return coins;
    }

    public void earnSomething(int coinAmount) {
        this.coins += coinAmount;
    }

    public void buySomething(int coinAmount) {
        this.coins -= coinAmount;
    }

    public int getAmountOfGrain() {
        return amountOfGrain;
    }

    public void buyGrain() {
        if(this.coins >= 3) {
            this.amountOfGrain++;
            this.coins -= 3;
        }
    }

    public void sellGrain() {
        if(this.amountOfGrain >= 1) {
            this.amountOfGrain--;
            this.coins += 2;
        }
    }

    public void addGrain(int amount) {
        this.amountOfGrain += amount;
    }

    public int getAmountOfCarrot() {
        return amountOfCarrot;
    }

    public void buyCarrot() {
        if(this.coins >= 2) {
            this.amountOfCarrot++;
            this.coins -= 2;
        }
    }

    public void sellCarrot() {
        if(this.amountOfCarrot >= 1) {
            this.amountOfCarrot--;
            this.coins += 1;
        }
    }

    public void addCarrot(int amount) {
        this.amountOfCarrot += amount;
    }

    public int getAmountOfPotato() {
        return amountOfPotato;
    }

    public void buyPatato() {
        if(this.coins >= 3) {
            this.amountOfPotato++;
            this.coins -= 3;
        }
    }

    public void sellPatato() {
        if(this.amountOfPotato >= 1) {
            this.amountOfPotato--;
            this.coins += 2;
        }
    }

    public void addPotato(int amount) {
        this.amountOfPotato += amount;
    }

    public int getAmountOfStrawberry() {
        return amountOfStrawberry;
    }

    public void buyStrawberry() {
        if(this.coins >= 20) {
            this.amountOfStrawberry++;
            this.coins -= 20;
        }
    }

    public void sellStrawberry() {
        if(this.amountOfStrawberry >= 1) {
            this.amountOfStrawberry--;
            this.coins += 13;
        }
    }

    public void addStrawberry(int amount) {
        this.amountOfStrawberry += amount;
    }

    public int getAmountOfEggplant() {
        return amountOfEggplant;
    }

    public void buyEggplant() {
        if(this.coins >= 24) {
            this.amountOfEggplant++;
            this.coins -= 24;
        }
    }

    public void sellEggplant() {
        if(this.amountOfEggplant >= 1) {
            this.amountOfEggplant--;
            this.coins += 16;
        }
    }

    public void addAmountOfEggplant(int amount) {
        this.amountOfEggplant += amount;
    }

    public int getAmountOfMilk() {
        return amountOfMilk;
    }

    public void addMilk(int amount) {
        this.amountOfMilk += amount;
    }

    public void buyMilk() {
        if(this.coins >= 9) {
            this.amountOfMilk++;
            this.coins -= 9;
        }
    }

    public void sellMilk() {
        if(this.amountOfMilk >= 1) {
            this.amountOfMilk--;
            this.coins += 6;
        }
    }

    public int getAmountOfEggs() {
        return amountOfEggs;
    }

    public void addEgg(int amount) {
        this.amountOfEggs += amount;
    }

    public void buyEgg() {
        if(this.coins >= 8) {
            this.amountOfEggs++;
            this.coins -= 8;
        }
    }

    public void sellEggs(int amount) {
        if(this.amountOfEggs >= 1) {
            this.amountOfEggs -= amount;
            this.coins += 5;
        }
    }

    public int getAmountOfApples() {
        return amountOfApples;
    }

    public void addApples(int amount) {
        this.amountOfApples += amount;
    }

    public void buyApple() {
        if(this.coins >= 5) {
            this.amountOfApples++;
            this.coins -= 5;
        }
    }

    public void sellApple() {
        if(this.amountOfApples >= 1) {
            this.amountOfApples --;
            this.coins += 3;
        }
    }

    public int getAmountOfRaspberries() {
        return amountOfRaspberries;
    }

    public void addRaspberries(int amount) {
        this.amountOfRaspberries += amount;
    }

    public void buyRaspberry() {
        if(this.coins >= 14) {
            this.amountOfRaspberries++;
            this.coins -= 14;
        }
    }

    public void sellRaspberry() {
        if(this.amountOfRaspberries >= 1) {
            this.amountOfRaspberries--;
            this.coins += 9;
        }
    }

    public int getAmountOfFertilizer() {
        return amountOfFertilizer;
    }

    public void findFertilizer() {
        this.amountOfFertilizer++;
    }

    public boolean useFertilizer() {
        this.amountOfFertilizer--;
        if(this.amountOfFertilizer >= 0) {
            return true;
        } else {
            this.amountOfFertilizer = 0;
            return false;
        }
    }

    public int getAmountOfSuperGrain() {
        return amountOfSuperGrain;
    }

    public void findSuperGrain() {
        this.amountOfSuperGrain++;
    }

    public boolean useSuperGrain() {
        this.amountOfSuperGrain--;
        if(this.amountOfSuperGrain >= 0) {
            return true;
        } else {
            this.amountOfSuperGrain = 0;
            return false;
        }
    }
}
