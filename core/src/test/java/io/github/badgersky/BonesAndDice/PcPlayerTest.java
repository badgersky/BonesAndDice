package io.github.badgersky.BonesAndDice;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class PcPlayerTest {

    @Test
    void testGenerateSubsetsCountFor3Dices() {
        Hand hand = new Hand();
        hand.dices.clear();

        for (int i = 0; i < 3; i++) {
            Dice d = new Dice();
            d.setValue(i + 1);
            hand.dices.add(d);
        }

        PcPlayer player = new PcPlayer(hand);
        List<List<Dice>> subsets = player.generateSubsets();

        System.out.println(subsets);
        assertEquals(7, subsets.size());
    }

    @Test
    void testGenerateSubsetsCountFor6Dices() {
        Hand hand = new Hand();

        PcPlayer player = new PcPlayer(hand);
        List<List<Dice>> subsets = player.generateSubsets();

        assertEquals(63, subsets.size());
    }

    @Test
    void testChooseBestCombo1() {
        Hand hand = new Hand();
        PcPlayer player = new PcPlayer(hand);
        List<Dice> bestCombo;

        hand.dices.get(0).setValue(2);
        hand.dices.get(1).setValue(2);
        hand.dices.get(2).setValue(2);
        hand.dices.get(3).setValue(3);
        hand.dices.get(4).setValue(4);
        hand.dices.get(5).setValue(4);

        bestCombo = player.chooseBestCombo();

        assertEquals(3, bestCombo.size());
        assertEquals(2, bestCombo.get(0).getValue());
        assertEquals(2, bestCombo.get(1).getValue());
        assertEquals(2, bestCombo.get(2).getValue());
    }

    @Test
    void testChooseBestCombo2() {
        Hand hand = new Hand();
        PcPlayer player = new PcPlayer(hand);
        List<Dice> bestCombo;

        hand.dices.get(0).setValue(1);
        hand.dices.get(1).setValue(2);
        hand.dices.get(2).setValue(2);
        hand.dices.get(3).setValue(3);
        hand.dices.get(4).setValue(4);
        hand.dices.get(5).setValue(4);

        bestCombo = player.chooseBestCombo();

        assertEquals(1, bestCombo.size());
        assertEquals(1, bestCombo.get(0).getValue());
    }

    @Test
    void testChooseBestCombo3() {
        Hand hand = new Hand();
        PcPlayer player = new PcPlayer(hand);
        List<Dice> bestCombo;

        hand.dices.get(0).setValue(1);
        hand.dices.get(1).setValue(1);
        hand.dices.get(2).setValue(1);
        hand.dices.get(3).setValue(1);
        hand.dices.get(4).setValue(1);
        hand.dices.get(5).setValue(1);

        bestCombo = player.chooseBestCombo();

        assertEquals(6, bestCombo.size());
        assertEquals(1, bestCombo.get(0).getValue());
        assertEquals(1, bestCombo.get(5).getValue());
    }

    @Test
    void testChooseBestCombo4() {
        Hand hand = new Hand();
        PcPlayer player = new PcPlayer(hand);
        List<Dice> bestCombo;

        hand.dices.get(0).setValue(1);
        hand.dices.get(1).setValue(2);
        hand.dices.get(2).setValue(3);
        hand.dices.get(3).setValue(4);
        hand.dices.get(4).setValue(5);
        hand.dices.get(5).setValue(6);

        bestCombo = player.chooseBestCombo();

        assertEquals(6, bestCombo.size());
        assertEquals(1, bestCombo.get(0).getValue());
        assertEquals(6, bestCombo.get(5).getValue());
    }

    @Test
    void testChooseBestCombo5() {
        Hand hand = new Hand();
        PcPlayer player = new PcPlayer(hand);
        List<Dice> bestCombo;

        hand.dices.get(0).setValue(5);
        hand.dices.get(1).setValue(3);
        hand.dices.get(2).setValue(3);
        hand.dices.get(3).setValue(4);
        hand.dices.get(4).setValue(6);
        hand.dices.get(5).setValue(6);

        bestCombo = player.chooseBestCombo();

        assertEquals(1, bestCombo.size());
        assertEquals(5, bestCombo.get(0).getValue());
    }
}
