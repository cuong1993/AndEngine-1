package com.zk.tank.activitys;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.opengl.GLES20;

import com.zk.tank.components.ShotButton;
import com.zk.tank.components.TiledMapRender;
import com.zk.tank.constant.GameConstants;
import com.zk.tank.entitys.Player;

/**
 * Màn hình khi chơi game
 * 
 * @author zk (ndviettien.zk@gmail.com)
 * @since 26/11/2012
 */
public class OnGameScreen extends SimpleBaseGameActivity implements GameConstants {
	
	//===============================================//
	//					   FIELDS
	//===============================================//
	/*
	 * Các trường liên quan tới hệ thống trong Game
	 */
	private Camera mCamera;
	private EngineOptions mOptions;
	private Scene mScene;
	private HUD mHUD;
	
	/*
	 * Các trường liên quan tới xây dựng 1 cần điều khiển
	 */
	private BitmapTextureAtlas mOnScreenControlTexture;
	private TextureRegion mOnScreenControlBaseTextureRegion;
	private TextureRegion mOnScreenControlKnobTextureRegion;
	private DigitalOnScreenControl mController;
	
	/*
	 * Các trường thành phần xây dựng game
	 */
	private TiledMapRender mTMXRender;
	private ShotButton mButton;
	private Player mPlayer;

	//=================================================================================//
	//									   METHODS
	//=================================================================================//
	@Override
	public EngineOptions onCreateEngineOptions() {

		//============================================================//
		// Khởi tạo các thuộc tính của hệ thống cần sử dụng trong game
		//============================================================//
		
		// Khởi tạo Camera phân giải mặc định (800x480)
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		
		// Khởi tạo EngineOptions, sử dụng màn hình chế độ ngang, dùng chế độ thu phóng theo tỉ lệ
		this.mOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, 
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
		
		// Yêu cầu sử dụng tính năng cảm ứng đa điểm
		this.mOptions.getTouchOptions().setNeedsMultiTouch(true);
		
		// Khởi tạo đối tượng TiledMapRender
		this.mTMXRender = new TiledMapRender(this.getAssets());
		
		// Khởi tạo đối tượng Player với hướng mặc định quay lên trên 
		this.mPlayer = new Player(1, 6, 9, UP);
		
		this.mButton = new ShotButton(mPlayer);
		
		return this.mOptions;
	}

	@Override
	protected void onCreateResources() {
		
		//======================================================================//
		//						Tạo tài nguyên cho controller
		//======================================================================//	
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(ASSET_GRAPHICS);
		this.mOnScreenControlTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 128, TextureOptions.BILINEAR);
		
		this.mOnScreenControlBaseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				this.mOnScreenControlTexture, this, "onscreen_control_base.png", 0, 0);
		
		this.mOnScreenControlKnobTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				this.mOnScreenControlTexture, this, "onscreen_control_knob.png", 128, 0);
		
		this.mOnScreenControlTexture.load();
		
		// Tạo tài nguyên cho đối tượng Playercủa Activity
		this.mPlayer.onCreateResource(this.mEngine, this);
		
		this.mButton.onCreateResource(mEngine, this);
	}

	@Override
	protected Scene onCreateScene() {
		this.createHUD();
		
		this.mScene = new Scene();
		for (int i = 0; i< LAYER_COUNT; i++)
			this.mScene.attachChild(new Entity());
		
		// Vẽ đối tượng Player lên màn hình
		this.mPlayer.onCreateScene(this.mEngine, this.mScene);
		
		// Vẽ TiledMap lên màn hình
		this.mTMXRender.onCreateScene(mEngine, mScene);
		
		this.createController();
		
		return this.mScene;
	}
	
	//======================================================================//
	//								Tạo HUD
	//======================================================================//	
	public void createHUD() {	
		this.mHUD = new HUD();
		this.mButton.onCreateScene(mEngine, mHUD);
		this.mCamera.setHUD(mHUD);
	}
	
	//======================================================================//
	//							  Tạo Controller
	//======================================================================//
	public void createController() {
		
		// Xây dựng 1 cần điều khiển
		this.mController = new DigitalOnScreenControl(40, CAMERA_HEIGHT - this.mOnScreenControlBaseTextureRegion.getHeight(), 
				this.mCamera, this.mOnScreenControlBaseTextureRegion, this.mOnScreenControlKnobTextureRegion, SPEED_SLOW, 
				this.getVertexBufferObjectManager(), new IOnScreenControlListener() {

			// Xử lý dữ liệu khi cần điều khiển thay đổi
			@Override
			public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, 
					final float pValueX, final float pValueY) {

				// Thay đổi hướng đối tượng Player phụ thuộc hướng của cần điều khiển
				if(pValueX == 1) {
					OnGameScreen.this.mPlayer.setmDirection(RIGHT);
				} else if(pValueX == -1) {
					OnGameScreen.this.mPlayer.setmDirection(LEFT);
				} else if(pValueY == 1) {
					OnGameScreen.this.mPlayer.setmDirection(DOWN);
				} else if(pValueY == -1) {
					OnGameScreen.this.mPlayer.setmDirection(UP);
				} else {
					OnGameScreen.this.mPlayer.setmDirection(NONE);					
				}

				// Di chuyển đối tượng theo hướng của cần điều khiển
				OnGameScreen.this.mPlayer.move(OnGameScreen.this.mEngine);
			}
		});

		/* Make the controls semi-transparent. */
		this.mController.getControlBase().setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		this.mController.getControlBase().setAlpha(0.5f);
		this.mController.getControlBase().setScaleCenter(0, 128);
		this.mController.getControlBase().setScale(1.25f);
		this.mController.getControlKnob().setScale(1.25f);
		this.mController.refreshControlKnobPosition();

		// Dựng cần điều khiển lên màn hình
		this.mScene.setChildScene(this.mController);
	}
}
