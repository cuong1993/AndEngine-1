package com.zk.gun.map.interfaces;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;

import android.content.Context;

public interface IGunMap {
	public void onCreateResource(Engine mEngine, Context context);
	public void onCreateScene(Engine mEngine, Scene mScene);
}
