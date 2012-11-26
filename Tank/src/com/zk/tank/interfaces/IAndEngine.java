package com.zk.tank.interfaces;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;

import android.content.Context;

public interface IAndEngine {
	public void onCreateResource(Engine mEngine, Context context);
	public void onCreateScene(Engine mEngine, Scene mScene);
}
