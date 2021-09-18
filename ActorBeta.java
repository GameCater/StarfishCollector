package com.alo.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * 扩展演员类，提供纹理以及碰撞检测
 * @author GameCater
 *
 */
public class ActorBeta extends Actor{
	
	private TextureRegion textureRegion;
	private Rectangle rectangle;
	
	public ActorBeta() {
		super();
		this.textureRegion = new TextureRegion();
		this.rectangle = new Rectangle();
	}
	
	public void setTexture(Texture t) {
		this.textureRegion.setRegion(t);
		this.setSize(t.getWidth(), t.getHeight());
		this.rectangle.setSize(t.getWidth(), t.getHeight());
	}
	
	public Rectangle getRectangle() {
		this.rectangle.setPosition(getX(), getY());
		return this.rectangle;
	}
	
	public boolean overlaps(ActorBeta other) {
		return this.getRectangle().overlaps(other.getRectangle());
	}
	
	public void act(float deltaTime) {
		super.act(deltaTime);
	}
	
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		Color c = this.getColor();
		batch.setColor(c.r, c.g, c.b, c.a);
		if (this.isVisible()) {
			batch.draw(this.textureRegion, getX(), getY(), getOriginX(), getOriginY(), 
					getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
		}
	}
}
