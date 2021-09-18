package com.alo.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Turtle extends ActorBeta{
	
	public Turtle() {
		super();
	}
	
	public void act(float deltaTime) {
		super.act(deltaTime);
		if (Gdx.input.isKeyPressed(Keys.A))
			this.moveBy(-1, 0);
		if (Gdx.input.isKeyPressed(Keys.D))
			this.moveBy(1, 0);
		if (Gdx.input.isKeyPressed(Keys.W))
			this.moveBy(0, 1);
		if (Gdx.input.isKeyPressed(Keys.S))
			this.moveBy(0, -1);
	}
}
