package com.farm.game.DataClasses;

public class Inventory {
    private int coins;

    private int amountOfGrainSeeds;
    private int amountOfGrainSacks;
    private int amountOfCarrotSeeds;
    private int amountOfCarrotSacks;
    private int amountOfPotatoSeeds;
    private int amountOfPotatoSacks;
    private int amountOfStrawberrySeeds;
    private int amountOfStrawberrySacks;
    private int amountOfEggplantSeeds;
    private int amountOfEggplantSacks;

    private int amountOfCalves;
    private int amountOfCows;
    private int amountOfMilk;
    private int amountOfPiglets;
    private int amountOfPigs;
    private int amountOfChicks;
    private int amountOfChickens;
    private int amountOfEggs;

    private int amountOfAppleSeeds;
    private int amountOfApples;
    private int amountOfBlueberrySeeds;
    private int amountOfBlueberries;

    private int amountOfFertilizer;
    private int amountOfSuperGrain;

    public int getCoins() {
        return coins;
    }

    public void earnSomething(int coinAmount) {
        this.coins += coinAmount;
    }

    public void buySomething(int coinAmount) {
        this.coins -= coinAmount;
    }

    public int getAmountOfGrainSeeds() {
        return amountOfGrainSeeds;
    }

    public void useGrainSeed() {
        this.amountOfGrainSeeds--;
    }

    public void addGrainSeeds(int amount) {
        this.amountOfGrainSeeds += amount;
    }

    public int getAmountOfGrainSacks() {
        return amountOfGrainSacks;
    }

    public void useGrainSack() {
        this.amountOfGrainSacks--;
    }

    public void sellGrainSacks(int amount) {
        this.amountOfGrainSacks -= amount;
    }

    public int getAmountOfCarrotSeeds() {
        return amountOfCarrotSeeds;
    }

    public void useCarrotSeed() {
        this.amountOfCarrotSeeds--;
    }

    public void addCarrotSeeds(int amount) {
        this.amountOfCarrotSeeds += amount;
    }

    public int getAmountOfCarrotSacks() {
        return amountOfCarrotSacks;
    }

    public void useCarrotSack() {
        this.amountOfCarrotSeeds--;
    }

    public void sellCarrotSacks(int amount) {
        this.amountOfCarrotSacks -= amount;
    }

    public int getAmountOfPotatoSeeds() {
        return amountOfPotatoSeeds;
    }

    public void usePatatoSeed() {
        this.amountOfPotatoSeeds--;
    }

    public void addPotatoSeeds(int amount) {
        this.amountOfPotatoSeeds += amount;
    }

    public int getAmountOfPotatoSacks() {
        return amountOfPotatoSacks;
    }

    public void usePatatoSack() {
        this.amountOfPotatoSacks--;
    }

    public void sellPotatoSacks(int amount) {
        this.amountOfPotatoSacks -= amount;
    }

    public int getAmountOfStrawberrySeeds() {
        return amountOfStrawberrySeeds;
    }

    public void useStrawberrySeed() {
        this.amountOfStrawberrySeeds--;
    }

    public void addStrawberrySeeds(int amount) {
        this.amountOfStrawberrySeeds += amount;
    }

    public int getAmountOfStrawberrySacks() {
        return amountOfStrawberrySacks;
    }

    public void useStrawberrySack() {
        this.amountOfStrawberrySacks--;
    }

    public void sellStrawberrySacks(int amount) {
        this.amountOfStrawberrySacks -= amount;
    }

    public int getAmountOfEggplantSeeds() {
        return amountOfEggplantSeeds;
    }

    public void useEggplantSeed() {
        this.amountOfEggplantSeeds--;
    }

    public void addAmountOfEggplantSeeds(int amount) {
        this.amountOfEggplantSeeds += amount;
    }

    public int getAmountOfEggplantSacks() {
        return amountOfEggplantSacks;
    }

    public void useEggplantSack() {
        this.amountOfEggplantSacks--;
    }

    public void sellAmountOfEggplantSacks(int amount) {
        this.amountOfEggplantSacks -= amount;
    }

    public int getAmountOfCalves() {
        return amountOfCalves;
    }

    public void buyCalves(int amount) {
        this.amountOfCalves += amount;
    }

    public void growCalveUp() {
        this.amountOfCalves--;
        this.amountOfCows++;
    }

    public int getAmountOfCows() {
        return amountOfCows;
    }

    public int getAmountOfMilk() {
        return amountOfMilk;
    }

    public void collectMilk() {
        this.amountOfMilk++;
    }

    public void sellMilk(int amount) {
        this.amountOfMilk -= amount;
    }

    public int getAmountOfPiglets() {
        return amountOfPiglets;
    }

    public void buyPiglets(int amount) {
        this.amountOfPiglets += amount;
    }

    public void growPigletUp() {
        this.amountOfPiglets--;
        this.amountOfPigs++;
    }

    public int getAmountOfPigs() {
        return amountOfPigs;
    }

    public int getAmountOfChicks() {
        return amountOfChicks;
    }

    public void buyChicks(int amount) {
        this.amountOfChicks += amount;
    }

    public void growChickUp() {
        this.amountOfChicks--;
        this.amountOfChickens++;
    }

    public int getAmountOfChickens() {
        return amountOfChickens;
    }

    public int getAmountOfEggs() {
        return amountOfEggs;
    }

    public void collectEgg() {
        this.amountOfEggs++;
    }

    public void sellEggs(int amount) {
        this.amountOfEggs -= amount;
    }

    public int getAmountOfAppleSeeds() {
        return amountOfAppleSeeds;
    }

    public void buyAppleSeeds(int amount) {
        this.amountOfAppleSeeds += amount;
    }

    public void useAppleSeed() {
        this.amountOfAppleSeeds--;
    }

    public int getAmountOfApples() {
        return amountOfApples;
    }

    public void sellApples(int amount) {
        this.amountOfApples -= amountOfApples;
    }

    public int getAmountOfBlueberrySeeds() {
        return amountOfBlueberrySeeds;
    }

    public void sellBlueberrySeeds(int amount) {
        this.amountOfBlueberrySeeds -= amount;
    }

    public void useBlueberrySeed() {
        this.amountOfBlueberrySeeds--;
    }

    public int getAmountOfBlueberries() {
        return amountOfBlueberries;
    }

    public void sellBlueberries(int amount) {
        this.amountOfBlueberries -= amount;
    }

    public int getAmountOfFertilizer() {
        return amountOfFertilizer;
    }

    public void findABagOfFertilizer() {
        this.amountOfFertilizer++;
    }

    public int getAmountOfSuperGrain() {
        return amountOfSuperGrain;
    }

    public void findABagOfSuperGrain() {
        this.amountOfSuperGrain++;
    }
}
