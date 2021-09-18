package com.alo.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class StarfishCollector extends ApplicationAdapter {
	
	private static final String TAG = StarfishCollector.class.getName();
	
	private SpriteBatch batch;
	private Sprite turtle;
	private float turtleX, turtleY;
	private Rectangle turtleRect;
	private Sprite star;
	private Rectangle starRect;
	
	private boolean win;
	
	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		batch = new SpriteBatch();
		
		Pixmap pixmap = new Pixmap(30, 30, Format.RGBA8888);
		pixmap.setColor(0, 1, 0, 1);
		pixmap.fill();
		Texture turtleTex = new Texture(pixmap);
		turtle = new Sprite(turtleTex);
		turtle.setSize(30, 30);
		turtle.setOriginCenter();
		turtle.setPosition(0, 0);
		turtleRect = new Rectangle(turtle.getX(), turtle.getY(), 
				turtle.getWidth(), turtle.getHeight());
		turtleX = turtle.getX();
		turtleY = turtle.getY();
		
		pixmap.setColor(1, 1, 0, 1);
		pixmap.fill();
		Texture starTex = new Texture(pixmap);
		star = new Sprite(starTex);
		star.setSize(30, 30);
		star.setOriginCenter();
		star.setPosition(150, 150);
		starRect = new Rectangle(star.getX(), star.getY(), star.getWidth(), star.getHeight());
		Gdx.app.log(TAG, "starRec: " + starRect.width + " : " + starRect.height);
		win = false;
	}

	@Override
	public void render () {
		
		if (Gdx.input.isKeyPressed(Keys.A))
			this.turtleX --;
		if (Gdx.input.isKeyPressed(Keys.D))
			this.turtleX ++;
		if (Gdx.input.isKeyPressed(Keys.W))
			this.turtleY ++;
		if (Gdx.input.isKeyPressed(Keys.S))
			this.turtleY --;
		
		this.turtleRect.setPosition(turtleX, turtleY);
		
		if (this.turtleRect.overlaps(starRect)) {
			win = true;
			Gdx.app.log(TAG, "Åö×²ÁË");
		}
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(turtle, turtleX, turtleY);
		if (!win)
			batch.draw(star, star.getX(), star.getY());
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
