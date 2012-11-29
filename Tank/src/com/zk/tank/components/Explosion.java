package com.zk.tank.components;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.content.Context;

import com.zk.tank.constant.GameConstants;
import com.zk.tank.entitys.Tank;
import com.zk.tank.interfaces.IAndEngine;

/**
 * class mô tả chuỗi hình ảnh sự kiện nổ trên màn hình
 * 
 * @author zk (ndviettien.zk@gmail.com)
 * @since 28/11/2012
 */
public class Explosion implements GameConstants, IAndEngine {

	//=================================================================================//
	//									  FIELDS
	//=================================================================================//
	
	private BitmapTextureAtlas mAtlas;
	
	private TiledTextureRegion mRegion;
	private AnimatedSprite mSprite;
	
	private int tileX;
	private int tileY;

	private TypeExplosion type;
	private boolean used;

	//=================================================================================//
	//									 CONSTRUCTORS
	//=================================================================================//
	
	public Explosion(TypeExplosion type) {
		this.type = type;
		this.used = false;
	}

	//=================================================================================//
	//									   METHODS
	//=================================================================================//

	@Override
	public void onCreateResource(Engine mEngine, Context context) {
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(ASSET_GRAPHICS);
		this.mAtlas = new BitmapTextureAtlas(mEngine.getTextureManager(), 128, 128);
		
		// Tạo TextureRegion tương ứng với kiểu của đối tượng
		switch (this.type) {
		
		// Kiểu nổ của đạn va chạm với đường biên hoặc vật cản
		case BULLET_EXPLOSION:
		this.mRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mAtlas, context, "bullet_explosion", 0, 0, 4, 4);
			break;
			
		// Kiểu nổ của đạn bắn khỏi nòng pháo
		case FIRED_EXPLOSION:
		this.mRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mAtlas, context, "fired_explosion", 0, 0, 4, 4);
			break;
			
		// Kiểu nổ của đạn va chạm với đối tượng Tank
		case TANK_EXPLOSION:
		this.mRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mAtlas, context, "tank_explosion.png", 0, 0, 4, 4);
			break;
			
		// Kiểu nổ của đạn va chạm với tượng chiến thắng
		case WONDER_EXPLOSION:
		this.mRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mAtlas, context, "wonder_explosion", 0, 0, 4, 4);
			break;
			
		default:
			break;
		}
		this.mAtlas.load();
	}

	@Override
	public void onCreateScene(Engine mEngine, Scene mScene) {

		this.mSprite = new AnimatedSprite(0, 0, mRegion, mEngine.getVertexBufferObjectManager());
		this.mSprite.setScale(1.5f);
		this.mSprite.setVisible(false);
		mScene.getChildByIndex(LAYER_TANK).attachChild(this.mSprite);
	}

	/**
	 * Phương thức mô tả vụ nổ bằng hình ảnh
	 * 
	 * @param type kiểu {@link TypeExplosion} mà vụ nổ mô tả 
	 * @param pX Tọa độ X của vị trí cần mô tả
	 * @param pY Tọa độ Y của vị trí cần mô tả
	 * @param mScene {@link Scene} sử dụng dể dựng hình ảnh lên
	 */
	public void perform(float pX, float pY) {
		this.mSprite.setPosition(pX, pY);
		this.mSprite.setVisible(true);
		this.used = true;
		this.mSprite.animate(16, false);
		this.mSprite.setVisible(false);
	}
	
	public boolean isUsed() {
		return this.used;
	}

	//=================================================================================//
	//									GETTER & SETTER
	//=================================================================================//
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

	//=================================================================================//
	//									   EXTENDS
	//=================================================================================//
	/**
	 * Enum mô tả các kiểu vụ nổ trong game
	 * 
	 * @author zk
	 * @since 28/11/2012
	 */
	public enum TypeExplosion {
		/**
		 * Kiểu nổ của đạn va chạm với đường biên hoặc vật cản
		 */
		BULLET_EXPLOSION,
		/**
		 * Kiểu nổ của đạn bắn khỏi nòng pháo
		 */
		FIRED_EXPLOSION,
		/**
		 * Kiểu nổ của đạn va chạm với đối tượng {@link Tank}
		 */
		TANK_EXPLOSION,
		/**
		 * Kiểu nổ của đạn va chạm với tượng chiến thắng
		 */
		WONDER_EXPLOSION
	}
}
