package com.zk.tank.interfaces;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;

import android.content.Context;

/**
 * Interface mô tả các phương thức cần thiết
 * để xây dựng 1 đối tượng bằng đồ họa
 * 
 * @author zk
 * @since 26/11/2012
 */
public interface IAndEngine {
	/**
	 * Phương thức khởi tạo tài nguyên đồ họa cần thiết để mô tả đối tượng bằng hình ảnh
	 * 
	 * @param mEngine {@link Enigne} sử dụng trong Game
	 * @param context {@link Context} của Activity trong Game
	 * 
	 */
	public void onCreateResource(Engine mEngine, Context context);
	/**
	 * Phương thức dựng đồ họa đối tượng lên màn hình
	 * 
	 * @param mEngine {@link Enigne} sử dụng trong Game
	 * @param mScene {@link Scene} sử dụng trong Game
	 */
	public void onCreateScene(Engine mEngine, Scene mScene);
}
