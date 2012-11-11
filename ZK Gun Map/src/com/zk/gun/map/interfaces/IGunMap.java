package com.zk.gun.map.interfaces;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;

import android.content.Context;

/**
 * Interface triển khai khởi tạo thành phần đồ họa
 * và dựng các thành phần này nên {@link Scene}
 * 
 * @author zk
 * @since 9/11/2012
 */
public interface IGunMap {
	/**
	 * Phương thức khởi tạo thành phần đồ họa
	 * 
	 * @param mEngine đối tượng {@link Engine} của Game
	 * @param context đối tượng {@link Context} của Game Activity
	 */
	public void onCreateResource(Engine mEngine, Context context);
	/**
	 * Phương thức dựng các thành phần nên {@link Scene}
	 * 
	 * @param mEngine đối tượng {@link Engine} của Game
	 * @param mScene đối tượng {@link Scene} của Game
	 */
	public void onCreateScene(Engine mEngine, Scene mScene);
}
