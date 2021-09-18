package com.alo.actors;

import java.util.ArrayList;

import com.alo.utils.AnimationUtils;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class BaseActor extends Actor{
	
	private Animation<TextureRegion> animation;
	private float elapsedTime; // 跟踪动画执行时间
	private boolean animationPaused;
	
	private Vector2 velocityVec; // 速度
	private Vector2 accelerationVec; // 加速度
	private float acceleration;
	private float maxSpeed;
	private float deceleration;
	
	private Polygon boundaryPolygon; // 多边形碰撞检测
	
	private static Rectangle worldBounds;
	
	public BaseActor(float x, float y, Stage s) {
		super();
		this.setPosition(x, y);
		s.addActor(this);
		
		this.animation = null;
		this.elapsedTime = 0;
		this.animationPaused = false;
		
		this.velocityVec = new Vector2(0, 0);
		this.accelerationVec = new Vector2(0,0);
		this.acceleration = 0;
		this.maxSpeed = 1000;
		this.deceleration = 0;
	}
	
	public void setAnimation(Animation<TextureRegion> anim) {
		this.animation = anim;
		TextureRegion tr = anim.getKeyFrame(0); // 获取第一帧
		float w = tr.getRegionWidth(), h = tr.getRegionHeight();
		this.setSize(w, h);
		this.setOrigin(w/2, h/2);
		
		if (this.boundaryPolygon == null)
			this.setBoundaryRectangle();
	}
	
	public void setAnimtionPaused(boolean pause) {
		this.animationPaused = pause;
	}
	
	public void act(float deltaTime) {
		super.act(deltaTime);
		if (!this.animationPaused)
			this.elapsedTime += deltaTime;
	}
	
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		Color c = this.getColor();
		batch.setColor(c);
		
		if (this.animation != null && this.isVisible()) {
			batch.draw(this.animation.getKeyFrame(elapsedTime), getX(), getY(),
					getOriginX(), getOriginY(), getWidth(), getHeight(), 
					getScaleX(), getScaleY(), getRotation());
		}
	}
	
	public void setAnimationFromFiles(String[] fileNames, float frameDuration, boolean loop) {
		setAnimation(AnimationUtils.loadAnimationFromFiles(fileNames, frameDuration, loop));
	}
	
	public void setAnimationFromSheet(String sheetName,int rows, int cols, float frameDuration, boolean loop, int row) {
		setAnimation(AnimationUtils.loadAnimationFromSheet(sheetName, rows, cols, frameDuration, loop, row));
	}
	
	public void loadTexture(String fileName) {
		setAnimation(AnimationUtils.loadTexture(fileName)); 
	}
	
	public boolean isAnimationFinished() {
		return this.animation.isAnimationFinished(elapsedTime);
	}
	
	public void setSpeed(float speed) {
		if (velocityVec.len() == 0) 
			this.velocityVec.set(speed, 0);
		else 
			this.velocityVec.setLength(speed);
	}
	
	public float getSpeed() {
		return this.velocityVec.len();
	}
	
	public void setMotionAngle(float angle) { // 相对于x轴的角度
		this.velocityVec.setAngleDeg(angle);
	}
	
	public float getMotionAngle() {
		return this.velocityVec.angleDeg();
	}
	
	public boolean isMoving() {
		return this.getSpeed() > 0;
	}
	
	public void setAcceleration(float acc) { // 存储加速度大小（不包括方向）
		this.acceleration = acc;
	}
	
	public void accelerateAtAngle(float angle) { // 根据加速度大小和方向更新表示加速度的向量
		this.accelerationVec.add(new Vector2(this.acceleration, 0).setAngleDeg(angle));
	}
	
	public void accelerateForward() { // 朝演员面对的方向加速
		this.accelerateAtAngle(this.getRotation());
	}
	
	public void setMaxSpeed(float ms) {
		this.maxSpeed = ms;
	}
	
	public void setDeceleration(float dec) {
		this.deceleration = dec;
	}
	
	public void applyPhysics(float deltaTime) {
		// 应用加速度更新速度
		this.velocityVec.add(this.accelerationVec.x * deltaTime,
				this.accelerationVec.y * deltaTime);
		
		float speed = this.getSpeed();
		
		// 当不再加速时，就减速
		if (this.accelerationVec.len() == 0)
			speed -= this.deceleration * deltaTime;
		
		// 限制速度的大小
		speed = MathUtils.clamp(speed, 0, this.maxSpeed);
		
		// 更新速度
		this.setSpeed(speed);
		
		// 应用速度
		this.moveBy(this.velocityVec.x * deltaTime, this.velocityVec.y * deltaTime);
		
		// 重置加速度
		this.accelerationVec.set(0, 0);
	}
	
	public void setBoundaryRectangle() {
		float w = getWidth();
		float h = getHeight();
		float[] vertices = {0,0, w,0, w,h, 0,h};
		this.boundaryPolygon = new Polygon(vertices);
	}
	
	public void setBoundaryPolygon(int numSides) { // 生成与矩形(w,h)紧密内嵌的椭圆(多边形)
		float w = getWidth();					
		float h = getHeight();
		
		float[] vertices = new float[2*numSides];
		for (int i = 0; i < numSides; i ++) {
			float angle = i * 6.28f / numSides;
			vertices[2*i] = w/2 * MathUtils.cos(angle) + w/2;
			vertices[2*i + 1] = h/2 * MathUtils.sin(angle) + h/2;
		}
		this.boundaryPolygon = new Polygon(vertices);
	}
	
	public Polygon getBoundaryPolygon() { // 获得当前的碰撞多边形
		this.boundaryPolygon.setPosition(this.getX(), this.getY());
		this.boundaryPolygon.setOrigin(this.getOriginX(), this.getOriginY());
		this.boundaryPolygon.setRotation(this.getRotation());
		this.boundaryPolygon.setScale(this.getScaleX(), this.getScaleY());
		return this.boundaryPolygon;
	}
	
	public boolean overlaps(BaseActor other) {
		Polygon poly1 = this.getBoundaryPolygon();
		Polygon poly2 = other.getBoundaryPolygon();
		
		// 检测多边形的碰撞需大量计算，所以预先判断围绕多边形的矩形是否碰撞了来优化性能
		if (!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle()))
			return false;
			
		return Intersector.overlapConvexPolygons(poly1, poly2);
	}
	
	public void centerAtPosition(float x, float y) { // 把演员中心置于目标位置
		this.setPosition(x - this.getWidth()/2, y - this.getHeight()/2);
	}
	
	public void centerAtActor(BaseActor other) { // 置当前演员于目标演员中心
		this.centerAtPosition(other.getX() + other.getWidth()/2,
				other.getY() + other.getHeight()/2);
	}
	
	public void setOpacity(float opacity) { // 更改演员透明度
		this.getColor().a = opacity;
	}
	
	public Vector2 preventOverlap(BaseActor other) { // 阻止两个演员相交（实现障碍效果）
		Polygon poly1 = this.getBoundaryPolygon();
		Polygon poly2 = other.getBoundaryPolygon();
		
		if (!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle()))
			return null;
		
		MinimumTranslationVector mtv = new MinimumTranslationVector();
		boolean polygonOverlap = Intersector.overlapConvexPolygons(poly1, poly2, mtv);
		
		if (!polygonOverlap)
			return null;
		
		this.moveBy(mtv.normal.x * mtv.depth, mtv.normal.y * mtv.depth);
		return mtv.normal; // 返回移动的方向
	}
	
	public static ArrayList<BaseActor> getList(Stage stage, String className){
		ArrayList<BaseActor> list = new ArrayList<>();
		
		Class<?> theClass = null;
		try {
			theClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		for (Actor actor : stage.getActors()) {
			if (theClass.isInstance(actor))
				list.add((BaseActor)actor);
		}
		return list;
	}
	
	public static int count(Stage stage, String className) {
		return getList(stage, className).size();
	}
	
	
	public static void setWorldBounds(float width, float height) {
		worldBounds = new Rectangle(0, 0, width, height);
	}
	
	public static void setWorldBounds(BaseActor ba) {
		setWorldBounds(ba.getWidth(), ba.getHeight());
	}
	
	public void boundToWorld() {
		if (getX() < 0)
			setX(0);
		if (getX() + this.getWidth() > worldBounds.width)
			setX(worldBounds.width - getWidth());
		if (getY() < 0)
			setY(0);
		if (getY() + this.getHeight() > worldBounds.height)
			setY(worldBounds.height - getHeight());
	}
	
	
	public void alignCamera() {
		Camera cam = this.getStage().getCamera();
		Viewport v = this.getStage().getViewport();
		
		// 定位于玩家中心
		cam.position.set(this.getX() + this.getOriginX(),
				this.getY() + this.getOriginY(), 0);
	
		cam.position.x = MathUtils.clamp(cam.position.x, cam.viewportWidth/2,
				worldBounds.width - cam.viewportWidth/2);
		cam.position.y = MathUtils.clamp(cam.position.y, cam.viewportHeight/2,
				worldBounds.height - cam.viewportHeight/2);
		
		cam.update();
	}
}
