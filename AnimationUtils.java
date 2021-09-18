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
	
	public static final String TAG = AnimationUtils.class.getName();
	
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
		
		Gdx.app.log(TAG, "�ɹ����أ�" + fileNames[0] + "...");
		
		return animation;
	}
	
	/**
	 * ��һ����ͼƬ���������������ɶ���
	 * @param sheetName
	 * @param rows
	 * @param cols
	 * @param frameDuration
	 * @param loop
	 * @return
	 */
	public static Animation<TextureRegion> loadAnimationFromSheet(String sheetName, 
			int rows, int cols, float frameDuration, boolean loop, int row){
		Texture texture = new Texture(Gdx.files.internal(sheetName));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		int frameWidth = texture.getWidth() / cols;
		int frameHeight = texture.getHeight() / rows;
		
		TextureRegion[][] temp = TextureRegion.split(texture, frameWidth, frameHeight);
		Array<TextureRegion> textureArray = new Array<>();
		
		if (row == -1) {
			for (int i = 0; i < rows; i ++)
				for (int j = 0; j < cols; j ++)
					textureArray.add(temp[i][j]);
			}
		else { // ѡ��ĳһ�е�֡����
			for (int i = 0; i < rows; i ++)
				textureArray.add(temp[row][i]);
		}
		
		Animation<TextureRegion> animation = new Animation<>(frameDuration, textureArray);
		
		if (loop)
			animation.setPlayMode(PlayMode.LOOP);
		else
			animation.setPlayMode(PlayMode.NORMAL);
		
		Gdx.app.log(TAG, "�ɹ�����: " + sheetName);
		
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
