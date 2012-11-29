package com.zk.tank.components;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;

import android.content.Context;

import com.zk.tank.constant.GameConstants;
import com.zk.tank.entitys.Player;
import com.zk.tank.interfaces.IAndEngine;

/**
 * Class mô tả nút bắn trên màn hình
 * 
 * @author zk (ndviettien.zk@gmail.com)
 * @since 28/11/2012
 */
public class ShotButton implements GameConstants, IAndEngine {

	//=================================================================================//
	//										FIELDS
	//=================================================================================//
	private BitmapTextureAtlas mAtlas;
	private TextureRegion mRegion;
	private Sprite mSprite;
	
	private Player player;

	//=================================================================================//
	//									  CONSTRUCTORS
	//=================================================================================//
	public ShotButton(Player player) {
		this.player = player;
	}
	//=================================================================================//
	//										METHODS
	//=================================================================================//
	
	@Override
	public void onCreateResource(Engine mEngine, Context context) {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(ASSET_GRAPHICS);
		mAtlas = new BitmapTextureAtlas(mEngine.getTextureManager(), 128, 128);
		mRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAtlas, context, "on_fire_button.png", 0, 0);
		mAtlas.load();
	}

	@Override
	public void onCreateScene(final Engine mEngine, final Scene mScene) {
		this.mSprite = new Sprite(CAMERA_WIDTH - mRegion.getWidth(), CAMERA_HEIGHT - mRegion.getHeight(), 
				mRegion, mEngine.getVertexBufferObjectManager()) {
			
			/*
			 * Phương thức xử lý khi có xự kiện chạm trên đối tượng
			 */
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				int act = pSceneTouchEvent.getAction();
				
				switch (act) {
				
				// Đưa đối tượng Player vào trạng thái bắn nếu chạm xuống hoặc di chuyển trên đối tượng
				case TouchEvent.ACTION_DOWN:
					ShotButton.this.player.fire();
					return true;
				default:
					return true;
				}
			}
		};
		
		mScene.registerTouchArea(mSprite);
		mScene.attachChild(mSprite);
	}
}
