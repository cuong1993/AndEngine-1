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

import com.zk.tank.components.Collision;
import com.zk.tank.components.Explosion;
import com.zk.tank.components.TiledMapRender;
import com.zk.tank.constant.GameConstants;
import com.zk.tank.interfaces.IAndEngine;

/**
 * class mô tả 1 đối tượng {@link Bullet}
 * 
 * @author zk (ndviettien.zk@gmail.com)
 * @since 28/11/2012
 */
public class Bullet implements GameConstants, IAndEngine {

	//=================================================================================//
	//										FIELDS
	//=================================================================================//
	private BitmapTextureAtlas mAtlas;
	private TextureRegion mRegion;
	private Sprite mSprite;
	
	private int lvl;
	private float speed;
	private boolean used;

	//=================================================================================//
	//									  CONSTRUCTORS
	//=================================================================================//
	
	public Bullet(int lvl, float speed) {
		this.lvl = lvl;
		this.speed = speed;
		this.used = false;
	}

	//=================================================================================//
	//									    METHODS
	//=================================================================================//
	
	@Override
	public void onCreateResource(Engine mEngine, Context context) {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(ASSET_GRAPHICS);
		mAtlas = new BitmapTextureAtlas(mEngine.getTextureManager(), 5, 24);
		mRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAtlas, context, "bullet.png", 0, 0);
		mAtlas.load();
	}

	@Override
	public void onCreateScene(Engine mEngine, Scene mScene) {
		mSprite = new Sprite(0, 0, mRegion, mEngine.getVertexBufferObjectManager());
		mSprite.setVisible(false);
		mScene.attachChild(mSprite);
	}

	/**
	 * @param direction
	 * @param pX
	 * @param pY
	 * @param mEngine
	 * @param mScene
	 */
	public void move(final int direction, final float pX, final float pY, final Engine mEngine, final Scene mScene) {
		
		this.mSprite.setPosition(pX, pY);
		this.mSprite.setVisible(true);
		
		this.used = true;
		
		this.mSprite.registerUpdateHandler(new TimerHandler(this.speed, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {

				// Kiểm tra tính hợp lệ của bước di chuyển
				boolean isCollision = Collision.isCollision(mEngine, direction, mSprite, TiledMapRender.getRocks());

				// Xử lý di chuyển với các hướng tương ứng
				switch (direction) {

				// hướng lên trên
				case UP:
					Bullet.this.mSprite.setRotation(UP);
					// kiểm tra tính va chạm và vượt khỏi bản đồ
					// nếu vi phạm, hiển thị chuỗi hình ảnh nổ
					if (Bullet.this.mSprite.getY() - 8 <= 0 || isCollision) {
						Bullet.this.used = false;
						return;
						}
					else
						// Di chuyển theo bước 4px
						Bullet.this.mSprite.setPosition(Bullet.this.mSprite.getX(), Bullet.this.mSprite.getY() - SPEED_STEP);
					break;
				case RIGHT:
					Bullet.this.mSprite.setRotation(RIGHT);
					// kiểm tra tính va chạm và vượt khỏi bản đồ
					// nếu vi phạm, hiển thị chuỗi hình ảnh nổ
					if (Bullet.this.mSprite.getX() + TILED_HEIGHT >= 768 || isCollision) {
						Bullet.this.used = false;
						return;
					}
					else
						// Di chuyển theo bước 4px
						Bullet.this.mSprite.setPosition(Bullet.this.mSprite.getX() + SPEED_STEP, Bullet.this.mSprite.getY());
					break;
				case DOWN:
					Bullet.this.mSprite.setRotation(DOWN);
					// kiểm tra tính va chạm và vượt khỏi bản đồ
					// nếu vi phạm, hiển thị chuỗi hình ảnh nổ
					if (Bullet.this.mSprite.getY() - 8  + TILED_HEIGHT >= 480 || isCollision) {
						Bullet.this.used = false;
						return;
					}
					else
						// Di chuyển theo bước 4px
						Bullet.this.mSprite.setPosition(Bullet.this.mSprite.getX(), Bullet.this.mSprite.getY() + SPEED_STEP);
					break;
				case LEFT:
					Bullet.this.mSprite.setRotation(LEFT);
					// kiểm tra tính va chạm và vượt khỏi bản đồ
					// nếu vi phạm, hiển thị chuỗi hình ảnh nổ
					if (Bullet.this.mSprite.getX() <= 48 || isCollision) {
						Bullet.this.used = false;
						return;
					}
					else
						// Di chuyển theo bước 4px
						Bullet.this.mSprite.setPosition(Bullet.this.mSprite.getX() - SPEED_STEP, Bullet.this.mSprite.getY());
					break;
				default:
					break;
				}
			}
		}));
	}

	public void setPoisition(float pX, float pY) {
		this.mSprite.setPosition(pX, pY);
	}
	
	public boolean isUsed() {
		return this.used;
	}

	//=================================================================================//
	//									 GETTER & SETTER
	//=================================================================================//

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public int getLvl() {
		return lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}
}
