package com.zk.gunmap.entity;

import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.zk.gunmap.handler.OrientationHandler;

/**
 * Class mô tả đối tượng chỉ dẫn phương vị
 * 
 * @author zk
 * @sine 29/10/2012
 * 
 */
public class Azimuth extends MovementObject {
	OrientationHandler orientation;
	
	/**
	 * 
	 * @param pX
	 * @param pY
	 * @param pTiledTextureRegion
	 * @param pTiledSpriteVertexBufferObject
	 */
	public Azimuth(float pX, float pY, TiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
	}

	
	@Override
	public void move() {
		float pX = 100 + (OrientationHandler.getValues()[0] * 60 / 36);
		this.setPosition(pX, 2);
	}
}