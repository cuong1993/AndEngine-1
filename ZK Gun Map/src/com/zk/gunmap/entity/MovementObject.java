package com.zk.gunmap.entity;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public abstract class MovementObject extends AnimatedSprite {

	public MovementObject(float pX, float pY,
			ITiledTextureRegion pTiledTextureRegion,
			final VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
	}
	
	@Override
	public void onManagedUpdate(float pSecondsElapsed) {
		move();
		super.onManagedUpdate(pSecondsElapsed);
	}
	
	public abstract void move();
}
