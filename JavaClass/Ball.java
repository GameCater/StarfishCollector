package com.alo.actors;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Ball extends BaseActor{

	public Ball(float x, float y, Stage s) {
		super(x, y, s);
		
		this.loadTexture("ball.png");
		
		Action spin = Actions.rotateBy(180, 1);
		this.addAction(Actions.forever(spin));
	}
}
