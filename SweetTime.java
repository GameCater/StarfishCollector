package com.alo.game2;

import com.alo.actors.Missile;
import com.alo.actors.BaseActor;
import com.alo.actors.Boom;
import com.alo.actors.Rock;
import com.alo.actors.YoungMan;
import com.alo.game.GameBeta;
import com.badlogic.gdx.Gdx;

public class SweetTime extends GameBeta{
	
	private YoungMan man;
	private BaseActor land;
	
	private Boom boom;
	
	private Rock rock;

	@Override
	public void init() {
		
		this.land = new BaseActor(0, 0, this.mainStage);
		this.land.loadTexture("grass.png");
		this.land.setSize(Gdx.graphics.getWidth() + 400, Gdx.graphics.getHeight() + 300);
		BaseActor.setWorldBounds(land);
		
		new Missile(150, 150, this.mainStage);
		new Missile(400, 400, this.mainStage);
		new Missile(200, 350, this.mainStage);
		
		this.man = new YoungMan(20, 20, this.mainStage);
		
		this.rock = new Rock(200,20, this.mainStage);
	}

	@Override
	public void update(float deltaTime) {
		
		this.man.preventOverlap(rock); // …Ë÷√’œ∞≠
		
		for (BaseActor object : BaseActor.getList(mainStage, "com.alo.actors.Missile")) {
			Missile missile = (Missile)object;
			if (man.overlaps(missile) && !missile.isCollected()) {
				missile.collect();
				this.boom = new Boom(0, 0, mainStage);
				boom.centerAtActor(missile);
				boom.setOpacity(0.85f);
			}
		}
	}
}
