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
import com.zk.tank.components.Explosion.TypeExplosion;
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
	
	private Explosion mExplosion;
	private Explosion mFiredExplosion;
	
	private int lvl;
	private float speed;
	private boolean used;
	
	//=================================================================================//
	//									  CONSTRUCTORS
	//=================================================================================//
	
	public Bullet(int lvl, float speed) {
		this.lvl = lvl;
		this.speed = speed;
		this.mExplosion = new Explosion(TypeExplosion.BULLET_EXPLOSION);
		this.mFiredExplosion = new Explosion(TypeExplosion.FIRED_EXPLOSION);
		this.used = false;
	}

	//=================================================================================//
	//									    METHODS
	//=================================================================================//
	
	@Override
	public void onCreateResource(Engine mEngine, Context context) {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(ASSET_GRAPHICS);
		mAtlas = new BitmapTextureAtlas(mEngine.getTextureManager(), 9, 20);
		mRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAtlas, context, "bullet.png", 0, 0);
		mAtlas.load();
		
		this.mExplosion.onCreateResource(mEngine, context);
		this.mFiredExplosion.onCreateResource(mEngine, context);
	}

	@Override
	public void onCreateScene(Engine mEngine, Scene mScene) {
		mSprite = new Sprite(0, 0, mRegion, mEngine.getVertexBufferObjectManager());
		mSprite.setScale(1.5f);
		mSprite.setVisible(false);
		mScene.getChildByIndex(LAYER_INI).attachChild(mSprite);
		
		this.mExplosion.onCreateScene(mEngine, mScene);
		this.mFiredExplosion.onCreateScene(mEngine, mScene);
	}

	/**
	 * Phương thức mô tả cách thức di chuyển của đối tượng
	 * 
	 * @param direction Hướng di chuyển của đối tượng
	 * @param pX Tọa độ X của Tiled tiếp sau theo hướng di chuyển
	 * @param pY Tọa độ Y của Tiled tiếp sau theo hướng di chuyển
	 * @param mEngine {@link Engine} sử dụng trong Game
	 */
	public void move(final int direction, final float pX, final float pY, final Engine mEngine) {
		
		// đặt đối tượng trong trạng thái đang được sử dụng
		this.used = true;
		
		// Cho phép hiển thị vụ nổ đạn bay khỏi nòng súng
		// và thay đổi điểm bắt đầu của viên đạn tương ứng với hướng hiện tại
		this.showFiredExplosion(direction, pX, pY);
		
		// Cho hình ảnh đối tượng hiển thị lên màn hình
		this.mSprite.setVisible(true);
		
		// đăng ký update dữ liệu cho đối tượng theo thời gian, với chu kỳ xác định bằng thuộc tính speed
		this.mSprite.registerUpdateHandler(new TimerHandler(this.speed, true, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {

				// Kiểm tra tính hợp lệ của bước di chuyển
				boolean isCollision = Collision.isCollision(mEngine, direction, mSprite, TiledMapRender.getRocks());

				// Xử lý di chuyển với các hướng tương ứng
				switch (direction) {

				// hướng lên trên
				case UP:
					
					// Xoay viên đạn theo hướng lên trên
					Bullet.this.mSprite.setRotation(UP);
		
					// kiểm tra tính va chạm và vượt khỏi bản đồ
					if (Bullet.this.mSprite.getY() - 8 <= 0 || isCollision) {
						
						// Hiển thị chuỗi hình ảnh mô tả vụ nổ giữa Bullet với vật cản
						Bullet.this.mExplosion.perform(direction, Bullet.this.mSprite.getX() - 12, Bullet.this.mSprite.getY() - 24);
						
						// ẩn đối tượng trên màn hình
						Bullet.this.mSprite.setVisible(false);
						
						// Bỏ đăng ký update dữ liệu theo chu kỳ thời gian
						Bullet.this.mSprite.unregisterUpdateHandler(pTimerHandler);

						// đặt đối tượng về trạng thái chưa được sử dụng
						Bullet.this.used = false;
						
						// thoát khỏi hàm
						return;
					}
					
					else
						// Di chuyển theo bước 4px
						Bullet.this.mSprite.setPosition(Bullet.this.mSprite.getX(), Bullet.this.mSprite.getY() - SPEED_STEP);
					break;
					
				// Hướng sang phải
				case RIGHT:
					
					// Xoay viên đạn sang hướng bên phải
					Bullet.this.mSprite.setRotation(RIGHT);
		
					// kiểm tra tính va chạm và vượt khỏi bản đồ
					if (Bullet.this.mSprite.getX() + TILED_HEIGHT >= 800 || isCollision) {

						// Hiển thị chuỗi hình ảnh mô tả vụ nổ giữa Bullet với vật cản
						Bullet.this.mExplosion.perform(direction, Bullet.this.mSprite.getX() - 8, Bullet.this.mSprite.getY() - 8);
						
						// ẩn đối tượng trên màn hình
						Bullet.this.mSprite.setVisible(false);
						
						// Bỏ đăng ký update dữ liệu theo chu kỳ thời gian
						Bullet.this.mSprite.unregisterUpdateHandler(pTimerHandler);

						// đặt đối tượng về trạng thái chưa được sử dụng
						Bullet.this.used = false;

						// thoát khỏi hàm
						return;
					} 
					
					else
						// Di chuyển theo bước 4px
						Bullet.this.mSprite.setPosition(Bullet.this.mSprite.getX() + SPEED_STEP, Bullet.this.mSprite.getY());
					break;
					
				// Hướng xuống dưới
				case DOWN:
					
					// Xoay viên đạn hướng xuống dưới
					Bullet.this.mSprite.setRotation(DOWN);
					
					// kiểm tra tính va chạm và vượt khỏi bản đồ
					if (Bullet.this.mSprite.getY() - 8  + TILED_HEIGHT >= 504 || isCollision) {

						// Hiển thị chuỗi hình ảnh mô tả vụ nổ giữa Bullet với vật cản
						Bullet.this.mExplosion.perform(direction, Bullet.this.mSprite.getX() - 12, Bullet.this.mSprite.getY());
						
						// ẩn đối tượng trên màn hình
						Bullet.this.mSprite.setVisible(false);
						
						// Bỏ đăng ký update dữ liệu theo chu kỳ thời gian
						Bullet.this.mSprite.unregisterUpdateHandler(pTimerHandler);

						// đặt đối tượng về trạng thái chưa được sử dụng
						Bullet.this.used = false;

						// thoát khỏi hàm
						return;
					}
					
					else
						// Di chuyển theo bước 4px
						Bullet.this.mSprite.setPosition(Bullet.this.mSprite.getX(), Bullet.this.mSprite.getY() + SPEED_STEP);
					break;
					
				// Hướng sang trái
				case LEFT:
					
					// xoay viên đạn hướng sang trái
					Bullet.this.mSprite.setRotation(LEFT);
		
					// kiểm tra tính va chạm và vượt khỏi bản đồ
					if (Bullet.this.mSprite.getX() <= 48 || isCollision) {

						// Hiển thị chuỗi hình ảnh mô tả vụ nổ giữa Bullet với vật cản
						Bullet.this.mExplosion.perform(direction, Bullet.this.mSprite.getX() - 24, Bullet.this.mSprite.getY() - 8);
						
						// ẩn đối tượng trên màn hình
						Bullet.this.mSprite.setVisible(false);
						
						// Bỏ đăng ký update dữ liệu theo chu kỳ thời gian
						Bullet.this.mSprite.unregisterUpdateHandler(pTimerHandler);

						// thoát khỏi hàm
						Bullet.this.used = false;

						// thoát khỏi hàm
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
	
	/**
	 * Phương thức mô tả cách hiển thị vụ nổ khi viên đạn bay khỏi nòng súng
	 * và điều chỉnh vị trí xuất hiện ban đầu của viên đạn phù hợp với hướng
	 * 
	 * @param direction Hướng quay hiện tại
	 * @param pX Tọa độ X của ô muốn hiển thị
	 * @param pY Tọa độ Y của ô muốn hiển thị
	 */
	public void showFiredExplosion(int direction, float pX, float pY) {

		// Xử lý di chuyển với các hướng tương ứng
		switch (direction) {

		// hướng lên trên
		case UP:
			
			// điều chỉnh lại tọa độ ban đầu của viên đạn
			this.mSprite.setPosition(pX + 12, pY + 40);
			
			// Thể hiện vụ nổ của đạn bay khỏi nòng súng
			this.mFiredExplosion.perform(direction, pX, pY + TILED_HEIGHT / 2);
			
			// thoát khỏi phương thức
			return;
			
		case RIGHT:

			// điều chỉnh lại tọa độ ban đầu của viên đạn
			this.mSprite.setPosition(pX - 40, pY + 6);
			
			// Thể hiện vụ nổ của đạn bay khỏi nòng súng
			this.mFiredExplosion.perform(direction, pX - TILED_WIDTH / 2, pY);
			
			// thoát khỏi phương thức
			return;
			
		case DOWN:

			// điều chỉnh lại tọa độ ban đầu của viên đạn
			this.mSprite.setPosition(pX + 12, pY - 40);
			
			// Thể hiện vụ nổ của đạn bay khỏi nòng súng
			this.mFiredExplosion.perform(direction, pX, pY - TILED_HEIGHT / 2);
			
			// thoát khỏi phương thức
			return;
			
		case LEFT:

			// điều chỉnh lại tọa độ ban đầu của viên đạn
			this.mSprite.setPosition(pX + 40, pY + 6);
			
			// Thể hiện vụ nổ của đạn bay khỏi nòng súng
			this.mFiredExplosion.perform(direction, pX + TILED_WIDTH / 2, pY);
			
			// thoát khỏi phương thức
			return;
		}
	}
	
	/**
	 * Phương thức hỏi đáp trạng thái sử dụng của đối tượng
	 * 
	 * @return nếu đối tượng đang được sử dung (true), ngược lại (false)
	 */
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
