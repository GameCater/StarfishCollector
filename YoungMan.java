package com.alo.actors;

import com.alo.utils.AnimationUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class YoungMan extends BaseActor {
	
	private Animation runUp = AnimationUtils.loadAnimationFromSheet("s1.png", 4, 4, 0.1f, true, 3);
	private Animation runDown = AnimationUtils.loadAnimationFromSheet("s1.png", 4, 4, 0.1f, true, 0);
	private Animation runRight = AnimationUtils.loadAnimationFromSheet("s1.png", 4, 4, 0.1f, true, 2);
	private Animation runLeft = AnimationUtils.loadAnimationFromSheet("s1.png", 4, 4, 0.1f, true, 1);
	
	public static final String TAG = YoungMan.class.getName();
	
	public YoungMan(float x, float y, Stage s) {
		super(x, y, s);
		
//		this.setAnimationFromSheet("s1.png", 4, 4, 0.1f, true, 2);
		this.setAnimation(runRight);
		
		this.setAcceleration(300);
		this.setMaxSpeed(100);
		this.setDeceleration(400);
		
		this.setBoundaryPolygon(8); // 给演员添加8变形碰撞
	}
	
	public void act(float deltaTime) {
		super.act(deltaTime);
		
		if (Gdx.input.isKeyPressed(Keys.A)) {
			this.accelerateAtAngle(180);
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			this.accelerateAtAngle(0);
		}
		if (Gdx.input.isKeyPressed(Keys.W)) {
			this.accelerateAtAngle(90);
		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
			this.accelerateAtAngle(270);
		}
		
		this.applyPhysics(deltaTime);
		
		this.setAnimtionPaused(!this.isMoving());
		
		if (this.getSpeed() > 0)
			this.setRotation(this.getMotionAngle());
		
		this.boundToWorld();
		
		this.alignCamera();
	}
}
