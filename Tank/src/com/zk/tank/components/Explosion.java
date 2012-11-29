package com.zk.tank.components;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.content.Context;

import com.zk.tank.constant.GameConstants;
import com.zk.tank.interfaces.IAndEngine;

public class Explosion implements GameConstants, IAndEngine {

	//=================================================================================//
	//									  FIELDS
	//=================================================================================//
	
	private BitmapTextureAtlas mAtlas;
	
	private TiledTextureRegion mTankRegion;
	private AnimatedSprite mTanknSprite;
	
	private TiledTextureRegion mFiredRegion;
	private AnimatedSprite mFiredSprite;
	
	private TiledTextureRegion mBulletRegion;
	private AnimatedSprite mBulletSprite;
	
	private TiledTextureRegion mWonderRegion;
	private AnimatedSprite mWonderSprite;
	
	private int tileX;
	private int tileY;

	//=================================================================================//
	//									CONSTRUCTORS
	//=================================================================================//
	
	@Override
	public void onCreateResource(Engine mEngine, Context context) {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(ASSET_GRAPHICS);
		this.mAtlas = new BitmapTextureAtlas(mEngine.getTextureManager(), 512, 512);
		this.mTankRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mAtlas, context, "tank_explosion.png", 0, 0, 5, 5);
	}

	@Override
	public void onCreateScene(Engine mEngine, Scene mScene) {
		this.mBulletSprite = new AnimatedSprite(0, 0, mBulletRegion, mEngine.getVertexBufferObjectManager());
		this.mFiredSprite = new AnimatedSprite(0, 0, mFiredRegion, mEngine.getVertexBufferObjectManager());
		this.mTanknSprite = new AnimatedSprite(0, 0, mTankRegion, mEngine.getVertexBufferObjectManager());
		this.mWonderSprite = new AnimatedSprite(0, 0, mWonderRegion, mEngine.getVertexBufferObjectManager());
	}

	//=================================================================================//
	//									   METHODS
	//=================================================================================//
	
	public void perform(TypeExplosion type, int direction, float pX, float pY, Scene mScene) {
		
		switch (type) {
		
		case BULLET_EXPLOSION:
			mScene.attachChild(mBulletSprite);
			return;
			
		case FIRED_EXPLOSION:
			return;
			
		case TANK_EXPLOSION:
			return;
			
		case WONDER_EXPLOSION:
			return;
		}
	}
	
	public int getTileX() {
		return tileX;
	}

	public void setTileX(int tileX) {
		this.tileX = tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public void setTileY(int tileY) {
		this.tileY = tileY;
	}

	public enum TypeExplosion {
		BULLET_EXPLOSION,
		FIRED_EXPLOSION,
		TANK_EXPLOSION,
		WONDER_EXPLOSION
	}
}
