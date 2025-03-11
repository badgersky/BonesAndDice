package io.github.badgersky.BonesAndDice;

import java.util.Random;

public class Dice {

    private int value;
    public boolean selected;

    public Dice() {
        selected = false;
        rollDice();
    }

    public void rollDice() {
        value = new Random().nextInt(6) + 1;
    }

    public void markSelected() {
        selected = true;
    }

    public void markUnselected() {
        selected = false;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
