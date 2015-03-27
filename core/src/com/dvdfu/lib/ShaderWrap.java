package com.dvdfu.lib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class ShaderWrap extends ShaderProgram {
	
	public ShaderWrap(String shader) {
		this(shader + ".vsh", shader + ".fsh");
	}
	
	public ShaderWrap(String vertexShader, String fragmentShader) {
		super(Gdx.files.internal(vertexShader), Gdx.files.internal(fragmentShader));
		if (!isCompiled()) {
			Gdx.app.log("Shader compilation failed: ", getLog());
		}
		ShaderProgram.pedantic = false;
	}
}