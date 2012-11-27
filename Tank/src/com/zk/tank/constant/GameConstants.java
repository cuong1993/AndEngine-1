package com.zk.tank.constant;

public interface GameConstants {
	//=========================================================//
	//			hằng số liên quan tới phân giải
	//=========================================================//
	public static final int CAMERA_WIDTH = 800;
	public static final int CAMERA_HEIGHT = 480;
	//=========================================================//
	//			hằng số liên quan tới đường dẫn
	//=========================================================//
	public static final String ASSET_GRAPHICS = "gfx/";
	public static final String ASSET_FONTS = "fonts/";
	public static final String ASSET_MUSICS = "musics/";
	public static final String ASSET_SOUNDS = "sounds/";
	public static final String ASSET_TMX = "tmx/";
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
	public static final int NONE = -1;
	//=========================================================//
	//			hằng số liên quan tới tiled map
	//=========================================================//
	/**
	 * Số ô trên hàng dọc (10)
	 */
	public static final int TILED_HORIZONTAL = 10;
	/**
	 * Số ô trên hàng ngang (15)
	 */
	public static final int TILED_VERTICAL = 15;	
	/**
	 * Độ rộng của 1 ô (32px)
	 */
	public static final int TILED_WIDTH = 48;
	/**
	 * Độ cao của 1 ô (32px)
	 */
	public static final int TILED_HEIGHT = 48;
	//=========================================================//
	//			hằng số liên quan tới tốc độ chạy
	//=========================================================//
	public static final float SPEED_SLOW = 1.0f;
	public static final float SPEED_MEDIUM = 1/50f;
	public static final float SPEED_FAST = 1/60f;
	public static final int SPEED_STEP = 48;
	//=========================================================//
	//			hằng số liên quan tới tốc độ bắn
	//=========================================================//
	public static final float SHOT_SPEED_SLOW = 1.0f;
	public static final float SHOT_SPEED_MEDIUM = 0.7f;
	public static final float SHOT_SPEED_FAST = 0.5f;
}
