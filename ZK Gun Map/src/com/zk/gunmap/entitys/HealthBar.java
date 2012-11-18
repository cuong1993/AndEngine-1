package com.zk.gunmap.entitys;

import org.andengine.engine.Engine;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;

import android.content.Context;

import com.zk.gunmap.interfaces.IGunMap;

/**
 * class mô tả thanh máu trên màn hình
 * @author zk
 * @since 18/11/2012
 */
public class HealthBar implements IGunMap {
	// Thuộc tính sử dụng để tính toán vị trí dựng hình
	private float pX;
	private float pY;
	private int layer;
	// Thuộc tính xây dựng thanh máu
	private float mHealth;
	private Rectangle mBar;	
	/**
	 * Hàm tạo
	 * 
	 * @param layer Lớp đồ họa dùng để dựng hình
	 * @param cameraWidth Chiều rộng của {@link Camera} sử dụng trong game
	 * @param cameraHeight Chiều cao của {@link Camera} sử dụng trong game
	 */
	public HealthBar (int layer, float cameraWidth, float cameraHeight) {
		this.layer = layer;
		this.pX = (cameraWidth - 300) / 2;
		this.pY = (cameraHeight - 256) / 2;
		this.mHealth = 100;
	}
	
	@Override
	public void onCreateResource(Engine mEngine, Context context) {
	}

	@Override
	public void onCreateScene(Engine mEngine, Scene mScene) {
		mBar = new Rectangle(pX, pY, 32, 256, mEngine.getVertexBufferObjectManager());
		mBar.setColor(0.0f, 1.0f, 0.0f, 0.0f);
		mScene.getChildByIndex(layer).attachChild(mBar);
	}
	
	/**
	 * Phương thức thay đổi chiều cao cột healthbar
	 * @param health lượng máu hiện tại
	 */
	public void updateHealth(float health) {
		if (health <= 0)
			this.mHealth = 0;
		else
			this.mHealth = health;
		/* 
		 * Tính độ cao cột máu sau khi thay đổi
		 */
		float healthHeight = this.mHealth * (256 / 100);
		mBar.setY(pY + healthHeight);
		mBar.setHeight(healthHeight);
		/*
		 * Thay đổi màu của cột theo độ cao thanh máu
		 */
		float red = 256 - healthHeight;
		float green = healthHeight;
		float blue = 0.0f;
		mBar.setColor(red, green, blue, 0.0f);
	}

	public void setX(float pX) {
		this.pX = pX;
	}
	
	public float getX() {
		return this.pX;
	}
	
	public void setY(float pY) {
		this.pY = pY;
	}
	
	public float getY() {
		return this.pY;
	}
	
	public float getHealth() {
		return this.mHealth;
	}
}
