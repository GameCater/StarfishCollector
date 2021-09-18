package com.alo.game;

import com.alo.objects.ActorBeta;
import com.alo.objects.Shark;
import com.alo.objects.Turtle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class StarfishCollectorBeta extends GameBeta{
	
	private Turtle turtle;
	private ActorBeta starfish;
	private ActorBeta ocean;
	private Shark shark;
	
	private boolean win;
	private int times = 5;
	private float rebornLeftTime = 5.0f;
	private boolean isDead = false;
	
	public void init() {
		
		Pixmap pixmap = this.generatePixmap(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight(), new Color(0, 0, 1, 1));
		this.ocean = new ActorBeta();
		this.ocean.setTexture(new Texture(pixmap));
		
		pixmap = this.generatePixmap(30, 30, new Color(0, 1, 0, 1));
		this.turtle = new Turtle();
		this.turtle.setTexture(new Texture(pixmap));
		this.turtle.setPosition(20, 20);
		
		pixmap = this.generatePixmap(30, 30, new Color(1, 1, 0, 1));
		this.starfish = new ActorBeta();
		this.starfish.setTexture(new Texture(pixmap));
		this.starfish.setPosition(200, 200);
		
		pixmap = this.generatePixmap(30, 30, new Color(1, 1, 1, 1));
		this.shark = new Shark();
		this.shark.setTexture(new Texture(pixmap));
		this.shark.setPosition(-20, 
				MathUtils.random(0, Gdx.graphics.getHeight() - this.shark.getHeight()));
		
		this.mainStage.addActor(ocean);
		this.mainStage.addActor(starfish);
		this.mainStage.addActor(turtle);
		this.mainStage.addActor(shark);
		
		win = false;
	}
	
	private Pixmap generatePixmap(int width, int height, Color color) {
		Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fill();
		return pixmap;
	}
	
	public void update(float deltaTime) {
		if (this.turtle.overlaps(starfish)) {
			this.starfish.remove(); // 演员从舞台移除，但并未从内存清除，只是隐藏了
			times --;
			if (times <= 0) {
				win = true;
				times = 0;
			}
			
			if (!win) {
				float nextX = MathUtils.random(0, 700);
				float nextY = MathUtils.random(0, 500);
				this.starfish.setPosition(nextX, nextY);
				this.mainStage.addActor(starfish);
			}
		}
		
		if (this.turtle.overlaps(shark)) {
			this.turtle.remove();
			isDead = true;
		}
		
		if (isDead) {
			this.rebornLeftTime -= deltaTime;
		}
		if (this.rebornLeftTime <= 0) {
			this.turtle.setPosition(20, 20);
			this.mainStage.addActor(turtle);
			this.rebornLeftTime = 5.0f;
			isDead = false;
		}
	}
}
