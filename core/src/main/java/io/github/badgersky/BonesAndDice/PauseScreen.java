package io.github.badgersky.BonesAndDice;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

public class PauseScreen implements Screen {

    final Main game;
    final GameScreen gameScreen;
    Texture background;
    private Stage stage;
    private final TextureAtlas buttonAtlas;

    public PauseScreen(Main game, GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;

        background = new Texture("pause_background.png");

        stage = new Stage(game.viewport, game.batch);
        Gdx.input.setInputProcessor(stage);

        buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons.atlas"));

        ImageButton.ImageButtonStyle backBtnStyle = new ImageButton.ImageButtonStyle();
        backBtnStyle.up = new TextureRegionDrawable(buttonAtlas.findRegion("backbtn"));
        backBtnStyle.down = new TextureRegionDrawable(buttonAtlas.findRegion("backbtn_click"));
        backBtnStyle.over = new TextureRegionDrawable(buttonAtlas.findRegion("backbtn_hover"));

        ImageButton.ImageButtonStyle quitBtnStyle = new ImageButton.ImageButtonStyle();
        quitBtnStyle.up = new TextureRegionDrawable(buttonAtlas.findRegion("quitbtn"));
        quitBtnStyle.down = new TextureRegionDrawable(buttonAtlas.findRegion("quitbtn_click"));
        quitBtnStyle.over = new TextureRegionDrawable(buttonAtlas.findRegion("quitbtn_hover"));

        ImageButton.ImageButtonStyle menuBtnStyle = new ImageButton.ImageButtonStyle();
        menuBtnStyle.up = new TextureRegionDrawable(buttonAtlas.findRegion("menubtn"));
        menuBtnStyle.down = new TextureRegionDrawable(buttonAtlas.findRegion("menubtn_click"));
        menuBtnStyle.over = new TextureRegionDrawable(buttonAtlas.findRegion("menubtn_hover"));

        ImageButton backBtn = new ImageButton(backBtnStyle);
        ImageButton quitBtn = new ImageButton(quitBtnStyle);
        ImageButton menuBtn = new ImageButton(menuBtnStyle);

        menuBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                long id = game.btnSound.play(1f);
                game.btnSound.setPitch(id, 0.8f);
                game.btnSound.setVolume(id, 0.2f);
                game.setScreen(new MainMenuScreen(game));
                gameScreen.dispose();
                dispose();
            }
        });

        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                long id = game.btnSound.play(1f);
                game.btnSound.setPitch(id, 0.8f);
                game.btnSound.setVolume(id, 0.2f);
                resume();
            }
        });

        quitBtn.addListener(new ClickListener() {
           @Override
           public void clicked(InputEvent event, float x, float y) {
               long id = game.btnSound.play(1f);
               game.btnSound.setPitch(id, 0.8f);
               game.btnSound.setVolume(id, 0.2f);
               Gdx.app.exit();
           }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.center();

        table.add(backBtn).size(2f, 1f).row();
        table.add(menuBtn).size(2f, 1f).row();
        table.add(quitBtn).size(2f, 1f).row();

        table.padBottom(1f);

        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        draw(delta);
        input();
    }

    private void input() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            resume();
        }
    }

    private void draw(float delta) {
        ScreenUtils.clear(Color.BLACK);

        game.viewport.apply();
        game.batch.setProjectionMatrix(stage.getCamera().combined);

        game.batch.begin();
        game.batch.draw(background, 0, 0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        game.batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {
        game.viewport.update(i, i1, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        game.setScreen(gameScreen);
        dispose();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
