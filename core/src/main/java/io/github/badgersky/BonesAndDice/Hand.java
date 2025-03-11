package io.github.badgersky.BonesAndDice;

import java.util.ArrayList;

public class Hand {

    public ArrayList<Dice> dices;
    public ArrayList<Dice> selectedDices;
    public Hand() {
        selectedDices = new ArrayList<>();
        dices = new ArrayList<>();
        Dice newDice;

        for (int i = 0; i < 6; i++) {
            newDice = new Dice();
            if (dices != null) {
                dices.add(newDice);
            }
        }
    }

    public void rollNand() {
        for (Dice dice : dices) {
            dice.rollDice();
        }
    }

    public void selectDice(int i) {
        dices.get(i).markSelected();
    }
}
