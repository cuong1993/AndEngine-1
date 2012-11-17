package com.zk.gunmap.activity;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import com.zk.gunmap.entitys.AzimuthToward;
import com.zk.gunmap.entitys.PreShotButton;
import com.zk.gunmap.entitys.RollToward;
import com.zk.gunmap.entitys.ShotButton;
import com.zk.gunmap.handlers.OrientationHandler;
import com.zk.gunmap.interfaces.GameConstants;

public class H2ScreenVer2Activity extends SimpleBaseGameActivity implements GameConstants {

	// đối tượng để đăng ký nhận dữ liệu từ sensor
	OrientationHandler orient;
	
	// Các thuộc tính cần thiết xây dựng game
	private static final float CAMERA_WIDTH = 800;
	private static final float CAMERA_HEIGHT = 480;
	private Camera mCamera;
	private Scene mScene;
	private HUD mHUD;
	
	// Các đối tượng hình ảnh cần thiết của Game
	private BitmapTextureAtlas azimuthBarAtlas;
	private TextureRegion azimuthBarRegion;
	
	private BitmapTextureAtlas crosshairsAtlas;
	private TextureRegion crosshairsRegion;
	
	// Đối tượng Azimuth thể hiện sự thay đổi phương vị
	private AzimuthToward azimuthToward;
	private RollToward rollToward;
	
	private PreShotButton preShotButton;
	private ShotButton shotButton;

	
	/**
	 * Phương thức khởi tạo {@link EngineOptions}
	 */
	@Override
	public EngineOptions onCreateEngineOptions() {
		// Tạo khoảng không gian 800 * 480
		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		// Đăng ký nhận dữ liệu từ sensor Orientation
		orient = new OrientationHandler(this);
		
		azimuthToward = new AzimuthToward(LAYER_USER, CAMERA_WIDTH, CAMERA_HEIGHT);
		rollToward = new RollToward(LAYER_USER, CAMERA_WIDTH, CAMERA_HEIGHT);
		
		preShotButton = new PreShotButton();
		shotButton = new ShotButton(CAMERA_WIDTH);
		
		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
	}

	/**
	 * Hàm khởi tạo các tài nguyên cần thiết
	 */
	@Override
	protected void onCreateResources() {
	
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("images/");
		
		// Tạo khối dữ liệu cho thanh chia độ cho hình azimuth_bar.png trong bộ nhớ
		azimuthBarAtlas = new BitmapTextureAtlas(this.getTextureManager(), 600, 48);
		azimuthBarRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(azimuthBarAtlas, this, "azimuth_bar.png", 0, 0);
		azimuthBarAtlas.load();
		
		// Tạo khối dữ liệu cho tâm ngắm cho hình crosshairs.png trong bộ nhớ
		crosshairsAtlas = new BitmapTextureAtlas(getTextureManager(), 256, 256);
		crosshairsRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(crosshairsAtlas, this, "crosshairs.png", 0, 0);
		crosshairsAtlas.load();
		
		// Xây dựng HUD
		createHUD();
		
		azimuthToward.onCreateResource(mEngine, this);
		rollToward.onCreateResource(mEngine, this);
		
		preShotButton.onCreateResource(mEngine, this);
		shotButton.onCreateResource(mEngine, this);
	}
	
	/**
	 * Hàm xây dựng {@link Scene}
	 */
	@Override
	protected Scene onCreateScene() {	
		mScene = new Scene();
		
		// Tạo 1 Scene với 5 lớp thiết kế
		for (int i = 0; i < LAYER_COUNT; i++) {
			mScene.attachChild(new Entity());
		}
		
		// Tạo màu nền cho Scene với màu trắng
		mScene.setBackground(new Background(1.0f, 1.0f, 1.0f, 0.0f));
		
		azimuthToward.onCreateScene(mEngine, mScene);
		rollToward.onCreateScene(mEngine, mScene);
		
		preShotButton.onCreateScene(mEngine, mScene);
		shotButton.onCreateScene(mEngine, mScene);
		
		return mScene;
	}
	
	/**
	 * Phương thức xây dựng HUD cho game
	 */
	public void createHUD() {		
		mHUD = new HUD();
		
		// Tính tọa độ bắt đầu dựng hình cho thanh chia độ
		float pX = (CAMERA_WIDTH - azimuthBarRegion.getWidth()) / 2;
		float pY = 2;
		
		// Đưa thanh chia độ vào HUD với tọa độ vừa tính
		mHUD.attachChild(new Sprite(pX, pY, azimuthBarRegion, this.getVertexBufferObjectManager()));
		
		// Tính tọa độ bắt đầu dựng hình cho tâm ngắm
		pX = (CAMERA_WIDTH - crosshairsRegion.getWidth()) / 2;
		pY = (CAMERA_HEIGHT - crosshairsRegion.getHeight()) /2;
		
		// Đưa tâm ngắm vào HUD với tọa độ vừa tạo
		mHUD.attachChild(new Sprite(pX, pY, crosshairsRegion, this.getVertexBufferObjectManager()));
		
		// Hiển thị khối HUD vừa xây dựng lên màn hình.
		mCamera.setHUD(mHUD);
	}
}
