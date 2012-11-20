package com.truonghau.gunmap.entitys;

import org.andengine.engine.Engine;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.DrawType;

import android.content.Context;

import com.truonghau.gunmap.interfaces.IGunMap;

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
		this.pX = (cameraWidth - 340) / 2;
		this.pY = (cameraHeight - 256) / 2;
		this.mHealth = 100;
	}
	
	@Override
	public void onCreateResource(Engine mEngine, Context context) {
		mBar = new Rectangle(pX, pY, 32, 256, mEngine.getVertexBufferObjectManager(), DrawType.DYNAMIC);
		mBar.setColor(0.0f, 1.0f, 0.0f, 1.0f);
	}

	@Override
	public void onCreateScene(Engine mEngine, Scene mScene) {
		mScene.getChildByIndex(layer).attachChild(mBar);
		updateHealth(80);
	}
	
	/**
	 * Phương thức thay đổi chiều cao cột healthbar
	 * @param health lượng máu hiện tại
	 */
	public void updateHealth(float health) {
		while (this.mHealth > health) {
			try {
				Thread.sleep(30);
				/* 
				 * Tính độ cao cột máu sau khi thay đổi
				 */
				float healthHeight = (float) (this.mHealth * (256 / 100.0));
				mBar.setY(pY + 256 - healthHeight);
				mBar.setHeight(healthHeight);
				/*
				 * Thay đổi màu của cột theo độ cao thanh máu
				 */
				float red = (256 - healthHeight) / 256;
				float green = healthHeight / 256;
				float blue = 0.0f;
				mBar.setColor(red, green, blue);
				
				this.mHealth--;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (health <= 0)
			this.mHealth = 0;
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
