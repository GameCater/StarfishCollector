package com.alo.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Boom extends BaseActor{

	public Boom(float x, float y, Stage s) {
		super(x, y, s);
		
		this.setAnimationFromSheet("boom.png", 1, 7, 0.1f, true, -1);
	}
	
	public void act(float deltaTime) {
		super.act(deltaTime);
		if (this.isAnimationFinished())
			this.remove();
	}

}
