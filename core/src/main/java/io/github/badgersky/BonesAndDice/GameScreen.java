package io.github.badgersky.BonesAndDice;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.HashMap;

public class GameScreen implements Screen {

    final Main game;
    Texture background;
    private Hand hand1;
    private Hand hand2;
    private int diceIndex;
    private TextureAtlas diceAtlas;
    private HashMap<String, TextureRegion> diceRegions;
    private TextureRegion hoverRegion;
    private TextureRegion chosenRegion;
    private TextureRegion hoverChosenRegion;
    private int roundPoints1;
    private int selectedPoints1;
    private int totalPoints1;

    public GameScreen(final Main game) {
        this.game = game;

        background = new Texture("game_background.png");
        diceAtlas = new TextureAtlas(Gdx.files.internal("dices.atlas"));

        diceRegions = new HashMap<>();
        for (int i = 1; i <= 6; i++) {
            diceRegions.put("dice" + i, diceAtlas.findRegion("dice" + i));
        }
        hoverRegion = new TextureRegion(diceAtlas.findRegion("hover"));
        chosenRegion = new TextureRegion(diceAtlas.findRegion("chosen"));
        hoverChosenRegion = new TextureRegion(diceAtlas.findRegion("chosenhover"));

        hand1 = new Hand();
        hand2 = new Hand();
        diceIndex = 0;

        roundPoints1 = 0;
        selectedPoints1 = 0;
        totalPoints1 = 0;
    }

    private void drawPoints() {
        float x = game.viewport.getWorldWidth() - 1;
        float y = 2;

        game.font.draw(game.batch, "Selected: " + selectedPoints1, x, y);
        game.font.draw(game.batch, "Round: " + roundPoints1, x, y - 0.5f);
        game.font.draw(game.batch, "Total: " + totalPoints1, x, y - 1f);
    }

    private void drawDices() {
        float diceSize = 1;
        float spacing = 0.2f;
        float totalSize = diceSize + spacing;

        for (int i = 0; i < hand1.dices.size(); i++) {
            Dice dice = hand1.dices.get(i);
            String key = "dice" + dice.getValue();

            TextureRegion region = diceRegions.get(key);
            float x = 2 + i * totalSize;
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

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(background, 0, 0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        drawDices();
        drawPoints();
        game.batch.end();
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
    }

    @Override
    public void render(float v) {
        draw();
        input();
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
