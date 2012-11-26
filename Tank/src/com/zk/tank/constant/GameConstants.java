package com.zk.tank.constant;

public interface GameConstants {
	//=========================================================//
	//			hằng số liên quan tới lớp đồ họa
	//=========================================================//
	public static final int LAYER_COUNT = 10;
	public static final int LAYER_BACKGROUND = 0;
	public static final int LAYER_INI = LAYER_BACKGROUND + 1;
	public static final int LAYER_ROCK = LAYER_INI + 1;
	public static final int LAYER_TANK = LAYER_ROCK + 1;
	public static final int LAYER_BUSH = LAYER_TANK + 1;
	//=========================================================//
	//			hằng số liên quan tới hướng
	//=========================================================//
	public static final int UP = 0;
	public static final int RIGHT = 90;
	public static final int DOWN = 180;
	public static final int LEFT = 270;
	//=========================================================//
	//			hằng số liên quan tới tiled map
	//=========================================================//
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
}
