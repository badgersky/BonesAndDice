package io.github.badgersky.BonesAndDice;

import java.util.ArrayList;
import java.util.List;

public class PcPlayer {

    Hand hand;

    public PcPlayer(Hand hand) {
        this.hand = hand;
    }

    public int play() {
        List<Dice> bestChoice;

        if (hand.hasNoPoints()) {
            return -1;
        }

        bestChoice = chooseBestCombo();

        return 1;
    }

    public List<List<Dice>> generateSubsets() {
        List<List<Dice>> subsets = new ArrayList<>();

        for (Dice d : hand.dices) {
            List<List<Dice>> tmpSubsets = new ArrayList<>();

            for (List<Dice> existing : subsets) {
                List<Dice> newSubset = new ArrayList<>(existing);
                newSubset.add(d);
                tmpSubsets.add(newSubset);
            }

            List<Dice> tmp = new ArrayList<>();
            tmp.add(d);
            tmpSubsets.add(tmp);

            subsets.addAll(tmpSubsets);
        }

        return subsets;
    }

    public List<Dice> chooseBestCombo() {
        List<List<Dice>> subsets = generateSubsets();
        List<List<Dice>> bestCombos = new ArrayList<>();
        List<Dice> bestCombo;
        int points;
        int maxPoints = 0;

        for (List<Dice> subset : subsets) {
            for (Dice d : subset) {
                d.markSelected();
            }

            points = hand.countPoints();
            if (points > maxPoints) {
                bestCombos.clear();
                maxPoints = points;
                bestCombos.add(subset);
            } else if (points == maxPoints) {
                bestCombos.add(subset);
            }

            for (Dice d : subset) {
                d.markUnselected();
            }
        }

        bestCombo = bestCombos.get(0);
        for (List<Dice> combo : bestCombos) {
            if (combo.size() < bestCombo.size()) {
                bestCombo = combo;
            }
        }

        return bestCombo;
    }
}
