package com.zk.lastexam.objects;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.zk.lastexam.constant.Direction;

/**
 * class mô tả 1 thực tể là 1 chiếc Tank
 * 
 * @author zk
 * @since 1/11/2012
 */
public abstract class Tank extends AnimatedSprite {
	
	public float velocityX;
	public float velocityY;
	public PhysicsHandler physicsHandler;
	public Direction direction;
	public boolean isDie;
	
	/**
	 * Hàm tạo của class {@link Tank}
	 * 
	 * @param pX Tọa độ X bắt đầu dựng hình
	 * @param pY Tọa độ Y bắt đầu dựng hình
	 * @param pTiledTextureRegion {@link ITextureRegion} của đối tượng Tank
	 * @param pVertexBufferObjectManager {@link VertexBufferObjectManager}
	 */
	public Tank(float pX, float pY, ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
	}

	/**
	 * Phương thức mô tả trạng thái di chuyển của đối tượng
	 */
	public abstract void move();
	
	/**
	 * Phương thức mô tả trạng thái vượt giới hạn vùng biên.
	 * 
	 * @return {@link Direction} bằng NONE nếu đối tượng ra khỏi vùng biên.
	 */
	public abstract Direction outOfBound();
	
	/**
	 * Phương thức mô tả trạng thái bắn của đối tượng
	 */
	public abstract void fire();
	
	/**
	 * Phương thức mô tả trạng thái chết của đối tượng
	 */
	public abstract void die();
}
