package com.zk.gun.map.entity;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLES20;

import com.zk.gun.map.handler.OrientationHandler;
import com.zk.gun.map.interfaces.IGunMap;

/**
 * Class thể hiện đối tượng RollToward chỉ dẫn góc nghiêng của máy
 * 
 * @author zk
 * @since 11/11/2012
 */
public class RollToward implements IGunMap {
	// Tọa độ dựng thành phần đồ họa
	private int pX;
	private int pY;
	// Các trường liên quan đến việc xây dựng phần đồ họa
	private BitmapTextureAtlas mAtlas;
	private TiledTextureRegion mRegion;
	private AnimatedSprite mSprite;
	// Trường liên quan tới xây dựng thông báo bằng text
	private Font mFont;
	private Text mText;
	
	/**
	 * Hàm tạo class
	 * 
	 * @param cameraWidth Chiều rộng của {@link Camera} game xây dựng
	 * @param cameraHeight Chiều cao của {@link Camera} game xây dựng
	 */
	public RollToward(float cameraWidth, float cameraHeight) {
		this.pX = (int) (cameraWidth / 2);
		this.pY = (int) (cameraHeight / 2);
	}
	
	/*
	 * Phương thức khởi tạo đối tượng đồ họa cho class
	 */
	@Override
	public void onCreateResource(Engine mEngine, Context context) {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("images/");
		this.mAtlas = new BitmapTextureAtlas(mEngine.getTextureManager(), 64, 64);
		this.mRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mAtlas, context, "roll_toward.png", 0, 0,1,1);
		this.mAtlas.load();
		
		FontFactory.setAssetBasePath("fonts/");
		this.mFont = FontFactory.createFromAsset(mEngine.getFontManager(), mEngine.getTextureManager(), 512, 512, TextureOptions.BILINEAR, context.getAssets(), "Plok.ttf", 32, true, Color.WHITE);
		this.mFont.load();
	}

	/*
	 * Phương thức đưa đối tượng đồ họa lên Scene
	 */
	@Override
	public void onCreateScene(Engine mEngine, Scene mScene) {
		
		this.mText = new Text(pX + 4, pY - 145, mFont, "00.00", "-90.00".length(), mEngine.getVertexBufferObjectManager());
		this.mText.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		this.mText.setAlpha(0.5f);
		mScene.attachChild(mText);
		
		this.mSprite = new AnimatedSprite(pX, pY, mRegion, mEngine.getVertexBufferObjectManager()) {
			/*
			 * Phương thức cập nhập đối tượng trong 1 khoảng thời gian định trước
			 */
			@Override
			protected void onManagedUpdate(final float pSecondsElapsed) {
				// Tính lại tọa độ và thay đổi tương ứng
				RollToward.this.pY = (int) (240 - OrientationHandler.getValues()[2] * (120 / 90));
				this.setY(pY);
			}
		};
		/*
		 * Đăng ký cập nhập tọa độ sau 0.03s 1 lần
		 */
		mEngine.registerUpdateHandler(new TimerHandler(0.03f, true, new ITimerCallback() {
			
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				RollToward.this.setTextRoll(OrientationHandler.getValues()[2]);
			}
		}));
		
		mScene.attachChild(mSprite);
	}
	/**
	 * Hàm thay đổi giá trị của Text
	 * 
	 * @param roll Góc cao của máy thời điểm hiện tại
	 */
	public void setTextRoll(float roll) {
		mText.setText("" + roll);
	}
}
