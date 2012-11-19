package com.zk.gunmap.entitys;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.HorizontalAlign;

import android.content.Context;
import android.graphics.Color;

import com.zk.gunmap.handlers.OrientationHandler;
import com.zk.gunmap.interfaces.GameConstants;
import com.zk.gunmap.interfaces.IGunMap;

/**
 * Class thể hiện đối tượng RollToward chỉ dẫn góc nghiêng của máy
 * 
 * @author zk
 * @since 11/11/2012
 */
public class RollToward implements IGunMap,GameConstants {
	// Tọa độ dựng thành phần đồ họa
	private int pX;
	private int pY;
	// Lớp đồ họa để dựng đối tượng
	private int mLayer;
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
	 * @param layer Lớp đồ họa được sử dụng để dựng đối tượng
	 * @param cameraWidth Chiều rộng của {@link Camera} game xây dựng
	 * @param cameraHeight Chiều cao của {@link Camera} game xây dựng
	 */
	public RollToward(int layer, float cameraWidth, float cameraHeight) {
		this.mLayer = layer;
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
		this.mFont = FontFactory.createFromAsset(mEngine.getFontManager(), mEngine.getTextureManager(), 128, 64, TextureOptions.BILINEAR, context.getAssets(), "Audiowide_Regular.ttf", 20, true, Color.BLACK);
		this.mFont.load();
	}

	/*
	 * Phương thức đưa đối tượng đồ họa lên Scene
	 */
	@Override
	public void onCreateScene(Engine mEngine, Scene mScene) {
		
		this.mText = new Text(pX + 5, pY - 140, mFont, "87.00", 100, new TextOptions(HorizontalAlign.LEFT), mEngine.getVertexBufferObjectManager());
		mScene.getChildByIndex(mLayer).attachChild(mText);
		
		mEngine.registerUpdateHandler(new TimerHandler(0.2f, true, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler arg0) {
				RollToward.this.setTextRoll((int) OrientationHandler.getValues()[2]);
			}
		}));
		
		this.mSprite = new AnimatedSprite(pX, pY, mRegion, mEngine.getVertexBufferObjectManager()) {
			/*
			 * Phương thức cập nhập đối tượng trong 1 khoảng thời gian định trước
			 */
			@Override
			protected void onManagedUpdate(final float pSecondsElapsed) {
				// Tính lại tọa độ và thay đổi tương ứng
				RollToward.this.pY = (int) (240 - (OrientationHandler.getValues()[2] - 90) * (128 / 90));
				this.setY(pY);
			}
		};
		
		mScene.getChildByIndex(mLayer).attachChild(mSprite);
	}
	/**
	 * Hàm thay đổi giá trị của Text
	 * 
	 * @param roll Góc cao của máy thời điểm hiện tại
	 */
	public void setTextRoll(int roll) {
		String text = Integer.toString(roll);
		text += " / ";
		text += Integer.toString((int) OrientationHandler.getValues()[0]);
		text += " / ";
		text += Integer.toString((int) OrientationHandler.getValues()[1]);
		this.mText.setText(text);
	}
}
