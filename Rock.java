package com.alo.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Rock extends BaseActor{

	public Rock(float x, float y, Stage s) {
		super(x, y, s);
		this.loadTexture("rock.png");
		this.setBoundaryPolygon(8);
	}
}
