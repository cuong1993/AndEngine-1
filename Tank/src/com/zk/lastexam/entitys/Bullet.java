package com.zk.lastexam.entitys;

import org.andengine.audio.sound.Sound;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.zk.lastexam.components.Explosion;
import com.zk.lastexam.constant.Direction;

public class Bullet extends Sprite {

	private Explosion graphicEffect;
	private Sound soundEffect;
	private Direction direction;
	private PhysicsHandler physicsHandler;
	private float velocityX;
	private float velocityY;
	
	public Bullet(float pX, float pY, float pWidth, float pHeight,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, ITiledTextureRegion pTiledTextureRegion) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager);
		graphicEffect = new Explosion(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		this.physicsHandler = new PhysicsHandler(this);
		this.registerUpdateHandler(physicsHandler);
	}
	
	public void move() {

		if (this.direction == Direction.RIGHT) {
			this.physicsHandler.setVelocity(velocityX, 0);
		}
		
		if (this.direction == Direction.DOWN) {
			this.physicsHandler.setVelocity(0, velocityY);
		}
		
		if (this.direction == Direction.LEFT) {
			this.physicsHandler.setVelocity(velocityX, 0);
		}
		
		if (this.direction == Direction.UP) {
			this.physicsHandler.setVelocity(0, velocityY);
		}
	}
	
	public void explosion() {
		graphicEffect.show();
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
