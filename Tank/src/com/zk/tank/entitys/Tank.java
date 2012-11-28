package com.zk.tank.entitys;

import java.util.ArrayList;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;

import android.content.Context;

import com.zk.tank.components.Collision;
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
	protected int cDirection;
	
	protected int tiledX;
	protected int tiledY;
	
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
		this.tiledX = tiledX + 1;
		this.tiledY = tiledY;
		this.mDirection = direction;
		this.speed = speed;
	}
	
	//=================================================================================//
	//										METHODS
	//=================================================================================//
	/**
	 * Phương thức khởi tạo tài nguyên đồ họa cần thiết để mô tả đối tượng bằng hình ảnh
	 * 
	 * @param mEngine {@link Enigne} sử dụng trong Game
	 * @param context {@link Context} của Activity trong Game
	 * 
	 */
	@Override
	public void onCreateResource(Engine mEngine, Context context) {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(ASSET_GRAPHICS);
		this.mAtlas = new BitmapTextureAtlas(mEngine.getTextureManager(), 32, 32);
		this.mRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAtlas, context, "tank_player.png", 0, 0);
		this.mAtlas.load();
	}
	
	/**
	 * Phương thức dựng đồ họa đối tượng lên màn hình
	 * 
	 * @param mEngine {@link Enigne} sử dụng trong Game
	 * @param mScene {@link Scene} sử dụng trong Game
	 */
	@Override
	public void onCreateScene(Engine mEngine, Scene mScene) {
		this.mSprite = new Sprite(this.tiledX * TILED_WIDTH, this.tiledY * TILED_WIDTH + 8, this.mRegion, mEngine.getVertexBufferObjectManager());
		this.mSprite.setScale(1.5f);
		mScene.getChildByIndex(LAYER_TANK).attachChild(mSprite);
	}
	
	/**
	 * Phương thức mô tả cách thức di chuyển của đối tượng
	 */
	public void move(final Engine mEngine, final ArrayList<Rectangle> rocks) {
		this.mSprite.registerUpdateHandler(new TimerHandler(this.speed, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {

				// Kiểm tra tính hợp lệ của bước di chuyển
				boolean isCollision = Collision.isCollision(mEngine, mDirection, mSprite, rocks);

				// Xử lý di chuyển với các hướng tương ứng
				switch (Tank.this.mDirection) {

				// hướng lên trên
				case UP:
					Tank.this.mSprite.setRotation(UP);
					// kiểm tra 
					if (Tank.this.mSprite.getY() - 8 <= 0 || isCollision)
						return;
					else
						Tank.this.mSprite.setPosition(Tank.this.mSprite.getX(), Tank.this.mSprite.getY() - SPEED_STEP);
					break;
				case RIGHT:
					Tank.this.mSprite.setRotation(RIGHT);
					if (Tank.this.mSprite.getX() + TILED_HEIGHT >= 768 || isCollision)
						return;
					else
						Tank.this.mSprite.setPosition(Tank.this.mSprite.getX() + SPEED_STEP, Tank.this.mSprite.getY());
					break;
				case DOWN:
					Tank.this.mSprite.setRotation(DOWN);
					if (Tank.this.mSprite.getY() - 8  + TILED_HEIGHT >= 480 || isCollision)
						return;
					else
						Tank.this.mSprite.setPosition(Tank.this.mSprite.getX(), Tank.this.mSprite.getY() + SPEED_STEP);
					break;
				case LEFT:
					Tank.this.mSprite.setRotation(LEFT);
					if (Tank.this.mSprite.getX() <= 48 || isCollision)
						return;
					else
						Tank.this.mSprite.setPosition(Tank.this.mSprite.getX() - SPEED_STEP, Tank.this.mSprite.getY());
					break;
				default:
					break;
				}
			}
		}));
	}
	
	/**
	 * Phương thức mô tả đối tượng ợ trạng thái bắn
	 */
	public void fire() {
		
	}
	
	/**
	 * Phương thức mô tả đối tượng ở trạng thái chết
	 */
	public void die() {
		
	}
	
	/**
	 * Phương thức xử lý đối tượng khi chuyển hướng mà chưa vào vị trí có thể đổi
	 * thì tiếp tục di chuyển theo hướng cũ cho tới khi tới tọa độ thích hợp
	 */
	public void keepMoving() {
		
	}
	
	/**
	 * Phương thức mô tả đối tượng trong trạng thái hồi sinh
	 * 
	 * @param tiledX Vị trí ô theo chiều ngang muốn hồi sinh 
	 * @param tiledY Vị trí ô theo chiều dọc muốn hồi sinh
	 */
	public void respawm(int tiledX, int tileY) {
		
	}

	//===================================================================//
	//							GETTER & SETTER
	//===================================================================//
	public int getmDirection() {
		return mDirection;
	}

	public void setmDirection(int mDirection) {
		this.cDirection = this.mDirection;
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
