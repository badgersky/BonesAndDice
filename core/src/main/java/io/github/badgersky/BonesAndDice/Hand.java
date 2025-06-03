package io.github.badgersky.BonesAndDice;

import java.util.*;

public class Hand {

    public ArrayList<Dice> dices;
    public ArrayList<Dice> putAwayDices;
    public Hand() {
        putAwayDices = new ArrayList<>();
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

        for (int i = 2; i <= 6; i++) {
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

    public void rollHand() {
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

    public void putAwaySelectedDices() {
        Iterator<Dice> it = dices.iterator();
        while (it.hasNext()) {
            Dice d = it.next();
            if (d.selected) {
                d.markUnselected();
                putAwayDices.add(d);
                it.remove();
            }
        }
    }

    public void returnPutAwayDices() {
        Iterator<Dice> it = putAwayDices.iterator();
        while (it.hasNext()) {
            Dice d = it.next();
            dices.add(d);
            it.remove();
        }
    }

    public int countPoints() {
        ArrayList<Integer> selectedPoints = new ArrayList<>();
        int points = 0;
        int occurrences;

        for (Dice d : dices) {
            if (d.selected) {
                selectedPoints.add(d.getValue());
            }
        }

        if (has16(selectedPoints)) {
            points = 1500;
            return points;
        }
        if (has15(selectedPoints)) {
            points = 500;
            if (countOccurrences(1, selectedPoints) == 2) {
                points += 100;
            }
            if (countOccurrences(5, selectedPoints) == 2) {
                points += 50;
            }
            return points;
        }
        if (has26(selectedPoints)) {
            points = 750;
            if (countOccurrences(5, selectedPoints) == 2) {
                points += 50;
            }
            return points;
        }

        for (int i = 1; i <= 6; i++) {
            occurrences = countOccurrences(i, selectedPoints);
            if (occurrences > 0) {
                if (i == 1) {
                    if (occurrences >= 3) {
                        points += 1000 * (occurrences - 2);
                    } else {
                        points += 100 * occurrences;
                    }
                } else if (i == 5) {
                    if (occurrences >= 3) {
                        points += 500 * (occurrences - 2);
                    } else {
                        points += 50 * occurrences;
                    }
                } else {
                    if (occurrences >= 3) {
                        points += (i * 100) * (occurrences - 2);
                    } else {
                        points = 0;
                        return points;
                    }
                }
            }
        }

        return points;
    }
}
