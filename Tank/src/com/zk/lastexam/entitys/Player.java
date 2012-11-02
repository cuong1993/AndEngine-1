package com.zk.lastexam.entitys;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.zk.lastexam.constant.Direction;
import com.zk.lastexam.objects.Tank;

/**
 * class mô tả 1 thực thể là người chơi
 * 
 * @author zk
 * @since 1/11/2012
 */
public class Player extends Tank {

	/**
	 * Hàm tạo của đối tượng Player, mặc định đăng ký {@link PhysicsHandler}
	 * đặt hướng hướng lên trên và trạng thái isDie = false
	 * 
	 * @param pX Tọa độ X bắt đầu dựng hình
	 * @param pY Tọa độ Y bắt đầu dựng hình
	 * @param pTiledTextureRegion {@link ITextureRegion} của đối tượng Tank
	 * @param pVertexBufferObjectManager {@link VertexBufferObjectManager}
	 */
	public Player(float pX, float pY, ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		this.physicsHandler = new PhysicsHandler(this);
		this.registerUpdateHandler(physicsHandler);
		this.direction = Direction.UP;
		this.isDie = false;
	}
	
	/**
	 * Phương thức mô tả trạng thái di chuyển của đối tượng
	 * Hướng di chuyển và góc quay của đối tượng phụ thuộc vào hướng của đối tượng
	 * 
	 */
	@Override
	public void move() {
		this.direction = outOfBound();
		
		if (this.direction == Direction.RIGHT) {
			this.setRotation(90);
			this.physicsHandler.setVelocity(velocityX, 0);
		}
		
		if (this.direction == Direction.DOWN) {
			this.setRotation(180);
			this.physicsHandler.setVelocity(0, velocityY);
		}
		
		if (this.direction == Direction.LEFT) {
			this.setRotation(270);
			this.physicsHandler.setVelocity(velocityX, 0);
		}
		
		if (this.direction == Direction.UP) {
			this.setRotation(0);
			this.physicsHandler.setVelocity(0, velocityY);
		}
		
		if (this.direction == Direction.NONE) {
			this.physicsHandler.setVelocity(0,0);
		}
	}

	/**
	 * Phương thức mô tả trạng thái vượt giới hạn vùng biên.
	 * 
	 * @return {@link Direction} bằng NONE nếu đối tượng ra khỏi vùng biên.
	 */
	@Override
	public Direction outOfBound() {
		if (this.getX() <= 0 && this.direction == Direction.LEFT ||
			this.getX() + this.getWidth() >= 800 && this.direction == Direction.RIGHT ||
			this.getY() <= 0 && this.direction == Direction.UP ||
			this.getY() + this.getHeight() >= 480 && this.direction == Direction.DOWN)
			
			return Direction.NONE;
		else
			return this.direction;
	}

	@Override
	public void fire() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public Direction getDirection() {
		return this.direction;
	}
	
	public void setVelocity(float velocityX, float velocityY) {
		this.velocityX = velocityX;
		this.velocityY = velocityY;
	}
	
	public void setVelocityX(float velocityX) {
		this.velocityX = velocityX;
	}

	public void setVelocityY(float velocityY) {
		this.velocityY = velocityY;
	}
}
