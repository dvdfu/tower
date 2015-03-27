package com.dvdfu.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class Tower {
	int layers = 10;
	int layerHeight = 32;
	int cells = 24;
	int cellWidth = 32;
	int radius = 128;
	Texture texture;
	ModelInstance instances[];
	
	public Tower() {
		texture = new Texture(Gdx.files.internal("text.png"));
		ModelBuilder modelBuilder = new ModelBuilder();
		instances = new ModelInstance[24];
		Model model = modelBuilder.createBox(32f, 32f, 32f,
				new Material(TextureAttribute.createDiffuse(texture)),
				Usage.Position | Usage.Normal | Usage.TextureCoordinates);
		for (int i = 0; i < 24; i++) {
			instances[i] = new ModelInstance(model);
//			instances[i].transform.setToTranslation(0, i * 32, 0);
		}
        model.dispose();
	}
	
	public ModelInstance[] getInstances() {
		return instances;
	}
}
