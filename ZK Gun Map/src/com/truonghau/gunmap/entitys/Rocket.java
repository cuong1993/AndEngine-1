package com.truonghau.gunmap.entitys;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import android.content.Context;
import android.graphics.Color;

import com.truonghau.gunmap.interfaces.GameConstants;
import com.truonghau.gunmap.interfaces.IGunMap;
import com.truonghau.gunmap.menus.RocketMenu;

public class Rocket implements IGunMap, GameConstants {
	// Tọa độ dựng thành phần đồ họa
	private int pX;
	private int pY;
	private int mWidth;
	private int mHeight;
	// Lớp đồ họa để dựng đối tượng
	private int mLayer;
	// Thuộc tính liên quan tới xây dựng đối tượng bằng đồ họa
	private AnimatedSprite mSprite;
	private TiledTextureRegion mTexture;
	private BitmapTextureAtlas mAtlas;
	// thuộc tính liên quan tới xây dựng thông báo bằng text
	private Font mFont;
	// Thuộc tính xây dựng menu khi chạm vào đối tượng
	private RocketMenu mMenu;
	/**
	 * Hàm tạo class
	 * 
	 * @param layer Lớp đồ họa được sử dụng để dựng đối tượng
	 * @param cameraWidth Chiều rộng của {@link Camera} game xây dựng
	 * @param cameraHeight Chiều cao của {@link Camera} game xây dựng
	 */
	public Rocket(int layer, float cameraWidth, float cameraHeight) {
		this.mLayer = layer;
		this.pX = (int) (cameraWidth / 2);
		this.pY = (int) cameraHeight;	
		this.mWidth = 128;
		this.mHeight = 128;
	}
	/*
	 * 
	 */
	@Override
	public void onCreateResource(Engine mEngine, Context context) {
		if (this.mLayer == LAYER_USER)
			this.mMenu.onCreateResource(mEngine, context);
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("images/");
		this.mAtlas = new BitmapTextureAtlas(mEngine.getTextureManager(), 128, 128);
		this.mTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mAtlas, context, "rocket.png", 0, 0, 1, 4);
		this.mAtlas.load();
		
		FontFactory.setAssetBasePath("fonts/");
		this.mFont = FontFactory.createFromAsset(mEngine.getFontManager(), mEngine.getTextureManager(), 128, 64, TextureOptions.BILINEAR, context.getAssets(), "Plok.ttf", 32, true, Color.BLACK);
		this.mFont.load();
	}
	/*
	 * 
	 */
	@Override
	public void onCreateScene(final Engine mEngine, final Scene mScene) {
		// Dựng hình Rocket như 1 nút bấm nếu được dựng ở LAYER_USER
		if (this.mLayer == LAYER_USER) {
			mSprite = new AnimatedSprite(10, 480 - mHeight, mTexture, mEngine.getVertexBufferObjectManager()) {
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					int act = pSceneTouchEvent.getAction();
					if (Rocket.this.mLayer == LAYER_USER && act == TouchEvent.ACTION_DOWN) {
						Rocket.this.mMenu.onCreateScene(mEngine, mScene);
					}
					return true;
				}
			};
			mScene.registerTouchArea(mSprite);
		}
		// Dựng hình Rocket như bình thường nếu được dựng ở LAYER_INI
		else if (this.mLayer == LAYER_INI) {
			mSprite = new AnimatedSprite(pX - mWidth / 2, pY - mHeight - 256, mTexture, mEngine.getVertexBufferObjectManager());
		}
		
		mScene.getChildByIndex(mLayer).attachChild(mSprite);
	}

	public int getX() {
		return pX;
	}
	public void setX(int pX) {
		this.pX = pX;
	}
	public int getY() {
		return pY;
	}
	public void setY(int pY) {
		this.pY = pY;
	}
}
