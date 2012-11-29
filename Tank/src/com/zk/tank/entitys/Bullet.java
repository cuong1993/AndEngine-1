package com.zk.tank.entitys;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;

import android.content.Context;

import com.zk.tank.constant.GameConstants;
import com.zk.tank.interfaces.IAndEngine;

/**
 * class mô tả 1 đối tượng {@link Bullet}
 * 
 * @author zk
 * @since 28/11/2012
 */
public class Bullet implements GameConstants, IAndEngine {

	//=================================================================================//
	//										FIELDS
	//=================================================================================//
	private BitmapTextureAtlas mAtlas;
	private TextureRegion mRegion;
	private Sprite mSprite;
	
	private float speed;

	//=================================================================================//
	//									  CONSTRUCTORS
	//=================================================================================//

	//=================================================================================//
	//									    METHODS
	//=================================================================================//
	@Override
	public void onCreateResource(Engine mEngine, Context context) {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(ASSET_GRAPHICS);
		mAtlas = new BitmapTextureAtlas(mEngine.getTextureManager(), 8, 8);
		mRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAtlas, context, "bullet.png", 0, 0);
		mAtlas.load();
	}

	@Override
	public void onCreateScene(Engine mEngine, Scene mScene) {
		mSprite = new Sprite(0, 0, mRegion, mEngine.getVertexBufferObjectManager());
	}

	public void move(int direction, float pX, float pY, Scene mScene) {
		this.mSprite.registerUpdateHandler(new TimerHandler(this.speed, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				// TODO Auto-generated method stub
				
			}
		}));
		this.mSprite.setPosition(pX, pY);
		mScene.attachChild(mSprite);
	}
}
