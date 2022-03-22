package com.alo.game2;

import com.alo.actors.Ball;
import com.alo.actors.BaseActor;
import com.alo.actors.YoungMan;
import com.alo.game.GameBeta;
import com.badlogic.gdx.Gdx;

public class SweetTime extends GameBeta{
	
	private YoungMan man;
	private Ball ball;
	private BaseActor land;

	@Override
	public void init() {
		
		this.land = new BaseActor(0, 0, this.mainStage);
		this.land.loadTexture("grass.png");
		this.land.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		this.ball = new Ball(150, 150, this.mainStage);
		
		this.man = new YoungMan(20, 20, this.mainStage);
	}

	@Override
	public void update(float deltaTime) {
		
	}
}
