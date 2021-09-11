package com.alo.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class YoungMan extends BaseActor {
	
	public static final String TAG = YoungMan.class.getName();
	
	public YoungMan(float x, float y, Stage s) {
		super(x, y, s);
		
		this.setAnimationFromSheet("zombie.png", 4, 4, 0.1f, true);
	}
}
