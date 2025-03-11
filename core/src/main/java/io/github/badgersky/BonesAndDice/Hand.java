package io.github.badgersky.BonesAndDice;

import java.util.ArrayList;
import java.util.Iterator;

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

    public void moveToSelected() {
        Iterator<Dice> it = dices.iterator();
        if (goodToMove()) {
            while (it.hasNext()) {
                if (it.next().selected) {
                    selectedDices.add(it.next());
                    it.remove();
                }
            }
        }
    }
}
