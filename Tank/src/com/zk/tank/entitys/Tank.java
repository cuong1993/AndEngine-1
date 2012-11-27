package com.zk.tank.entitys;

import java.util.ArrayList;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;

import android.content.Context;
import android.graphics.Rect;

import com.zk.tank.constant.GameConstants;
import com.zk.tank.interfaces.IAndEngine;

/**
 * class mô tả 1 đối tượng {@link Tank}
 * 
 * @author zk
 * @since 26/11/2012
 */
public abstract class Tank implements GameConstants, IAndEngine {
	
	protected BitmapTextureAtlas mAtlas;
	protected TextureRegion mRegion;
	protected Sprite mSprite;
	
	protected int mDirection;
	
	protected int tiledX;
	protected int tiledY;
	
//	private float pX;
//	private float pY;
	
	protected float speed;
	
	protected int bullets;
	protected float shotSpeed;

	//=================================================================================//
	//									CONSTRUCTORS
	//=================================================================================//
	/**
	 * Hàm tạo đối tượng {@link Tank} mô tả sơ lược vị trí, hướng ban đầu
	 * 
	 * @param tiledX Vị trí ô theo chiều ngang ban đầu muốn dựng 
	 * @param tiledY Vị trí ô theo chiều dọc ban đầu muốn dựng
	 * @param direction Hướng ban đầu của đối tượng (lấy từ interface {@link GameConstants}
	 */
	public Tank(int tiledX, int tiledY, int direction) {
		this(tiledX, tiledY, direction, SPEED_SLOW);
	}

	/**
	 * Hàm tạo đối tượng {@link Tank} mô tả sơ lược vị trí, hướng ban đầu
	 * 
	 * @param tiledX Vị trí ô theo chiều ngang ban đầu muốn dựng 
	 * @param tiledY Vị trí ô theo chiều dọc ban đầu muốn dựng
	 * @param direction Hướng ban đầu của đối tượng (lấy từ interface {@link GameConstants}
	 * @param speed Tốc độ chạy của đối tượng
	 */
	public Tank(int tiledX, int tiledY, int direction, float speed) {
		this.tiledX = tiledX;
		this.tiledY = tiledY;
		this.mDirection = direction;
		this.speed = speed;
	}
	
	//=================================================================================//
	//										METHODS
	//=================================================================================//
	/* 
	 * Phương thức khởi tạo tài nguyên đồ họa cần thiết để mô tả đối tượng bằng hình ảnh
	 */
	@Override
	public void onCreateResource(Engine mEngine, Context context) {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(ASSET_GRAPHICS);
		this.mAtlas = new BitmapTextureAtlas(mEngine.getTextureManager(), 32, 32);
		this.mRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAtlas, context, "tank_player.png", 0, 0);
		this.mAtlas.load();
	}
	
	/* 
	 * Phương thức dựng đồ họa đối tượng lên màn hình
	 */
	@Override
	public void onCreateScene(Engine mEngine, Scene mScene) {
		this.mSprite = new Sprite(16 + this.tiledX * TILED_WIDTH, this.tiledY * TILED_WIDTH + 8, this.mRegion, mEngine.getVertexBufferObjectManager());
		this.mSprite.setScale(1.5f);
		mScene.getChildByIndex(LAYER_TANK).attachChild(mSprite);
	}
	
	/**
	 * Phương thức mô tả cách thức di chuyển của đối tượng
	 */
	public void move(final ArrayList<Rect> rocks) {
		
		switch (Tank.this.mDirection) {
		case UP:
			Tank.this.mSprite.setRotation(UP);
			if (Tank.this.mSprite.getY() - 8 <= 0)
				return;
			else
				Tank.this.mSprite.setPosition(Tank.this.mSprite.getX(), Tank.this.mSprite.getY() - SPEED_STEP);
			break;
		case RIGHT:
			Tank.this.mSprite.setRotation(RIGHT);
			if (Tank.this.mSprite.getX() + TILED_HEIGHT + 8 >= 800)
				return;
			else
				Tank.this.mSprite.setPosition(Tank.this.mSprite.getX() + SPEED_STEP, Tank.this.mSprite.getY());
			break;
		case DOWN:
			Tank.this.mSprite.setRotation(DOWN);
			if (Tank.this.mSprite.getY() + TILED_HEIGHT + 8 >= 480)
				return;
			else
				Tank.this.mSprite.setPosition(Tank.this.mSprite.getX(), Tank.this.mSprite.getY() + SPEED_STEP);
			break;
		case LEFT:
			Tank.this.mSprite.setRotation(LEFT);
			if (Tank.this.mSprite.getX() - 8 <= 0)
				return;
			else
				Tank.this.mSprite.setPosition(Tank.this.mSprite.getX() - SPEED_STEP, Tank.this.mSprite.getY());
			break;
		default:
			break;
		}
	}
	
	public void fire() {
		
	}
	public void die() {
		
	}
	
	/**
	 * Phương thức xử lý di chuyển khi hướng di chuyển
	 * của đối tượng {@link Tank} phía trước có vật cản
	 * 
	 * @param rocks Mảng chứa tọa độ của các Tiled mang thuộc tính ROCK
	 * 
	 * @return true nếu phía trước theo hướng đó không bị cản, false nếu ngược lại
	 */
	public boolean collision(ArrayList<Rect> rocks) {
		int left = (int) this.mSprite.getX();
		int top = (int) this.mSprite.getY();
		int right = left + TILED_WIDTH;
		int bottom = top + TILED_HEIGHT;
		
		Rect tank = new Rect(left, top, right, bottom);

		for (Rect rock : rocks) {
			if (rock.contains(tank))
				return false;
		}
		return true;
	}

	//===================================================================//
	//							GETTER & SETTER
	//===================================================================//
	public int getmDirection() {
		return mDirection;
	}

	public void setmDirection(int mDirection) {
		this.mDirection = mDirection;
	}

	public float getmtiledX() {
		return tiledX;
	}

	public void setmtiledX(int tiledX) {
		this.tiledX = tiledX;
	}

	public float getmtiledY() {
		return tiledY;
	}

	public void setmY(int tiledY) {
		this.tiledY = tiledY;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public int getBullets() {
		return bullets;
	}

	public void setBullets(int bullets) {
		this.bullets = bullets;
	}
}
