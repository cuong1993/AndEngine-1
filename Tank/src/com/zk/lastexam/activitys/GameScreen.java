package com.zk.lastexam.activitys;

import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
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
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXProperties;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.extension.tmx.TMXTileProperty;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.graphics.Rect;
import android.opengl.GLES20;
import com.zk.lastexam.constant.Direction;
import com.zk.lastexam.entitys.Player;
import com.zk.lastexam.interfaces.TankConstants;

public class GameScreen extends SimpleBaseGameActivity implements TankConstants {
	
	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;
	
	private Camera mCamera;
	private Scene mScene;
	
	private static int LAYER_COUNT = 4;
	
	// Các thành phần để dựng hình người chơi
	private BitmapTextureAtlas tankAtlas;
	private TiledTextureRegion tankRegion;
	private Player player;

	// Các thành phần để dựng cần điều khiển
	private BitmapTextureAtlas mOnScreenControlTexture;
	private ITextureRegion mOnScreenControlBaseTextureRegion;
	private ITextureRegion mOnScreenControlKnobTextureRegion;
	
	private BitmapTextureAtlas mFireAtlas;
	private TextureRegion mFireRegion;
	private Sprite mFire;

	private TMXTiledMap tiledMap;
	private ArrayList<Rect> rocks = new ArrayList<Rect>();
	
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
		
		// Tạo texture cho nút bắn
		this.mFireAtlas = new BitmapTextureAtlas(this.getTextureManager(), 128, 128);
		this.mFireRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mFireAtlas, getAssets(), "on_fire_icon.png", 0, 0);
		this.mFireAtlas.load();
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
		
		// Tải tile map vào bộ nhớ
		try {
			final TMXLoader mapLoader = new TMXLoader(this.getAssets(), this.mEngine.getTextureManager(), TextureOptions.BILINEAR_PREMULTIPLYALPHA, this.getVertexBufferObjectManager(), new TMXLoader.ITMXTilePropertiesListener() {
				
				@Override
				public void onTMXTileWithPropertiesCreated(TMXTiledMap arg0, TMXLayer arg1,
						TMXTile arg2, TMXProperties<TMXTileProperty> arg3) {
					if (arg3.containsTMXProperty("type", "ROCK"))
						rocks.add(new Rect(arg2.getTileX(), arg2.getTileY(), arg2.getTileX() + TILED_WIDHT, arg2.getTileY() + TILED_HEIGHT));
				}
			});

			tiledMap = mapLoader.loadFromAsset("tmx/map_1.tmx");
		} catch (TMXLoadException e) {
			e.printStackTrace();
		}
		
		// Đẩy tile map lên màn hình
		for (TMXLayer layer : this.tiledMap.getTMXLayers()) {
			// Nếu layer có tên bush thì dựng nó bên trên layer TANK
			if (layer.getName().equals("bush"))
				mScene.getChildByIndex(LAYER_BUSH).attachChild(layer);
			else
				mScene.getChildByIndex(LAYER_MAP).attachChild(layer);
		}
		// Đặt tile map vào giữa màn hình
		mScene.getChildByIndex(LAYER_MAP).setPosition(80, 0);
		mScene.getChildByIndex(LAYER_BUSH).setPosition(80, 0);
		
		// Xây dựng 1 đối tượng Player
		this.player = new Player(TILED_WIDHT * 10, TILED_HEIGHT *13, tankRegion, getVertexBufferObjectManager());
		
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
				
				// Cần điều khiển đẩy sang phải, đổi hướng player về phía phải, thay đổi giá trị vận tốc trục X
				if(pValueX == 1) {
					if (hitRock(GameScreen.this.player.getX(), GameScreen.this.player.getY(), Direction.RIGHT)){
						GameScreen.this.player.setDirection(Direction.NONE);
					}

					else {
						GameScreen.this.player.setDirection(Direction.RIGHT);
						GameScreen.this.player.setVelocityX(pValueX * 100);
					}
					
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
		
		// 
		mFire = new Sprite(CAMERA_WIDTH - 120, CAMERA_HEIGHT - 110, mFireRegion, getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				GameScreen.this.player.fire();
				return true;
			}
		};
		mFire.setScale(0.75f);
		mScene.attachChild(mFire);
		
		return mScene;
	}
	/**
	 * Phương thức kiểm tra hướng di chuyển tiếp theo có được phép
	 * 
	 * @param pX Tọa độ X hiện tại
	 * @param pY Tọa độ Y hiện tại
	 * @param direction {@link Direction} sẽ chuyển.
	 * @return true nếu chuyển hướng và va vào, false nếu ngược lại
	 */
	public boolean hitRock(float pX, float pY, Direction direction) {
		boolean isHit = false;
		int altpX;
		int altpY;
		for (Rect rock : rocks) {
			
			// Xử lý khi nhận hướng sang phải
			if (direction == Direction.RIGHT) {
				altpX = (int)pX + TILED_WIDHT;
				altpY = (int)pY + TILED_HEIGHT;
				isHit = rock.contains((int)pX, (int)pY) || rock.contains((int)pX, altpY);
			}

			// Xử lý khi nhận hướng lên trên
			if (direction == Direction.UP) {
				altpX = (int)pX - TILED_WIDHT;
				altpY = (int)pY - TILED_HEIGHT;
				isHit = rock.contains((int)pX, altpY) || rock.contains(altpX, altpY);
			}

			// Xử lý khi nhận hướng xuống dưới
			if (direction == Direction.DOWN) {
				altpX = (int)pX + TILED_WIDHT;
				altpY = (int)pY + TILED_HEIGHT;
				isHit = rock.contains((int)pX, altpY) || rock.contains(altpX, altpY);
			}

			// Xử lý khi nhận hướng sang trái
			if (direction == Direction.LEFT) {
				altpX = (int)pX - TILED_WIDHT;
				altpY = (int)pY - TILED_HEIGHT;
				pX = pX - TILED_WIDHT;
				isHit = rock.contains(altpX, altpY) || rock.contains(altpX, altpY);
			}
		}
		
		// Trả kết quả
		return isHit;
	}
}
