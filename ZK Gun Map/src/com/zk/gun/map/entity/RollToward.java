package com.zk.gun.map.entity;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.content.Context;

import com.zk.gun.map.handler.OrientationHandler;
import com.zk.gun.map.interfaces.IGunMap;

/**
 * Class thể hiện đối tượng RollToward chỉ dẫn góc nghiêng của máy
 * 
 * @author zk
 * @since 11/11/2012
 */
public class RollToward implements IGunMap {
	
	private int pX;
	private int pY;
	
	private BitmapTextureAtlas mAtlas;
	private TiledTextureRegion mRegion;
	private AnimatedSprite mSprite;
	
	public RollToward(float cameraWidth, float cameraHeight) {
		this.pX = (int) (cameraWidth / 2);
		this.pY = (int) (cameraHeight / 2);
	}
	
	@Override
	public void onCreateResource(Engine mEngine, Context context) {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("images/");
		this.mAtlas = new BitmapTextureAtlas(mEngine.getTextureManager(), 64, 64);
		this.mRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mAtlas, context, "roll_toward.png", 0, 0,1,1);
		this.mAtlas.load();
	}

	@Override
	public void onCreateScene(Engine mEngine, Scene mScene) {
		this.mSprite = new AnimatedSprite(pX, pY, mRegion, mEngine.getVertexBufferObjectManager()) {
			@Override
			protected void onManagedUpdate(final float pSecondsElapsed) {
				RollToward.this.pY = (int) (240 - OrientationHandler.getValues()[2] * (120 / 90));
				this.setY(pY);
			}
		};
		mScene.attachChild(mSprite);
	}
}
