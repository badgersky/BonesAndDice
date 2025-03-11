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
        moveToSelected();
    }

    public void unselectDice(int i) {
        dices.get(i).markUnselected();
        removeFromSelectd();
    }

    public void moveToSelected() {
        Iterator<Dice> it = dices.iterator();
        while (it.hasNext()) {
            if (it.next().selected) {
                selectedDices.add(it.next());
            }
        }
    }

    public void removeFromSelectd() {
        selectedDices.removeIf(dice -> !dice.selected);
    }
}
