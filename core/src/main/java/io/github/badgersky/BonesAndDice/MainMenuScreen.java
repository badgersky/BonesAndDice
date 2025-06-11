package io.github.badgersky.BonesAndDice;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class MainMenuScreen implements Screen {

    final Main game;
    private final Texture background;
    private final Stage stage;
    private final TextureAtlas buttonAtlas;

    public MainMenuScreen(final Main game) {
        this.game = game;

        background = new Texture("menu_background.png");

        stage = new Stage(game.viewport, game.batch);
        Gdx.input.setInputProcessor(stage);

        buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons.atlas"));

        ImageButton.ImageButtonStyle playBtnStyle = new ImageButton.ImageButtonStyle();
        playBtnStyle.up = new TextureRegionDrawable(buttonAtlas.findRegion("playbtn"));
        playBtnStyle.over = new TextureRegionDrawable(buttonAtlas.findRegion("playbtn_hover"));
        playBtnStyle.down = new TextureRegionDrawable(buttonAtlas.findRegion("playbtn_click"));

        ImageButton.ImageButtonStyle quitBtnStyle = new ImageButton.ImageButtonStyle();
        quitBtnStyle.up = new TextureRegionDrawable(buttonAtlas.findRegion("quitbtn"));
        quitBtnStyle.over = new TextureRegionDrawable(buttonAtlas.findRegion("quitbtn_hover"));
        quitBtnStyle.down = new TextureRegionDrawable(buttonAtlas.findRegion("quitbtn_click"));

        ImageButton.ImageButtonStyle controlsBtnStyle = new ImageButton.ImageButtonStyle();
        controlsBtnStyle.up = new TextureRegionDrawable(buttonAtlas.findRegion("controlsbtn"));
        controlsBtnStyle.over = new TextureRegionDrawable(buttonAtlas.findRegion("controlsbtn_hover"));
        controlsBtnStyle.down = new TextureRegionDrawable(buttonAtlas.findRegion("controlsbtn_click"));

        ImageButton playBtn = new ImageButton(playBtnStyle);
        ImageButton quitBtn = new ImageButton(quitBtnStyle);
        ImageButton controlsBtn = new ImageButton(controlsBtnStyle);

        controlsBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ControlScreen(game, MainMenuScreen.this));
            }
        });

        playBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                long id = game.btnSound.play(1f);
//                game.btnSound.setPitch(id, 0.8f);
//                game.btnSound.setVolume(id, 0.2f);
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });

        quitBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                long id = game.btnSound.play(1f);
//                game.btnSound.setPitch(id, 0.8f);
//                game.btnSound.setVolume(id, 0.2f);
                Gdx.app.exit();
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.center();

        table.add(playBtn).size(2f, 1f).row();
        table.add(controlsBtn).size(2f, 1f).row();
        table.add(quitBtn).size(2f, 1f).row();

        table.padBottom(1f);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        draw(delta);
    }

    private void draw(float delta) {
        ScreenUtils.clear(Color.BLACK);

        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.batch.begin();
        game.batch.draw(background, 0, 0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        game.batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
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
        background.dispose();
        stage.dispose();
        buttonAtlas.dispose();
    }
}
