package io.github.badgersky.BonesAndDice;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {
    private final Hand hand = new Hand();

    @Test
    void hasNoPoints() {
        hand.dices.get(0).setValue(2);
        hand.dices.get(1).setValue(2);
        hand.dices.get(2).setValue(3);
        hand.dices.get(3).setValue(3);
        hand.dices.get(4).setValue(4);
        hand.dices.get(5).setValue(4);

        assertTrue(hand.hasNoPoints());
    }

    @Test
    void has26() {
        hand.dices.get(0).setValue(2);
        hand.dices.get(1).setValue(3);
        hand.dices.get(2).setValue(4);
        hand.dices.get(3).setValue(5);
        hand.dices.get(4).setValue(6);
        hand.dices.get(5).setValue(6);

        ArrayList<Integer> diceValues = new ArrayList<>();

        for (Dice d : hand.dices) {
            diceValues.add(d.getValue());
        }
        assertTrue(hand.has26(diceValues));
    }

    @Test
    void has15() {
        hand.dices.get(0).setValue(1);
        hand.dices.get(1).setValue(2);
        hand.dices.get(2).setValue(3);
        hand.dices.get(3).setValue(4);
        hand.dices.get(4).setValue(5);
        hand.dices.get(5).setValue(5);

        ArrayList<Integer> diceValues = new ArrayList<>();

        for (Dice d : hand.dices) {
            diceValues.add(d.getValue());
        }
        assertTrue(hand.has15(diceValues));
    }

    @Test
    void has16() {
        hand.dices.get(0).setValue(1);
        hand.dices.get(1).setValue(2);
        hand.dices.get(2).setValue(3);
        hand.dices.get(3).setValue(4);
        hand.dices.get(4).setValue(5);
        hand.dices.get(5).setValue(6);

        ArrayList<Integer> diceValues = new ArrayList<>();

        for (Dice d : hand.dices) {
            diceValues.add(d.getValue());
        }
        assertTrue(hand.has15(diceValues));
    }

    @Test
    void countOccurrences() {
        hand.dices.get(0).setValue(1);
        hand.dices.get(1).setValue(1);
        hand.dices.get(2).setValue(1);
        hand.dices.get(3).setValue(2);
        hand.dices.get(4).setValue(2);
        hand.dices.get(5).setValue(2);

        ArrayList<Integer> diceValues = new ArrayList<>();

        for (Dice d : hand.dices) {
            diceValues.add(d.getValue());
        }

        assertEquals(3, hand.countOccurrences(1, diceValues));
    }

    @Test
    void rollHand() {
        hand.dices.get(0).setValue(1);
        hand.dices.get(1).setValue(1);
        hand.dices.get(2).setValue(1);
        hand.dices.get(3).setValue(1);
        hand.dices.get(4).setValue(1);
        hand.dices.get(5).setValue(1);

        hand.rollHand();
        ArrayList<Integer> diceValues = new ArrayList<>();

        for (Dice d : hand.dices) {
            diceValues.add(d.getValue());
        }

        assertNotEquals(6, hand.countOccurrences(1, diceValues));
    }

    @Test
    void selectDice() {
        hand.selectDice(0);

        assertTrue(hand.dices.get(0).selected);
    }

    @Test
    void unselectDice() {
        hand.unselectDice(0);

        assertFalse(hand.dices.get(0).selected);
    }

    @Test
     void countPoints1() {
        hand.dices.get(0).setValue(1);
        hand.dices.get(1).setValue(1);
        hand.dices.get(2).setValue(1);
        hand.dices.get(3).setValue(3);
        hand.dices.get(4).setValue(2);
        hand.dices.get(5).setValue(3);

        hand.selectDice(0);
        hand.selectDice(1);
        hand.selectDice(2);

//        System.out.printf("%d %d %d\n", hand.dices.get(0).getValue(), hand.dices.get(1).getValue(), hand.dices.get(2).getValue());

        assertEquals(1000, hand.countPoints());
    }

    @Test
    void countPoints2() {
        hand.dices.get(0).setValue(1);
        hand.dices.get(1).setValue(1);
        hand.dices.get(2).setValue(1);
        hand.dices.get(3).setValue(3);
        hand.dices.get(4).setValue(2);
        hand.dices.get(5).setValue(3);

        hand.selectDice(0);
        hand.selectDice(1);
        hand.selectDice(2);
        hand.selectDice(3);
        hand.selectDice(4);
        hand.selectDice(5);

//        System.out.printf("%d %d %d\n", hand.dices.get(0).getValue(), hand.dices.get(1).getValue(), hand.dices.get(2).getValue());

        assertEquals(0, hand.countPoints());
    }

    @Test
    void countPoints3() {
        hand.dices.get(0).setValue(1);
        hand.dices.get(1).setValue(1);
        hand.dices.get(2).setValue(2);
        hand.dices.get(3).setValue(3);
        hand.dices.get(4).setValue(2);
        hand.dices.get(5).setValue(3);

        hand.selectDice(0);
        hand.selectDice(1);

//        System.out.printf("%d %d %d\n", hand.dices.get(0).getValue(), hand.dices.get(1).getValue(), hand.dices.get(2).getValue());

        assertEquals(200, hand.countPoints());
    }

    @Test
    void countPoints4() {
        hand.dices.get(0).setValue(2);
        hand.dices.get(1).setValue(2);
        hand.dices.get(2).setValue(2);
        hand.dices.get(3).setValue(3);
        hand.dices.get(4).setValue(2);
        hand.dices.get(5).setValue(3);

        hand.selectDice(0);
        hand.selectDice(1);
        hand.selectDice(2);
        hand.selectDice(4);

//        System.out.printf("%d %d %d\n", hand.dices.get(0).getValue(), hand.dices.get(1).getValue(), hand.dices.get(2).getValue());

        assertEquals(400, hand.countPoints());
    }

    @Test
    void countPoints5() {
        hand.dices.get(0).setValue(1);
        hand.dices.get(1).setValue(2);
        hand.dices.get(2).setValue(3);
        hand.dices.get(3).setValue(4);
        hand.dices.get(4).setValue(5);
        hand.dices.get(5).setValue(6);

        hand.selectDice(0);
        hand.selectDice(1);
        hand.selectDice(2);
        hand.selectDice(3);
        hand.selectDice(4);
        hand.selectDice(5);

//        System.out.printf("%d %d %d\n", hand.dices.get(0).getValue(), hand.dices.get(1).getValue(), hand.dices.get(2).getValue());

        assertEquals(1500, hand.countPoints());
    }

    @Test
    void countPoints6() {
        hand.dices.get(0).setValue(2);
        hand.dices.get(1).setValue(2);
        hand.dices.get(2).setValue(2);
        hand.dices.get(3).setValue(3);
        hand.dices.get(4).setValue(3);
        hand.dices.get(5).setValue(3);

        hand.selectDice(0);
        hand.selectDice(1);
        hand.selectDice(2);
        hand.selectDice(3);
        hand.selectDice(4);
        hand.selectDice(5);

//        System.out.printf("%d %d %d\n", hand.dices.get(0).getValue(), hand.dices.get(1).getValue(), hand.dices.get(2).getValue());

        assertEquals(500, hand.countPoints());
    }
}
