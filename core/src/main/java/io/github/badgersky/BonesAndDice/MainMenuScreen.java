package io.github.badgersky.BonesAndDice;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class MainMenuScreen implements Screen {

    final Main game;

    Texture background;

    public MainMenuScreen(final Main game) {
        this.game = game;

        this.background = new Texture("menu_background.png");
    }

    @Override
    public void render(float delta) {
        draw();
        input();
    }

    private void input() {
        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);

        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.batch.begin();
        game.batch.draw(background, 0, 0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void show() {

    }

    @Override
    public void pause() {

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
