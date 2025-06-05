package io.github.badgersky.BonesAndDice;

import java.util.ArrayList;
import java.util.List;

public class PcPlayer {

    Hand hand;

    public PcPlayer(Hand hand) {
        this.hand = hand;
    }

    public int play() {
        if (hand.hasNoPoints()) {
            return -1;
        }

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

    private void chooseBestCombo() {
    }
}
