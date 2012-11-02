package com.zk.lastexam.components;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * class mô tả chuỗi hành động nổ khi đạn va tường hoặc Tank
 * 
 * @author z.k.
 * @since 31/10/2012
 */
public class Explosion extends AnimatedSprite  {

	public Explosion(float pX, float pY,
			ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
	}
	
	@Override
	public void onManagedUpdate(float pSecondsElapsed) {
		super.onUpdate(pSecondsElapsed);
		show();
	}
	
	/**
	 * Phương thức thể hiện sự kiện nổ trên màn hình
	 */
	public void show() {
		this.animate(1000);
		this.setVisible(false);
	}
}
