package com.alo.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
/**
 * �����ࡪ����Ŀ�������
 * @author GameCater
 *
 */
public abstract class GameBeta extends ApplicationAdapter{
	
	protected Stage mainStage;
	protected Stage uiStage; // ����ui��Ԫ��
	
	public void create() {
		this.mainStage = new Stage();
		this.uiStage = new Stage();
		init();
	}
	
	public abstract void init();
	
	public void render() {
		
		float deltaTime = Gdx.graphics.getDeltaTime();
		// ������̨���߼�
		this.mainStage.act(deltaTime);
		this.uiStage.act(deltaTime);
		
		// �û����������߼�
		update(deltaTime);
		 
		// ������̨ǰ����
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// ��ʼ����
		this.mainStage.draw();
		this.uiStage.draw();
	}
	
	public abstract void update(float deltaTime);
}
