package com.alo.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * ���ڼ��ض���
 * @author GameCater
 *
 */
public class AnimationUtils {
	
	private AnimationUtils() {}
	
	/**
	 * �ӷ�ɢ��ͼƬ�ļ��������ɶ���
	 * @param fileNames
	 * @param frameDuration
	 * @param loop
	 * @return
	 */
	public static Animation<TextureRegion> loadAnimationFromFiles(String[] fileNames, 
			float frameDuration, boolean loop){
		
		int fileCount = fileNames.length;
		Array<TextureRegion> textureArray = new Array<>();
		
		for (int i = 0; i < fileCount; i ++) {
			String fileName = fileNames[i];
			Texture texture = new Texture(Gdx.files.internal(fileName));
			texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			TextureRegion tr = new TextureRegion(texture);
			textureArray.add(tr);
		}
		
		Animation<TextureRegion> animation = new Animation<>(frameDuration, textureArray);
		
		if (loop)
			animation.setPlayMode(PlayMode.LOOP);
		else
			animation.setPlayMode(PlayMode.NORMAL);
		
		Gdx.app.log(null, "�ɹ�����");
		
		return animation;
	}
	
	/**
	 * ��һ����ͼƬ���������������ɶ�����֧��4�����ϵ�sheet
	 * @param sheetName
	 * @param rows
	 * @param cols
	 * @param frameDuration
	 * @param loop
	 * @return
	 */
	public static Animation<TextureRegion> loadAnimationFromSheet(String sheetName, 
			int rows, int cols, float frameDuration, boolean loop){
		Texture texture = new Texture(Gdx.files.internal(sheetName));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		int frameWidth = texture.getWidth() / cols;
		int frameHeight = texture.getHeight() / rows;
		
		TextureRegion[][] temp = TextureRegion.split(texture, frameWidth, frameHeight);
		Array<TextureRegion> textureArray = new Array<>();
		
		for (int i = 0; i < rows; i ++)
			for (int j = 0; j < cols; j ++)
				textureArray.add(temp[i][j]);
		
		Animation<TextureRegion> animation = new Animation<>(frameDuration, textureArray);
		
		if (loop)
			animation.setPlayMode(PlayMode.LOOP);
		else
			animation.setPlayMode(PlayMode.NORMAL);
		
		Gdx.app.log(null, "�ɹ�����: " + sheetName);
		
		return animation;
	}
	
	/**
	 * ��Բ���Ҫ������actor
	 * @param fileName
	 * @return
	 */
	public static Animation<TextureRegion> loadTexture(String fileName){
		String[] fileNames = new String[1];
		fileNames[0] = fileName;
		Animation<TextureRegion> anim = AnimationUtils.loadAnimationFromFiles(fileNames, 1, true);
		return anim;
	}
}
