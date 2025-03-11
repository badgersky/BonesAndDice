package io.github.badgersky.BonesAndDice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

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

    public boolean hasNoPoints() {
        ArrayList<Integer> diceValues = new ArrayList<>();

        for (Dice d : dices) {
            diceValues.add(d.getValue());
        }
        Collections.sort(diceValues);

        if (diceValues.contains(1) || diceValues.contains(5)) {
            return false;
        }

        for (int value : diceValues) {
            if (countOccurrences(value, diceValues) >= 3) {
                return false;
            }
        }

        if (has16(diceValues)) {
            return false;
        } else if (has15(diceValues)) {
            return false;
        } else if (has26(diceValues)) {
            return false;
        }

        return true;
    }

    public boolean has26(ArrayList<Integer> numbers) {
        boolean res = true;

        for (int i = 1; i <= 6; i++) {
            if (!numbers.contains(i)) {
                res = false;
                return res;
            }
        }

        return res;
    }

    public boolean has15(ArrayList<Integer> numbers) {
        boolean res = true;

        for (int i = 1; i <= 5; i++) {
            if (!numbers.contains(i)) {
                res = false;
                return res;
            }
        }

        return res;
    }

    public boolean has16(ArrayList<Integer> numbers) {
        Collections.sort(numbers);

        if (numbers.equals(Arrays.asList(1, 2, 3, 4, 5, 6))) {
            return true;
        }

        return false;
    }

    public int countOccurrences(int num, ArrayList<Integer> numbers) {
        int res = 0;
        for (int n : numbers) {
            if (num == n) {
                res++;
            }
        }

        return res;
    }

    public void rollNand() {
        for (Dice dice : dices) {
            dice.rollDice();
        }
    }

    public void selectDice(int i) {
        dices.get(i).markSelected();
    }

    public void unselectDice(int i) {
        dices.get(i).markUnselected();
    }
}
