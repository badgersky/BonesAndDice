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

        roundPoints1 = 0;
        selectedPoints1 = 0;
        totalPoints1 = 0;

        rollAndCheck(hand1);
    }

    private void drawPoints() {
        float x = game.viewport.getWorldWidth() - 1.6f;
        float y = 2;

        game.font.draw(game.batch, "Selected: " + selectedPoints1, x, y);
        game.font.draw(game.batch, "Round: " + roundPoints1, x, y - 0.4f);
        game.font.draw(game.batch, "Total: " + totalPoints1, x, y - 0.8f);
    }

    private void drawDices() {
        float diceSize = 1;
        float spacing = 0.2f;
        float totalSize = diceSize + spacing;

        for (int i = 0; i < hand1.dices.size(); i++) {
            Dice dice = hand1.dices.get(i);
            String key = "dice" + dice.getValue();

            TextureRegion region = diceRegions.get(key);
            float x = 1.8f + i * totalSize;
            float y = 1;
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

    private void drawPutAwayDices(Hand hand) {
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

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(background, 0, 0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        drawDices();
        drawPutAwayDices(hand1);
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

                rollAndCheck(hand1);
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            if (hand1.countPoints() > 0) {
                endRound(false);
            }
        }
    }

    private void rollAndCheck(Hand hand) {
        hand.rollHand();
        if (hand.hasNoPoints()) {
            endRound(true);
        }
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
        diceIndex = 0;
        hand1.resetSelection();
    }

    @Override
    public void render(float delta) {
        input();

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
