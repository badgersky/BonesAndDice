package io.github.badgersky.BonesAndDice;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

public class ControlScreen implements Screen {

    final Main game;
    final Screen prevScreen;
    final Texture background;
    private Stage stage;
    private final TextureAtlas keyAtlas;
    private final TextureAtlas buttonAtlas;

    public ControlScreen(Main game, Screen prevScreen) {
        this.game = game;
        this.prevScreen = prevScreen;

        this.background = new Texture("pause_background.png");

        stage = new Stage(game.viewport, game.batch);
        Gdx.input.setInputProcessor(stage);

        keyAtlas = new TextureAtlas(Gdx.files.internal("keys.atlas"));
        buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons.atlas"));

        String[] keys = {"arrow_key", "e_key", "f_key", "q_key", "esc_key"};
        String[] description = {
            "Change hovered dice.",
            "Select/Deselect dice.",
            "Put away dices, count up selected points and throw again.",
            "Count up round points and finish your turn.",
            "Pause/Unpause the game."
        };

        Table keysTable = new Table();
        keysTable.top().left().padTop(1f).padLeft(1f);
        keysTable.setFillParent(true);

        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = game.font;

        for (int i = 0; i < keys.length; i++) {
            TextureRegion keyRegion = keyAtlas.findRegion(keys[i]);
            Image keyImage = new Image(keyRegion);
            Label keyLabel = new Label(description[i], labelStyle);

            keysTable.add(keyImage).size(1f, 1f).left();
            keysTable.add(keyLabel).padLeft(0.5f).left();
            keysTable.row();
        }

        stage.addActor(keysTable);

        ImageButton.ImageButtonStyle backBtnStyle = new ImageButton.ImageButtonStyle();
        backBtnStyle.up = new TextureRegionDrawable(buttonAtlas.findRegion("backbtn"));
        backBtnStyle.down = new TextureRegionDrawable(buttonAtlas.findRegion("backbtn_click"));
        backBtnStyle.over = new TextureRegionDrawable(buttonAtlas.findRegion("backbtn_hover"));

        ImageButton backBtn = new ImageButton(backBtnStyle);
        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(prevScreen);
            }
        });

        Table backBtnTable = new Table();
        backBtnTable.bottom().right().padBottom(1f).padRight(1f);
        backBtnTable.setFillParent(true);
        backBtnTable.add(backBtn).size(2f, 1f);

        stage.addActor(backBtnTable);
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
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        draw(delta);
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
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
