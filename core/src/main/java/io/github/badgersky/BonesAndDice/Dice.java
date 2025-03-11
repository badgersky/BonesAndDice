package io.github.badgersky.BonesAndDice;

import java.util.Random;

public class Dice {

    private int value;

    public Dice() {
        roll_dice();
    }

    private void roll_dice() {
        value = new Random().nextInt(6) + 1;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
