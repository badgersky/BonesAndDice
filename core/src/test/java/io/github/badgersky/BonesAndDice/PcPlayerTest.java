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
}
