package com.dvdfu.tower;

import com.dvdfu.lib.Main;

public class MainGame extends Main {
	
	public void create () {
		super.create();
		enterScreen(new MainScreen(this));
	}

	public void render () {
		super.render();
	}
}
