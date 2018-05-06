package com.farm.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
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

        coins = 1000;
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
        coinLabel.setFontScale(2);

        Table scrollTable = new Table();
        scrollTable.defaults().pad(10).width(128).height(128);

        scrollTable.add(coinsImage);
        scrollTable.add(coinLabel).center();
        scrollTable.row();

        // Grain
        Image grainImage = new Image(Assets.grainTexture);
        grainImage.setScaling(Scaling.fit);
        final Label grain = new Label(String.valueOf(amountOfGrain), skin);
        grain.setFontScale(2);
        Image buyGrainImage = new Image(Assets.buyTexture);
        buyGrainImage.setScaling(Scaling.fit);
        buyGrainImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buyGrain();
                grain.setText(String.valueOf(amountOfGrain));
                coinLabel.setText(String.valueOf(coins));
                FarmGameMain.settings.saveToJSON();
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });
        Image sellGrainImage = new Image(Assets.sellTexture);
        sellGrainImage.setScaling(Scaling.fit);
        sellGrainImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                sellGrain();
                grain.setText(String.valueOf(amountOfGrain));
                coinLabel.setText(String.valueOf(coins));
                FarmGameMain.settings.saveToJSON();
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });
        Image sellAllGrainImage = new Image(Assets.sellAllTexture);
        sellAllGrainImage.setScaling(Scaling.fit);
        sellAllGrainImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                int max = amountOfGrain;
                for(int i=0; i<= max; i++) {
                    sellGrain();
                }
                grain.setText(String.valueOf(amountOfGrain));
                coinLabel.setText(String.valueOf(coins));
                FarmGameMain.settings.saveToJSON();
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });

        scrollTable.add(grainImage);
        scrollTable.add(grain).center();
        scrollTable.add(buyGrainImage);
        scrollTable.add(sellGrainImage);
        scrollTable.add(sellAllGrainImage);
        scrollTable.row();

        // Carrot
        Image carrotImage = new Image(Assets.carrotTexture);
        carrotImage.setScaling(Scaling.fit);
        final Label carrot = new Label(String.valueOf(amountOfCarrot), skin);
        carrot.setFontScale(2);
        Image buyCarrotImage = new Image(Assets.buyTexture);
        buyCarrotImage.setScaling(Scaling.fit);
        buyCarrotImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buyCarrot();
                carrot.setText(String.valueOf(amountOfCarrot));
                coinLabel.setText(String.valueOf(coins));
                FarmGameMain.settings.saveToJSON();
                event.handle(); //the Stage will stop trying to handle this event
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });
        Image sellCarrotImage = new Image(Assets.sellTexture);
        sellCarrotImage.setScaling(Scaling.fit);
        sellCarrotImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                sellCarrot();
                carrot.setText(String.valueOf(amountOfCarrot));
                coinLabel.setText(String.valueOf(coins));
                FarmGameMain.settings.saveToJSON();
                event.handle(); //the Stage will stop trying to handle this event
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });
        Image sellAllCarrotImage = new Image(Assets.sellAllTexture);
        sellAllCarrotImage.setScaling(Scaling.fit);
        sellAllCarrotImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                int max = amountOfCarrot;
                for(int i=0; i<= max; i++) {
                    sellCarrot();
                }
                carrot.setText(String.valueOf(amountOfCarrot));
                coinLabel.setText(String.valueOf(coins));
                FarmGameMain.settings.saveToJSON();
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });

        scrollTable.add(carrotImage);
        scrollTable.add(carrot).center();
        scrollTable.add(buyCarrotImage);
        scrollTable.add(sellCarrotImage);
        scrollTable.add(sellAllCarrotImage);
        scrollTable.row();

        // Potato
        Image potatoImage = new Image(Assets.potatoTexture);
        potatoImage.setScaling(Scaling.fit);
        final Label potato = new Label(String.valueOf(amountOfPotato), skin);
        potato.setFontScale(2);
        Image buyPotatoImage = new Image(Assets.buyTexture);
        buyPotatoImage.setScaling(Scaling.fit);
        buyPotatoImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buyPotato();
                potato.setText(String.valueOf(amountOfPotato));
                coinLabel.setText(String.valueOf(coins));
                FarmGameMain.settings.saveToJSON();
                event.handle(); //the Stage will stop trying to handle this event
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });
        Image sellPotatoImage = new Image(Assets.sellTexture);
        sellPotatoImage.setScaling(Scaling.fit);
        sellPotatoImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                sellPotato();
                potato.setText(String.valueOf(amountOfPotato));
                coinLabel.setText(String.valueOf(coins));
                FarmGameMain.settings.saveToJSON();
                event.handle(); //the Stage will stop trying to handle this event
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });
        Image sellAllPotatoImage = new Image(Assets.sellAllTexture);
        sellAllPotatoImage.setScaling(Scaling.fit);
        sellAllPotatoImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                int max = amountOfPotato;
                for(int i=0; i<= max; i++) {
                    sellPotato();
                }
                potato.setText(String.valueOf(amountOfPotato));
                coinLabel.setText(String.valueOf(coins));
                FarmGameMain.settings.saveToJSON();
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });

        scrollTable.add(potatoImage);
        scrollTable.add(potato).center();
        scrollTable.add(buyPotatoImage);
        scrollTable.add(sellPotatoImage);
        scrollTable.add(sellAllPotatoImage);
        scrollTable.row();

        // Strawberry
        Image strawberryImage = new Image(Assets.strawberryTexture);
        strawberryImage.setScaling(Scaling.fit);
        final Label strawberry = new Label(String.valueOf(amountOfStrawberry), skin);
        strawberry.setFontScale(2);
        Image buyStrawberryImage = new Image(Assets.buyTexture);
        buyStrawberryImage.setScaling(Scaling.fit);
        buyStrawberryImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buyStrawberry();
                strawberry.setText(String.valueOf(amountOfStrawberry));
                coinLabel.setText(String.valueOf(coins));
                FarmGameMain.settings.saveToJSON();
                event.handle(); //the Stage will stop trying to handle this event
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });
        Image sellStrawberryImage = new Image(Assets.sellTexture);
        sellStrawberryImage.setScaling(Scaling.fit);
        sellStrawberryImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                sellStrawberry();
                strawberry.setText(String.valueOf(amountOfStrawberry));
                coinLabel.setText(String.valueOf(coins));
                FarmGameMain.settings.saveToJSON();
                event.handle(); //the Stage will stop trying to handle this event
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });
        Image sellAllStrawberryImage = new Image(Assets.sellAllTexture);
        sellAllStrawberryImage.setScaling(Scaling.fit);
        sellAllStrawberryImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                int max = amountOfStrawberry;
                for(int i=0; i<= max; i++) {
                    sellStrawberry();
                }
                strawberry.setText(String.valueOf(amountOfStrawberry));
                coinLabel.setText(String.valueOf(coins));
                FarmGameMain.settings.saveToJSON();
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });

        scrollTable.add(strawberryImage);
        scrollTable.add(strawberry).center();
        scrollTable.add(buyStrawberryImage);
        scrollTable.add(sellStrawberryImage);
        scrollTable.add(sellAllStrawberryImage);
        scrollTable.row();

        // Eggplant
        Image eggplantImage = new Image(Assets.eggplantTexture);
        eggplantImage.setScaling(Scaling.fit);
        final Label eggplant = new Label(String.valueOf(amountOfEggplant), skin);
        eggplant.setFontScale(2);
        Image buyEggplantImage = new Image(Assets.buyTexture);
        buyEggplantImage.setScaling(Scaling.fit);
        buyEggplantImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buyEggplant();
                eggplant.setText(String.valueOf(amountOfEggplant));
                coinLabel.setText(String.valueOf(coins));
                FarmGameMain.settings.saveToJSON();
                event.handle(); //the Stage will stop trying to handle this event
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });
        Image sellEggplantImage = new Image(Assets.sellTexture);
        sellEggplantImage.setScaling(Scaling.fit);
        sellEggplantImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                sellEggplant();
                eggplant.setText(String.valueOf(amountOfEggplant));
                coinLabel.setText(String.valueOf(coins));
                FarmGameMain.settings.saveToJSON();
                event.handle(); //the Stage will stop trying to handle this event
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });
        Image sellAllEggplantImage = new Image(Assets.sellAllTexture);
        sellAllEggplantImage.setScaling(Scaling.fit);
        sellAllEggplantImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                int max = amountOfEggplant;
                for(int i=0; i<= max; i++) {
                    sellEggplant();
                }
                eggplant.setText(String.valueOf(amountOfEggplant));
                coinLabel.setText(String.valueOf(coins));
                FarmGameMain.settings.saveToJSON();
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });

        scrollTable.add(eggplantImage);
        scrollTable.add(eggplant).center();
        scrollTable.add(buyEggplantImage);
        scrollTable.add(sellEggplantImage);
        scrollTable.add(sellAllEggplantImage);
        scrollTable.row();

        // Apple
        Image appleImage = new Image(Assets.appleTexture);
        appleImage.setScaling(Scaling.fit);
        final Label apple = new Label(String.valueOf(amountOfApples), skin);
        apple.setFontScale(2);
        /*Image buyAppleImage = new Image(Assets.buyTexture);
        buyAppleImage.setScaling(Scaling.fit);
        buyAppleImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buyApple();
                apple.setText(String.valueOf(amountOfApples));
                coinLabel.setText(String.valueOf(coins));
                FarmGameMain.settings.saveToJSON();
                event.handle(); //the Stage will stop trying to handle this event
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });*/
        Image sellAppleImage = new Image(Assets.sellTexture);
        sellAppleImage.setScaling(Scaling.fit);
        sellAppleImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                sellApple();
                apple.setText(String.valueOf(amountOfApples));
                coinLabel.setText(String.valueOf(coins));
                FarmGameMain.settings.saveToJSON();
                event.handle(); //the Stage will stop trying to handle this event
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });
        Image sellAllAppleImage = new Image(Assets.sellAllTexture);
        sellAllAppleImage.setScaling(Scaling.fit);
        sellAllAppleImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                int max = amountOfApples;
                for(int i=0; i<= max; i++) {
                    sellApple();
                }
                apple.setText(String.valueOf(amountOfApples));
                coinLabel.setText(String.valueOf(coins));
                FarmGameMain.settings.saveToJSON();
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });

        scrollTable.add(appleImage);
        scrollTable.add(apple).center();
        scrollTable.add(/*buyAppleImage*/);
        scrollTable.add(sellAppleImage);
        scrollTable.add(sellAllAppleImage);
        scrollTable.row();

        // Raspberry
        Image raspberryImage = new Image(Assets.raspberryTexture);
        raspberryImage.setScaling(Scaling.fit);
        final Label raspberry = new Label(String.valueOf(amountOfRaspberries), skin);
        raspberry.setFontScale(2);
        /*Image buyRaspberryImage = new Image(Assets.buyTexture);
        buyRaspberryImage.setScaling(Scaling.fit);
        buyRaspberryImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buyRaspberry();
                raspberry.setText(String.valueOf(amountOfRaspberries));
                coinLabel.setText(String.valueOf(coins));
                FarmGameMain.settings.saveToJSON();
                event.handle(); //the Stage will stop trying to handle this event
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });*/
        Image sellRaspberryImage = new Image(Assets.sellTexture);
        sellRaspberryImage.setScaling(Scaling.fit);
        sellRaspberryImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                sellRaspberry();
                raspberry.setText(String.valueOf(amountOfRaspberries));
                coinLabel.setText(String.valueOf(coins));
                FarmGameMain.settings.saveToJSON();
                event.handle(); //the Stage will stop trying to handle this event
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });
        Image sellAllRaspberryImage = new Image(Assets.sellAllTexture);
        sellAllRaspberryImage.setScaling(Scaling.fit);
        sellAllRaspberryImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                int max = amountOfRaspberries;
                for(int i=0; i<= max; i++) {
                    sellRaspberry();
                }
                raspberry.setText(String.valueOf(amountOfRaspberries));
                coinLabel.setText(String.valueOf(coins));
                FarmGameMain.settings.saveToJSON();
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });

        scrollTable.add(raspberryImage);
        scrollTable.add(raspberry).center();
        scrollTable.add(/*buyRaspberryImage*/);
        scrollTable.add(sellRaspberryImage);
        scrollTable.add(sellAllRaspberryImage);
        scrollTable.row();

        // Egg
        Image eggImage = new Image(Assets.eggTexture);
        eggImage.setScaling(Scaling.fit);
        final Label egg = new Label(String.valueOf(amountOfEggs), skin);
        egg.setFontScale(2);
        /*Image buyEggImage = new Image(Assets.buyTexture);
        buyEggImage.setScaling(Scaling.fit);
        buyEggImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buyEgg();
                egg.setText(String.valueOf(amountOfEggs));
                coinLabel.setText(String.valueOf(coins));
                FarmGameMain.settings.saveToJSON();
                event.handle(); //the Stage will stop trying to handle this event
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });*/
        Image sellEggImage = new Image(Assets.sellTexture);
        sellEggImage.setScaling(Scaling.fit);
        sellEggImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                sellEgg();
                egg.setText(String.valueOf(amountOfEggs));
                coinLabel.setText(String.valueOf(coins));
                FarmGameMain.settings.saveToJSON();
                event.handle(); //the Stage will stop trying to handle this event
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });
        Image sellAllEggImage = new Image(Assets.sellAllTexture);
        sellAllEggImage.setScaling(Scaling.fit);
        sellAllEggImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                int max = amountOfEggs;
                for(int i=0; i<= max; i++) {
                    sellEgg();
                }
                egg.setText(String.valueOf(amountOfEggs));
                coinLabel.setText(String.valueOf(coins));
                FarmGameMain.settings.saveToJSON();
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });

        scrollTable.add(eggImage);
        scrollTable.add(egg).center();
        scrollTable.add(/*buyEggImage*/);
        scrollTable.add(sellEggImage);
        scrollTable.add(sellAllEggImage);
        scrollTable.row();

        // Milk
        Image milkImage = new Image(Assets.milkTexture);
        milkImage.setScaling(Scaling.fit);
        final Label milk = new Label(String.valueOf(amountOfMilk), skin);
        milk.setFontScale(2);
        /*Image buyMilkImage = new Image(Assets.buyTexture);
        buyMilkImage.setScaling(Scaling.fit);
        buyMilkImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buyMilk();
                milk.setText(String.valueOf(amountOfMilk));
                coinLabel.setText(String.valueOf(coins));
                FarmGameMain.settings.saveToJSON();
                event.handle(); //the Stage will stop trying to handle this event
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });*/
        Image sellMilkImage = new Image(Assets.sellTexture);
        sellMilkImage.setScaling(Scaling.fit);
        sellMilkImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                sellMilk();
                milk.setText(String.valueOf(amountOfMilk));
                coinLabel.setText(String.valueOf(coins));
                FarmGameMain.settings.saveToJSON();
                event.handle(); //the Stage will stop trying to handle this event
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });
        Image sellAllMilkImage = new Image(Assets.sellAllTexture);
        sellAllMilkImage.setScaling(Scaling.fit);
        sellAllMilkImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                int max = amountOfMilk;
                for(int i=0; i<= max; i++) {
                    sellMilk();
                }
                milk.setText(String.valueOf(amountOfMilk));
                coinLabel.setText(String.valueOf(coins));
                FarmGameMain.settings.saveToJSON();
                return true; //the input multiplexer will stop trying to handle this touch
            }
        });

        scrollTable.add(milkImage);
        scrollTable.add(milk).center();
        scrollTable.add(/*buyMilkImage*/);
        scrollTable.add(sellMilkImage);
        scrollTable.add(sellAllMilkImage);
        scrollTable.row();

        // SuperItems
        Image superFertilizerImage = new Image(Assets.superFertilizerTexture);
        superFertilizerImage.setScaling(Scaling.fit);
        final Label superFertilizer = new Label(String.valueOf(amountOfFertilizer), skin);
        superFertilizer.setFontScale(2);
        Image superGrainImage = new Image(Assets.superGrainTexture);
        superGrainImage.setScaling(Scaling.fit);
        final Label superGrain = new Label(String.valueOf(amountOfSuperGrain), skin);
        superGrain.setFontScale(2);

        scrollTable.add(superFertilizerImage);
        scrollTable.add(superFertilizer).center();
        scrollTable.add();
        scrollTable.add(superGrainImage);
        scrollTable.add(superGrain).center();
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

    public boolean useGrain() {
        if(this.amountOfGrain >= 1) {
            this.amountOfGrain--;
            return true;
        } else {
            return false;
        }
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

    public boolean useCarrot() {
        if(this.amountOfCarrot >= 1) {
            this.amountOfCarrot--;
            return true;
        } else {
            return false;
        }
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

    public boolean usePotato() {
        if(this.amountOfCarrot >= 1) {
            this.amountOfCarrot--;
            return true;
        } else {
            return false;
        }
    }

    public void buyPotato() {
        if(this.coins >= 3) {
            this.amountOfPotato++;
            this.coins -= 3;
        }
    }

    public void sellPotato() {
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

    public boolean useStrawberry() {
        if(this.amountOfStrawberry >= 1) {
            this.amountOfStrawberry--;
            return true;
        } else {
            return false;
        }
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

    public boolean useEggplant() {
        if(this.amountOfEggplant >= 1) {
            this.amountOfEggplant--;
            return true;
        } else {
            return false;
        }
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

    public void addEggplant(int amount) {
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

    public void sellEgg() {
        if(this.amountOfEggs >= 1) {
            this.amountOfEggs --;
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
