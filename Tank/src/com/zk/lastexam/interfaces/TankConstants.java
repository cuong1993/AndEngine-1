package com.zk.lastexam.interfaces;

import com.zk.lastexam.objects.Tank;

/**
 * Interface mô tả các hằng số sử dụng trong game
 * 
 * @author zk
 * @since 2/11/2012
 */
public interface TankConstants {
	
	/**
	 * Số ô trên hàng dọc (15)
	 */
	public static final int TILED_HORIZONTAL = 15;
	/**
	 * Số ô trên hàng ngang (20)
	 */
	public static final int TILED_VERTICAL = 20;
	
	/**
	 * Độ rộng của 1 ô (32px)
	 */
	public static final int TILED_WIDHT = 32;
	/**
	 * Độ cao của 1 ô (32px)
	 */
	public static final int TILED_HEIGHT = 32;
	
	/**
	 * Lớp hình ảnh nền được dựng lên màn hình (0)
	 */
	public static final int LAYER_BACKGROUND = 0;
	/**
	 * Lớp hình ảnh bản đồ được dựng lên màn hình (1)
	 */
	public static final int LAYER_MAP = LAYER_BACKGROUND + 1;
	/**
	 * Lớp hình ảnh các đối tượng {@link Tank} (2)
	 */
	public static final int LAYER_TANK = LAYER_MAP + 1;
}
