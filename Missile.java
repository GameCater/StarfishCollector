package com.alo.actors;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

public class Missile extends BaseActor{
	
	private boolean collected;

	public Missile(float x, float y, Stage s) {
		super(x, y, s);
		
		this.loadTexture("ball.png");
		
		Action boost = Actions.scaleBy(0.5f, 0.5f, 1f); 
		Action shrink = Actions.scaleBy(-0.5f, -0.5f, 1f);
		SequenceAction sa = Actions.sequence(boost, shrink);
		
		this.addAction(Actions.forever(sa));
		
		this.setBoundaryPolygon(8);
		
		this.collected = false;
	}
	
	public boolean isCollected() {
		return this.collected;
	}
	
	public void collect() {
		this.collected = true;
		this.clearActions();
		this.addAction(Actions.fadeOut(0.5f));
		this.addAction(Actions.after(Actions.removeActor()));
	}
}
