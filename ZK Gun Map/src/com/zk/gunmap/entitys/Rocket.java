package com.zk.gunmap.entitys;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import android.content.Context;
import android.graphics.Color;

import com.zk.gunmap.interfaces.GameConstants;
import com.zk.gunmap.interfaces.IGunMap;

public class Rocket implements IGunMap, GameConstants {
	// Tọa độ dựng thành phần đồ họa
	private int pX;
	private int pY;
	private int mWidth;
	private int mHeight;
	// Lớp đồ họa để dựng đối tượng
	private int mLayer;
	private AnimatedSprite mSprite;
	private TiledTextureRegion mTexture;
	private BitmapTextureAtlas mAtlas;
	// thuộc tính liên quan tới xây dựng thông báo bằng text
	private Font mFont;
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
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("images/");
		mAtlas = new BitmapTextureAtlas(mEngine.getTextureManager(), 128, 128);
		mTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mAtlas, context, "rocket.png", 0, 0, 1, 4);
		mAtlas.load();
		
		FontFactory.setAssetBasePath("fonts/");
		this.mFont = FontFactory.createFromAsset(mEngine.getFontManager(), mEngine.getTextureManager(), 128, 64, TextureOptions.BILINEAR, context.getAssets(), "Plok.ttf", 32, true, Color.BLACK);
		this.mFont.load();
	}
	/*
	 * 
	 */
	@Override
	public void onCreateScene(Engine mEngine, Scene mScene) {
		mSprite = new AnimatedSprite(pX - mWidth / 2, pY - mHeight - 256, mTexture, mEngine.getVertexBufferObjectManager());
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
