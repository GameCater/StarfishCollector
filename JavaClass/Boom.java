package com.alo.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Boom extends BaseActor{

	public Boom(float x, float y, Stage s) {
		super(x, y, s);
		
		this.setAnimationFromSheet("boom.png", 1, 7, 0.1f, false);
	}
	
	public void act(float deltaTime) {
		super.act(deltaTime);
		if (this.isAnimationFinished())
			this.remove();
	}

}
