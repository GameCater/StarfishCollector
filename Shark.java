package com.alo.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Shark extends ActorBeta{
	
	public static final String TAG = Shark.class.getName();
	
	private Vector2 velocity;
	private boolean horizontalMove = true;
	private boolean verticalMove = false;
	
	public Shark() {
		super();
		this.velocity = new Vector2(50, 30);
	}
	
	public void act(float deltaTime) {
		
		if (this.getY() >= -this.getHeight() && this.getY() <= Gdx.graphics.getHeight() && 
				this.getX() >= -this.getWidth() && this.getX() <= Gdx.graphics.getWidth() && 
				horizontalMove) {
			this.setX(this.getX() + velocity.x * deltaTime);
		}
		
		if (this.getX() > Gdx.graphics.getWidth()) {
			float nextX = MathUtils.random(0, Gdx.graphics.getWidth() - this.getWidth() + 1);
			this.setPosition(nextX, Gdx.graphics.getHeight());
			horizontalMove = false;
			verticalMove = true;
		}
		
		if (this.getY() >= -this.getHeight() && this.getY() <= Gdx.graphics.getHeight() && 
				this.getX() >= -this.getWidth() && this.getX() <= Gdx.graphics.getWidth() && 
				verticalMove) {
			this.setY(this.getY() - velocity.y * deltaTime);
		}
		
		if (this.getY() < -this.getHeight()) {
			float nextY = MathUtils.random(10, Gdx.graphics.getHeight() - this.getHeight());
			this.setPosition(-this.getWidth(), nextY);
			verticalMove = false;
			horizontalMove = true;
		}
		
		Gdx.app.log(TAG, "öèÓãµÄÎ»ÖÃ: " + this.getX() + " : " + this.getY());
	}
}
