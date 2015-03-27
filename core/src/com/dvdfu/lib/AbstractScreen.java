package com.dvdfu.lib;

import com.badlogic.gdx.Screen;

public abstract class AbstractScreen implements Screen {
	protected Main game;

	public AbstractScreen(Main game) {
		this.game = game;
	}

	public abstract void render(float delta);

	public abstract void resize(int width, int height);

	public abstract void show();

	public abstract void hide();

	public abstract void pause();

	public abstract void resume();

	public abstract void dispose();
}