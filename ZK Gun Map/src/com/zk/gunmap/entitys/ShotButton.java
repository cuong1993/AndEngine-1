package com.zk.gunmap.entitys;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;

import android.content.Context;

import com.zk.gunmap.interfaces.IGunMap;

/**
 * Class mô tả nút bắn
 * 
 * @author zk
 * @since 9/11/2012
 */
public class ShotButton implements IGunMap {
	
	private int pX;
	private int pY;
	
	private BitmapTextureAtlas mAtlas;
	private TextureRegion mRegion;
	private Sprite mSprite;
	
	public ShotButton(float cameraWidth) {
		this.pX = (int) (cameraWidth - 120);
		this.pY = 0;
	}

	@Override
	public void onCreateResource(Engine mEngine, Context context) {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("images/");
		this.mAtlas = new BitmapTextureAtlas(mEngine.getTextureManager(), 128, 128);
		this.mRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAtlas, context, "on_fire.png", 0, 0);
		this.mAtlas.load();
	}

	@Override
	public void onCreateScene(Engine mEngine, Scene mScene) {
		this.mSprite = new Sprite(pX, pY, mRegion, mEngine.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				int act = pSceneTouchEvent.getAction();
				switch (act) {
				case TouchEvent.ACTION_DOWN:
					this.setAlpha(128);
					break;
				case TouchEvent.ACTION_UP:
					this.setAlpha(1.0f);
					break;
				}
				return true;
			}
		};
		mScene.attachChild(mSprite);
		mScene.registerTouchArea(mSprite);
	}
}
