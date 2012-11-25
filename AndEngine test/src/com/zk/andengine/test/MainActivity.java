package com.zk.andengine.test;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import com.truonghau.gunmap.interfaces.GameConstants;

public class MainActivity extends SimpleBaseGameActivity implements GameConstants {
	
	private static final float CAMERA_WIDTH = 800;
	private static final float CAMERA_HEIGHT = 480;
	private Camera mCamera;
	private Scene mScene;
	private Rocket mRocket;
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		mRocket = new Rocket(LAYER_USER, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
	}

	@Override
	protected void onCreateResources() {
		mRocket.onCreateResource(mEngine, this);
	}

	@Override
	protected Scene onCreateScene() {
		mScene = new Scene();
		for (int i = 0; i < LAYER_COUNT; i++)
			mScene.attachChild(new Entity());
		this.mRocket.onCreateScene(mEngine, mScene);
		mScene.setBackground(new Background(1.0f, 1.0f, 1.0f, 0.0f));
		return mScene;
	}
}
