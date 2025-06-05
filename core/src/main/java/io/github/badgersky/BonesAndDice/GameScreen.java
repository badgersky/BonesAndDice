package io.github.badgersky.BonesAndDice;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.sun.tools.javac.comp.Todo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameScreen implements Screen {

    final Main game;
    Texture background;
    private Hand hand1;
    private Hand hand2;
    private int diceIndex;
    private final TextureAtlas diceAtlas;
    private final TextureAtlas msgAtlas;
    private final HashMap<String, TextureRegion> diceRegions;
    private final TextureRegion hoverRegion;
    private final TextureRegion chosenRegion;
    private final TextureRegion hoverChosenRegion;
    private final TextureRegion msgFail;
    private final TextureRegion msgWin;
    private final TextureRegion msgLose;
    private float msgTimer;
    private TextureRegion currMsg;
    private int roundPoints1;
    private int selectedPoints1;
    private int totalPoints1;
    private int roundPoints2;
    private int selectedPoints2;
    private int totalPoints2;
    private boolean playerTurn;
    private PcPlayer pcPlayer;
    private float pcTimer;
    private int pcAction;
    public int winningPoints;
    private boolean shouldRollAtStart;

    public GameScreen(final Main game) {
        this.game = game;

        background = new Texture("game_background.png");
        diceAtlas = new TextureAtlas(Gdx.files.internal("dices.atlas"));
        msgAtlas = new TextureAtlas(Gdx.files.internal("messages.atlas"));

        diceRegions = new HashMap<>();
        for (int i = 1; i <= 6; i++) {
            diceRegions.put("dice" + i, diceAtlas.findRegion("dice" + i));
        }
        hoverRegion = new TextureRegion(diceAtlas.findRegion("hover"));
        chosenRegion = new TextureRegion(diceAtlas.findRegion("chosen"));
        hoverChosenRegion = new TextureRegion(diceAtlas.findRegion("chosenhover"));

        msgFail = new TextureRegion(msgAtlas.findRegion("fail"));
        msgLose = new TextureRegion(msgAtlas.findRegion("lose"));
        msgWin = new TextureRegion(msgAtlas.findRegion("win"));
        currMsg = null;
        msgTimer = 0f;

        hand1 = new Hand();
        hand2 = new Hand();
        diceIndex = 0;

        pcPlayer = new PcPlayer(hand2);
        pcTimer = 0.7f;
        pcAction = 0;

        roundPoints1 = 0;
        selectedPoints1 = 0;
        totalPoints1 = 0;

        roundPoints2 = 0;
        selectedPoints2 = 0;
        totalPoints2 = 0;

        playerTurn = true;
        shouldRollAtStart = true;

        winningPoints = 4000;
    }

    private void pcTurn(float delta) {
        int selectNum = 0;

        pcTimer -= delta;
        if (pcTimer <= 0) {
            pcTimer = 2f;

            switch (pcAction) {
                case 0:
                    System.out.println("PC rolling dices");
                    if (hand2.dices.isEmpty()) {
                        hand2.returnPutAwayDices();
                    }
                    rollAndCheck(hand2, true);
                    pcAction = 1;
                    break;
                case 1:
                    System.out.println("PC selecting dices");
                    int pointsSoFar = totalPoints2 + selectedPoints2 + roundPoints2;
                    selectedPoints2 += pcPlayer.play(pointsSoFar, winningPoints);
                    pcAction = 2;
                    break;
                case 2:
                    System.out.println("PC deciding if he brave or not");
                    for (Dice d : hand2.dices) {
                        if (d.selected) {
                            selectNum += 1;
                        }
                    }

                    if (shouldEndRound(selectNum)) {
                        endPcRound(false);
                    } else {
                        hand2.putAwaySelectedDices();
                        roundPoints2 += selectedPoints2;
                        selectedPoints2 = 0;
                    }

                    pcAction = 0;
                    break;
            }
        }
    }

    private boolean shouldEndRound(int selectNum) {
        int diceLeft = hand2.dices.size() - selectNum;
        int putAway = hand2.putAwayDices.size();
        int projectedPoints = totalPoints2 + selectedPoints2 + roundPoints2;

        if (putAway == 6) return false;
        if ((totalPoints1 - totalPoints2) >= 1500 && (roundPoints2 + selectedPoints2) <= 300 && diceLeft >= 2) return false;
        if ((totalPoints2 - totalPoints1) >= 1000 && (roundPoints2 + selectedPoints2) >= 400) return true;
        if (diceLeft == 1 && (roundPoints2 + selectedPoints2) >= 300) return true;
        if (diceLeft == 2 && (roundPoints2 + selectedPoints2) >= 600) return true;
        if (diceLeft == 3 && (roundPoints2 + selectedPoints2) >= 800) return true;
        if (diceLeft == 4 && (roundPoints2 + selectedPoints2) >= 1000) return true;
        if (diceLeft == 5 && (roundPoints2 + selectedPoints2) >= 1200) return true;
        if (roundPoints2 >= 500 && putAway >= 4) return true;
        if (projectedPoints >= winningPoints) return true;

        return false;
    }

    private void drawPoints() {
        float x = game.viewport.getWorldWidth() - 1.6f;
        float y1 = 2;
        float y2 = 6.5f;

        game.font.draw(game.batch, "Selected: " + selectedPoints1, x, y1);
        game.font.draw(game.batch, "Round: " + roundPoints1, x, y1 - 0.4f);
        game.font.draw(game.batch, "Total: " + totalPoints1, x, y1 - 0.8f);

        game.font.draw(game.batch, "Selected: " + selectedPoints2, x, y2);
        game.font.draw(game.batch, "Round: " + roundPoints2, x, y2 - 0.4f);
        game.font.draw(game.batch, "Total: " + totalPoints2, x, y2 - 0.8f);
    }

    private void drawDices() {
        float diceSize = 1;
        float spacing = 0.2f;
        float totalSize = diceSize + spacing;
        float startX = 1.8f;
        float y = 1;
        float x;

        for (int i = 0; i < hand1.dices.size(); i++) {
            Dice dice = hand1.dices.get(i);
            String key = "dice" + dice.getValue();

            TextureRegion region = diceRegions.get(key);
            x = startX + i * totalSize;
            if (i == diceIndex && dice.selected) {
                game.batch.draw(hoverChosenRegion, x, y, diceSize, diceSize);
            } else if (i == diceIndex) {
                game.batch.draw(hoverRegion, x, y, diceSize, diceSize);
            } else if (dice.selected) {
                game.batch.draw(chosenRegion, x, y, diceSize, diceSize);
            }

            game.batch.draw(region, x, y, diceSize, diceSize);
        }
    }

    private void drawComputerDices() {
        float diceSize = 1;
        float spacing = 0.2f;
        float totalSize = diceSize + spacing;

        float startX = 1.8f;
        float y = 5.5f;
        float x;

        for (int i = 0; i < hand2.dices.size(); i++) {
            Dice dice = hand2.dices.get(i);
            String key = "dice" + dice.getValue();

            TextureRegion region = diceRegions.get(key);
            x = startX + i * totalSize;

            if (dice.selected) {
                game.batch.draw(chosenRegion, x, y, diceSize, diceSize);
            }
            game.batch.draw(region, x, y, diceSize, diceSize);
        }
    }

    private void drawPutAwayDices() {
        float diceSize = 1;
        for (int i = 0; i < hand1.putAwayDices.size(); i++) {
            Dice dice = hand1.putAwayDices.get(i);
            String key = "dice" + dice.getValue();

            TextureRegion region = diceRegions.get(key);
            float y = 0.5f + i * diceSize;
            float x = 9;

            game.batch.draw(region, x, y, diceSize, diceSize);
        }
    }

    private void drawPutAwayPCDices() {
        float diceSize = 1;
        for (int i = 0; i < hand2.putAwayDices.size(); i++) {
            Dice dice = hand2.putAwayDices.get(i);
            String key = "dice" + dice.getValue();

            TextureRegion region = diceRegions.get(key);
            float y = 5.5f - i * diceSize;
            float x = 0.5f;

            game.batch.draw(region, x, y, diceSize, diceSize);
        }
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(background, 0, 0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        drawDices();
        drawPutAwayDices();
        drawComputerDices();
        drawPutAwayPCDices();
        drawMsg();
        drawPoints();
        game.batch.end();
    }

    private void drawMsg() {
        float centerX = game.viewport.getWorldWidth() / 2f;
        float centerY = game.viewport.getWorldHeight() / 2f;

        float msgWidth = 4f;
        float msgHeight = 2f;

        float drawX = centerX - msgWidth / 2f;
        float drawY = centerY - msgHeight / 2f;

        if (currMsg != null) {
            game.batch.draw(currMsg, drawX, drawY, msgWidth, msgHeight);
        }
    }

    private void input() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            pause();
        }
        if (playerTurn) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                if (diceIndex == hand1.dices.size() - 1) {
                    diceIndex = 0;
                } else {
                    diceIndex += 1;
                }
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                if (diceIndex == 0) {
                    diceIndex = hand1.dices.size() - 1;
                } else {
                    diceIndex -= 1;
                }
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
                selectedPoints1 = 0;
                if (hand1.dices.get(diceIndex).selected) {
                    hand1.unselectDice(diceIndex);
                } else {
                    hand1.selectDice(diceIndex);
                }
                selectedPoints1 += hand1.countPoints();
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
                if (hand1.countPoints() > 0) {
                    hand1.putAwaySelectedDices();
                    roundPoints1 += selectedPoints1;
                    selectedPoints1 = 0;
                    diceIndex = 0;

                    if (hand1.dices.isEmpty()) {
                        hand1.returnPutAwayDices();
                    }

                    rollAndCheck(hand1, false);
                }
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
                if (hand1.countPoints() > 0) {
                    endRound(false);
                }
            }
        }
    }

    private void rollAndCheck(Hand hand, boolean pc) {
        hand.rollHand();
        if (hand.hasNoPoints()) {
            if (pc) {
                endPcRound(true);
            } else {
                endRound(true);
            }
        }
    }

    public void endPcRound(boolean fail) {
        if (!fail) {
            totalPoints2 += roundPoints2 + selectedPoints2;
            System.out.println("pc finishing its round counting points");
        } else {
            System.out.println("pc failed!");
        }

        roundPoints2 = 0;
        selectedPoints2 = 0;
        hand2.returnPutAwayDices();
        hand2.resetSelection();
        playerTurn = true;
        shouldRollAtStart = true;
        pcAction = 0;
    }

    public void endRound(boolean fail) {
        if (!fail) {
            totalPoints1 += roundPoints1 + selectedPoints1;
        } else {
            currMsg = msgFail;
            msgTimer = 2f;
        }

        roundPoints1 = 0;
        selectedPoints1 = 0;
        hand1.returnPutAwayDices();
        hand1.resetSelection();
        diceIndex = 0;
        playerTurn = false;
        shouldRollAtStart = true;
        pcAction = 0;
    }

    @Override
    public void render(float delta) {
        input();

        if (playerTurn && shouldRollAtStart) {
            if (hand1.dices.isEmpty()) {
                hand1.returnPutAwayDices();
            }
            rollAndCheck(hand1, false);
            shouldRollAtStart = false;
        }

        if (!playerTurn) {
            pcTurn(delta);
        }

        if (msgTimer > 0) {
            msgTimer -= delta;
            if (msgTimer <= 0) {
                currMsg = null;
            }
        }

        draw();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int i, int i1) {
        game.viewport.update(i, i1, true);
    }

    @Override
    public void pause() {
        PauseScreen pauseScreen = new PauseScreen(game, this);
        game.setScreen(pauseScreen);
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
