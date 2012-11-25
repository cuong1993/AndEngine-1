package com.zk.andengine.test;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;

import android.content.Context;

/**
 * Class mô tả nút bắn
 * 
 * @author zk
 * @since 9/11/2012
 */
public class ShotButton {
	
	private int pX;
	private int pY;
	
	private BitmapTextureAtlas mAtlas;
	private TextureRegion mRegion;
	private Sprite mSprite;
	
	public ShotButton(float cameraHeight) {
		this.pX = 0;
		this.pY = (int) (cameraHeight / 2);
	}

	public void onCreateResource(Engine mEngine, Context context) {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("images/");
		this.mAtlas = new BitmapTextureAtlas(mEngine.getTextureManager(), 128, 128);
		this.mRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAtlas, context, "on_fire.png", 0, 0);
		this.mAtlas.load();
	}

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
		
		this.mSprite.registerUpdateHandler(new TimerHandler(0.1f, true, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				ShotButton.this.pX += 8;
				ShotButton.this.mSprite.setX(ShotButton.this.pX);
			}
		}));
		mScene.attachChild(mSprite);
		mScene.registerTouchArea(mSprite);
	}
}
