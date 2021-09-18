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
	private float elapsedTime; // ���ٶ���ִ��ʱ��
	private boolean animationPaused;
	
	private Vector2 velocityVec; // �ٶ�
	private Vector2 accelerationVec; // ���ٶ�
	private float acceleration;
	private float maxSpeed;
	private float deceleration;
	
	private Polygon boundaryPolygon; // �������ײ���
	
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
		TextureRegion tr = anim.getKeyFrame(0); // ��ȡ��һ֡
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
	
	public void setMotionAngle(float angle) { // �����x��ĽǶ�
		this.velocityVec.setAngleDeg(angle);
	}
	
	public float getMotionAngle() {
		return this.velocityVec.angleDeg();
	}
	
	public boolean isMoving() {
		return this.getSpeed() > 0;
	}
	
	public void setAcceleration(float acc) { // �洢���ٶȴ�С������������
		this.acceleration = acc;
	}
	
	public void accelerateAtAngle(float angle) { // ���ݼ��ٶȴ�С�ͷ�����±�ʾ���ٶȵ�����
		this.accelerationVec.add(new Vector2(this.acceleration, 0).setAngleDeg(angle));
	}
	
	public void accelerateForward() { // ����Ա��Եķ������
		this.accelerateAtAngle(this.getRotation());
	}
	
	public void setMaxSpeed(float ms) {
		this.maxSpeed = ms;
	}
	
	public void setDeceleration(float dec) {
		this.deceleration = dec;
	}
	
	public void applyPhysics(float deltaTime) {
		// Ӧ�ü��ٶȸ����ٶ�
		this.velocityVec.add(this.accelerationVec.x * deltaTime,
				this.accelerationVec.y * deltaTime);
		
		float speed = this.getSpeed();
		
		// �����ټ���ʱ���ͼ���
		if (this.accelerationVec.len() == 0)
			speed -= this.deceleration * deltaTime;
		
		// �����ٶȵĴ�С
		speed = MathUtils.clamp(speed, 0, this.maxSpeed);
		
		// �����ٶ�
		this.setSpeed(speed);
		
		// Ӧ���ٶ�
		this.moveBy(this.velocityVec.x * deltaTime, this.velocityVec.y * deltaTime);
		
		// ���ü��ٶ�
		this.accelerationVec.set(0, 0);
	}
	
	public void setBoundaryRectangle() {
		float w = getWidth();
		float h = getHeight();
		float[] vertices = {0,0, w,0, w,h, 0,h};
		this.boundaryPolygon = new Polygon(vertices);
	}
	
	public void setBoundaryPolygon(int numSides) { // ���������(w,h)������Ƕ����Բ(�����)
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
	
	public Polygon getBoundaryPolygon() { // ��õ�ǰ����ײ�����
		this.boundaryPolygon.setPosition(this.getX(), this.getY());
		this.boundaryPolygon.setOrigin(this.getOriginX(), this.getOriginY());
		this.boundaryPolygon.setRotation(this.getRotation());
		this.boundaryPolygon.setScale(this.getScaleX(), this.getScaleY());
		return this.boundaryPolygon;
	}
	
	public boolean overlaps(BaseActor other) {
		Polygon poly1 = this.getBoundaryPolygon();
		Polygon poly2 = other.getBoundaryPolygon();
		
		// ������ε���ײ��������㣬����Ԥ���ж�Χ�ƶ���εľ����Ƿ���ײ�����Ż�����
		if (!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle()))
			return false;
			
		return Intersector.overlapConvexPolygons(poly1, poly2);
	}
	
	public void centerAtPosition(float x, float y) { // ����Ա��������Ŀ��λ��
		this.setPosition(x - this.getWidth()/2, y - this.getHeight()/2);
	}
	
	public void centerAtActor(BaseActor other) { // �õ�ǰ��Ա��Ŀ����Ա����
		this.centerAtPosition(other.getX() + other.getWidth()/2,
				other.getY() + other.getHeight()/2);
	}
	
	public void setOpacity(float opacity) { // ������Ա͸����
		this.getColor().a = opacity;
	}
	
	public Vector2 preventOverlap(BaseActor other) { // ��ֹ������Ա�ཻ��ʵ���ϰ�Ч����
		Polygon poly1 = this.getBoundaryPolygon();
		Polygon poly2 = other.getBoundaryPolygon();
		
		if (!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle()))
			return null;
		
		MinimumTranslationVector mtv = new MinimumTranslationVector();
		boolean polygonOverlap = Intersector.overlapConvexPolygons(poly1, poly2, mtv);
		
		if (!polygonOverlap)
			return null;
		
		this.moveBy(mtv.normal.x * mtv.depth, mtv.normal.y * mtv.depth);
		return mtv.normal; // �����ƶ��ķ���
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
		
		// ��λ���������
		cam.position.set(this.getX() + this.getOriginX(),
				this.getY() + this.getOriginY(), 0);
	
		cam.position.x = MathUtils.clamp(cam.position.x, cam.viewportWidth/2,
				worldBounds.width - cam.viewportWidth/2);
		cam.position.y = MathUtils.clamp(cam.position.y, cam.viewportHeight/2,
				worldBounds.height - cam.viewportHeight/2);
		
		cam.update();
	}
}
