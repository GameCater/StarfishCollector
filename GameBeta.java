package com.alo.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
/**
 * 抽象类——项目基本框架
 * @author GameCater
 *
 */
public abstract class GameBeta extends ApplicationAdapter{
	
	protected Stage mainStage;
	protected Stage uiStage; // 管理ui等元素
	
	public void create() {
		this.mainStage = new Stage();
		this.uiStage = new Stage();
		init();
	}
	
	public abstract void init();
	
	public void render() {
		
		float deltaTime = Gdx.graphics.getDeltaTime();
		// 更新舞台中逻辑
		this.mainStage.act(deltaTime);
		this.uiStage.act(deltaTime);
		
		// 用户其他更新逻辑
		update(deltaTime);
		 
		// 绘制舞台前清屏
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// 开始绘制
		this.mainStage.draw();
		this.uiStage.draw();
	}
	
	public abstract void update(float deltaTime);
}
