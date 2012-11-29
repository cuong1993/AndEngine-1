package com.zk.tank.entitys;

import com.zk.tank.constant.GameConstants;

/**
 * class mô tả 1 đối tượng {@link Player}
 * 
 * @author zk (ndviettien.zk@gmail.com)
 * @since 26/11/2012
 */
public class Player extends Tank {
	//=================================================================================//
	//										FIELDS
	//=================================================================================//
	private int playerID;
	
	//=================================================================================//
	//									CONSTRUCTORS
	//=================================================================================//
	/**
	 * Hàm tạo đối tượng {@link Player} mô tả sơ lược vị trí, hướng ban đầu và ID người chơi
	 * 
	 * @param tiledX Vị trí ô theo chiều ngang ban đầu muốn dựng 
	 * @param tiledY Vị trí ô theo chiều dọc ban đầu muốn dựng
	 * @param direction Hướng ban đầu của đối tượng (lấy từ interface {@link GameConstants}
	 * @param playerID ID của người chơi (1: chủ phòng, 2..4L khách)
	 */
	public Player(int playerID, int tiledX, int tiledY, int direction) {
		this(playerID, tiledX, tiledY, direction, SPEED_FAST);
	}

	public Player(int playerID, int tiledX, int tiledY, int direction, float speed) {
		super(tiledX, tiledY, direction, speed);
		this.playerID = playerID;
	}

	//=================================================================================//
	//										METHODs
	//=================================================================================//
	

	//=================================================================================//
	//									 SETTER & GETTER
	//=================================================================================//
	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

}
