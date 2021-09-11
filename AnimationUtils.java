package com.alo.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * 用于加载动画
 * @author GameCater
 *
 */
public class AnimationUtils {
	
	private AnimationUtils() {}
	
	/**
	 * 从分散的图片文件加载生成动画
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
		
		Gdx.app.log(null, "成功加载");
		
		return animation;
	}
	
	/**
	 * 从一整张图片（类似纹理集）生成动画，支持4行以上的sheet
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
		
		Gdx.app.log(null, "成功加载: " + sheetName);
		
		return animation;
	}
	
	/**
	 * 针对不需要动画的actor
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
