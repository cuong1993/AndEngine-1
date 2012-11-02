package com.zk.lastexam.activitys;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.opengl.GLES20;
import android.util.Log;

import com.zk.lastexam.constant.Direction;
import com.zk.lastexam.entitys.Player;
import com.zk.lastexam.interfaces.TankConstants;

public class GameScreen extends SimpleBaseGameActivity implements TankConstants {
	
	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;
	
	private Camera mCamera;
	private HUD mHUD;
	private Scene mScene;
	
	private static int LAYER_COUNT = 3;
	
	// Các thành phần để dựng hình người chơi
	private BitmapTextureAtlas tankAtlas;
	private TiledTextureRegion tankRegion;
	private Player player;

	// Các thành phần để dựng cần điều khiển
	private BitmapTextureAtlas mOnScreenControlTexture;
	private ITextureRegion mOnScreenControlBaseTextureRegion;
	private ITextureRegion mOnScreenControlKnobTextureRegion;

	private TMXTiledMap tiledMap;
	
	/**
	 * Phương thức tạo EngineOptions
	 */
	@Override
	public EngineOptions onCreateEngineOptions() {
		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
	}

	/**
	 * Phương thức tạo các tài nguyên cần thiết cho game
	 */
	@Override
	protected void onCreateResources() {
		
		// Trỏ tới thư mục đồ họa gốc
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		// Tạo 1 Texture cho hình ảnh Tank
		tankAtlas = new BitmapTextureAtlas(this.getTextureManager(), 64, 64);
		tankRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(tankAtlas, this, "player.png", 0, 0, 1, 1);
		tankAtlas.load();
		
		// Tạo Texture cho cần điều khiển
		this.mOnScreenControlTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 128, TextureOptions.BILINEAR);
		this.mOnScreenControlBaseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mOnScreenControlTexture, this, "onscreen_control_base.png", 0, 0);
		this.mOnScreenControlKnobTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mOnScreenControlTexture, this, "onscreen_control_knob.png", 128, 0);
		this.mOnScreenControlTexture.load();
	}

	/**
	 * Phương thức xây dựng đồ họa lên màn hình
	 */
	@Override
	protected Scene onCreateScene() {
		
		mScene = new Scene();
		for (int i = 0; i < LAYER_COUNT; i++)
			mScene.attachChild(new Entity());
		
		// Tạo 1 phông nền màu trắng
		mScene.setBackground(new Background(1, 1, 1));
		
		try {
			final TMXLoader mapLoader = new TMXLoader(getAssets(), getTextureManager(), getVertexBufferObjectManager());
			tiledMap = mapLoader.loadFromAsset("map_1.tmx");
		} catch (TMXLoadException e) {
			e.printStackTrace();
		}
		
		for (TMXLayer layer : this.tiledMap.getTMXLayers())
			mScene.getChildByIndex(LAYER_MAP).attachChild(layer);
		
		// Tạo vị trí khởi đầu cho player (giữa màn hình)
		float pX = (CAMERA_WIDTH - tankRegion.getWidth()) / 2;
		float pY = (CAMERA_HEIGHT - tankRegion.getHeight()) / 2;
		
		// Xây dựng 1 đối tượng Player
		player = new Player(pX, pY, tankRegion, getVertexBufferObjectManager());
		
		// Thu nhỏ đối tượng về độ phân giải (32x32)
		player.setScale(0.5f);
		
		// Đặt đối tượng lên màn hình
		mScene.getChildByIndex(LAYER_TANK).attachChild(player);
		
		// Tạo cần điều khiển
		final DigitalOnScreenControl analogOnScreenControl = new DigitalOnScreenControl(0, CAMERA_HEIGHT - this.mOnScreenControlBaseTextureRegion.getHeight(), 
				this.mCamera, this.mOnScreenControlBaseTextureRegion, this.mOnScreenControlKnobTextureRegion, 0.1f, this.getVertexBufferObjectManager(), 
				new IAnalogOnScreenControlListener() {
			
			/**
			 * Phương thức xử lý khi cần điều khiển thay đổi hướng
			 */
			@Override
			public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
				Log.d("Control values", "X" + pValueX + "; Y: " + pValueY);
				// Cần điều khiển đẩy sang phải, đổi hướng player về phía phải, thay đổi giá trị vận tốc trục X
				if(pValueX == 1) {
					GameScreen.this.player.setDirection(Direction.RIGHT);
					GameScreen.this.player.setVelocityX(pValueX * 100);
					GameScreen.this.player.move();
				} 
				
				// Cần điều khiển đẩy sang trái, đổi hướng player về phía trái, thay đổi giá trị vận tốc trục X
				else if(pValueX == -1) {
					GameScreen.this.player.setDirection(Direction.LEFT);
					GameScreen.this.player.setVelocityX(pValueX * 100);
					GameScreen.this.player.move();
				}
				
				// Cần điều khiển đẩy xuống dưới, đổi hướng player xuống dưới, thay đổi giá trị vận tốc trục Y
				else if(pValueY == 1) {
					GameScreen.this.player.setDirection(Direction.DOWN);
					GameScreen.this.player.setVelocityY(pValueY * 100);
					GameScreen.this.player.move();
				 }

				// Cần điều khiển đẩy lên trên, đổi hướng player lên trên, thay đổi giá trị vận tốc trục Y
				else if(pValueY == -1) {
					GameScreen.this.player.setDirection(Direction.UP);
					GameScreen.this.player.setVelocityY(pValueY * 100);
					GameScreen.this.player.move();
				}
				
				// Cần điều khiển quay trở về vị trí trung tâm, dừng chuyển động của Player
				else {
					GameScreen.this.player.setDirection(Direction.NONE);
					GameScreen.this.player.move();					
				}
			}

			@Override
			public void onControlClick(final AnalogOnScreenControl pAnalogOnScreenControl) {
				// không làm gì.
			}
		});
		
		// Làm mờ cần điều khiển
		analogOnScreenControl.getControlBase().setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		analogOnScreenControl.getControlBase().setAlpha(0.5f);
		
		// Đặt cần điều khiển lên màn hình
		mScene.setChildScene(analogOnScreenControl);
		
		return mScene;
	}
	
	public void createHUD() {
		mHUD = new HUD();
		
		mCamera.setHUD(mHUD);
	}
}
