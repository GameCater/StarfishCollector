package com.alo.actors;

import com.alo.utils.AnimationUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class BaseActor extends Actor{
	
	private Animation<TextureRegion> animation;
	private float elapsedTime; // 跟踪动画执行时间
	private boolean animationPaused;
	
	public BaseActor(float x, float y, Stage s) {
		super();
		this.setPosition(x, y);
		s.addActor(this);
		
		this.animation = null;
		this.elapsedTime = 0;
		this.animationPaused = false;
	}
	
	public void setAnimation(Animation<TextureRegion> anim) {
		this.animation = anim;
		TextureRegion tr = anim.getKeyFrame(0); // 获取第一帧
		float w = tr.getRegionWidth(), h = tr.getRegionHeight();
		this.setSize(w, h);
		this.setOrigin(w/2, h/2);
	}
	
	public void setAnimtionPaused(boolean pause) {
		this.animationPaused = pause;
	}
	
	public void act(float deltaTime) {
		super.act(deltaTime);
		if (!this.animationPaused)
			this.elapsedTime += deltaTime;
	}
	
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		Color c = this.getColor();
		batch.setColor(c);
		
		if (this.animation != null && this.isVisible()) {
			batch.draw(this.animation.getKeyFrame(elapsedTime), getX(), getY(),
					getOriginX(), getOriginY(), getWidth(), getHeight(), 
					getScaleX(), getScaleY(), getRotation());
		}
	}
	
	public void setAnimationFromFiles(String[] fileNames, float frameDuration, boolean loop) {
		setAnimation(AnimationUtils.loadAnimationFromFiles(fileNames, frameDuration, loop));
	}
	
	public void setAnimationFromSheet(String sheetName,int rows, int cols, float frameDuration, boolean loop) {
		setAnimation(AnimationUtils.loadAnimationFromSheet(sheetName, rows, cols, frameDuration, loop));
	}
	
	public void loadTexture(String fileName) {
		setAnimation(AnimationUtils.loadTexture(fileName)); 
	}
	
	public boolean isAnimationFinished() {
		return this.animation.isAnimationFinished(elapsedTime);
	}
}
