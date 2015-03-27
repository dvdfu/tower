package com.dvdfu.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.MathUtils;
import com.dvdfu.lib.AbstractScreen;
import com.dvdfu.lib.Main;

public class MainScreen extends AbstractScreen {
	OrthographicCamera cam;
    ModelBatch modelBatch;
	ModelInstance instances[];
	ModelInstance player, tower;
	Environment environment;
	float angle = 0;
	float y = 32, vy = 0;

	public MainScreen(Main game) {
		super(game);
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.zoom = 1 / 2f;
		cam.near = -3000;
		cam.far = 3000;
		cam.position.set(1, 1, 1);
		cam.lookAt(0, 0, 0);
		cam.up.set(0, 1, 0);
		modelBatch = new ModelBatch();
		int n = 24;
		instances = new ModelInstance[n];
		player = new ModelInstance(createPlayer());
		tower = new ModelInstance(createTower());
		float radius = 16 / MathUtils.sin(MathUtils.PI / 180 * 360 / 24 / 2) - 32;
		for (int i = 0; i < n; i++) {
			instances[i] = new ModelInstance(createBlock());
			if (i % 8 < 3) continue;
			float a = MathUtils.PI2 / n * i,
					x = radius * MathUtils.cos(a),
					z = radius * MathUtils.sin(a);
			instances[i].transform.setToTranslation(x, 0, z);
			instances[i].transform.rotate(cam.up, 360 / n * -(i + 0.75f));
		}
		environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.1f, 0.1f, 0.1f, 1f));
        environment.add(new DirectionalLight().set(1, 0.95f, 0.8f, -1f, -0.8f, -0.2f));
//        environment.add(new DirectionalLight().set(0.2f, 0.25f, 0.3f, 1f, 0.8f, 0.2f));
	}
	
	private void update(float delta) {
		if (Gdx.input.isKeyPressed(Input.Keys.A)) angle += 0.02f;
		if (Gdx.input.isKeyPressed(Input.Keys.D)) angle -= 0.02f;
		if (y + vy > 32) {
			vy -= 0.1f;
		} else {
			y = 32;
			vy = 0;
			if (Gdx.input.isKeyPressed(Input.Keys.W)) {
				vy = 3;
			}
		}
		y += vy;
		player.transform.setToTranslation(110*MathUtils.cos(angle), y, 110*MathUtils.sin(angle));
		player.transform.rotateRad(cam.up, -angle);
		cam.position.set(MathUtils.cos(angle) * 200, y, MathUtils.sin(angle) * 200);
		cam.lookAt(0, y, 0);
		cam.up.set(0, 1, 0);
		cam.update();
	}

	public void render(float delta) {
		update(delta);
		
		modelBatch.begin(cam);
		for (ModelInstance i : instances) {
	        modelBatch.render(i, environment);
		}
		modelBatch.render(player, environment);
		modelBatch.render(tower, environment);
        modelBatch.end();
	}
	
	public Model createPlayer() {
		Texture texture = new Texture(Gdx.files.internal("player.png"));
		Material mat = new Material(TextureAttribute.createDiffuse(texture));
		mat.set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
		ModelBuilder modelBuilder = new ModelBuilder();
		modelBuilder.begin();
		MeshPartBuilder tileBuilder;
		tileBuilder = modelBuilder.part("player", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal | Usage.TextureCoordinates, mat);
		tileBuilder.rect(0,0,-8,	0,16,-8,	0,16,8,	0,0,8,		1,0,0);
		tileBuilder.rect(0,0,-8,	0,0,8,		0,16,8,	0,16,-8,	-1,0,0);
		return modelBuilder.end();
	}
	
	public Model createTower() {
		Texture sideTexture = new Texture(Gdx.files.internal("tower.png"));
		Material mat = new Material(TextureAttribute.createDiffuse(sideTexture));
		ModelBuilder modelBuilder = new ModelBuilder();
		modelBuilder.begin();
		MeshPartBuilder tileBuilder;
		tileBuilder = modelBuilder.part("tower", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal | Usage.TextureCoordinates, mat);
		tileBuilder.cylinder(180, 1200, 180, 24, 0, 360);
		return modelBuilder.end();
	}
	
	public Model createBlock() {
		Texture capTexture = new Texture(Gdx.files.internal("cap.png"));
		Texture sideTexture = new Texture(Gdx.files.internal("side.png"));
		Texture botTexture = new Texture(Gdx.files.internal("bot.png"));
		Material cap = new Material(TextureAttribute.createDiffuse(capTexture));
		Material side = new Material(TextureAttribute.createDiffuse(sideTexture));
		Material bot = new Material(TextureAttribute.createDiffuse(botTexture));
		ModelBuilder modelBuilder = new ModelBuilder();
		modelBuilder.begin();
		MeshPartBuilder tileBuilder;
		tileBuilder = modelBuilder.part("cap", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal | Usage.TextureCoordinates, cap);
		tileBuilder.rect(0,32,0,	0,32,32,	32,32,32,	32,32,0,	0,1,0);
		tileBuilder = modelBuilder.part("bot", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal | Usage.TextureCoordinates, bot);
		tileBuilder.rect(0, 0, 0,	32, 0,0,	32, 0,32,	0, 0,32,	0,-1,0);
		tileBuilder = modelBuilder.part("side", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal | Usage.TextureCoordinates, side);
		tileBuilder.rect(32,0,32,	32, 0,0,	32,32,0,	32,32,32,	1,0,0);
		tileBuilder.rect(0, 0,32,	32,0,32,	32,32,32,	0,32,32,	0,0,1);
		tileBuilder.rect(0, 0, 0,	0, 0,32,	0,32,32,	0,32, 0,	-1,0,0);
		tileBuilder.rect(32,0, 0,	0, 0, 0,	0,32, 0,	32,32,0,	0,0,-1);
		return modelBuilder.end();
	}

	public void resize(int width, int height) {
	}

	public void show() {
	}

	public void hide() {
	}

	public void pause() {
	}

	public void resume() {
	}

	public void dispose() {
		modelBatch.dispose();
	}

}
