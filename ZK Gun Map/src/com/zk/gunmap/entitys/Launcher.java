package com.zk.gunmap.entitys;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;

import android.content.Context;

import com.zk.gunmap.interfaces.GameConstants;
import com.zk.gunmap.interfaces.IGunMap;

/**
 * class mô tả bệ phóng trên màn hình
 * 
 * @author z.k.
 * @since 17/11/2012
 */
public class Launcher implements IGunMap, GameConstants {
	// Tọa độ dựng thành phần đồ họa
	private int pX;
	private int pY;
	private int mWidth;
	private int mHeight;
	// Lớp đồ họa để dựng đối tượng
	private int mLayer;
	// Các thuộc tính liên quan đến việc xây dựng phần đồ họa
	private BitmapTextureAtlas mAtlas;
	private TextureRegion mRegion;
	private Sprite mSprite;
	/**
	 * Hàm tạo class
	 * 
	 * @param layer Lớp đồ họa được sử dụng để dựng đối tượng
	 * @param cameraWidth Chiều rộng của {@link Camera} game xây dựng
	 * @param cameraHeight Chiều cao của {@link Camera} game xây dựng
	 */
	public Launcher(int layer, float cameraWidth, float cameraHeight) {
		this.mLayer = layer;
		this.pX = (int) (cameraWidth / 2);
		this.pY = (int) cameraHeight;	
		this.mWidth = 128;
		this.mHeight = 256;
	}
	/*
	 * Phương thức khởi tạo đối tượng đồ họa cho class
	 */
	@Override
	public void onCreateResource(Engine mEngine, Context context) {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("images/");
		mAtlas = new BitmapTextureAtlas(mEngine.getTextureManager(), 128, 256);
		mRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAtlas, context, "launcher.png", 0, 0);
		mAtlas.load();
	}
	/*
	 * Phương thức đưa đối tượng đồ họa lên Scene
	 */
	@Override
	public void onCreateScene(Engine mEngine, Scene mScene) {
		mSprite = new Sprite(this.pX - this.mWidth / 2, this.pY - this.mHeight / 2, mRegion, mEngine.getVertexBufferObjectManager());
		mScene.getChildByIndex(mLayer).attachChild(mSprite);
	}
}
