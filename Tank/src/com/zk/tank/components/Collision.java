package com.zk.tank.components;

import java.util.ArrayList;

import org.andengine.engine.Engine;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;

import com.zk.tank.constant.GameConstants;

/**
 * Class mô tả cách thức nhận biết và xử lý va chạm giữa các thực thể
 * 
 * @author zk
 * @since 28/11/2012
 */
public class Collision implements GameConstants {

	//=================================================================================//
	//										METHODS
	//=================================================================================//
	/**
	 * Phương thức xác định va chạm giữa {@link Sprite} và các khối tile mang thuộc tính ROCK
	 * 
	 * @param mEngine {@link Engine} sử dụng trong game
	 * @param direction Hướng của vật thể di chuyển
	 * @param mSprite {@link Sprite} vật thể di chuyển
	 * @param rocks ArrayList {@link Rectangle} chứa tọa độ các tile mang thuộc tính ROCK
	 * @return true nếu 2 vật thể có xảy ra và chạm, false nếu ngược lại
	 */
	public static boolean isCollision(Engine mEngine, int direction, Sprite mSprite, ArrayList<Rectangle> rocks) {
		
		// Tạo 1 đối tượng Rectangle tạm thời từ Sprite nguôn để kiểm tra
		Rectangle entity = new Rectangle(mSprite.getX(), mSprite.getY(), 
				mSprite.getWidth() * 1.5f - 2, mSprite.getHeight() * 1.5f - 2, mEngine.getVertexBufferObjectManager());
		
		// Dịch chuyển đối tượng Rectangle tạm theo hướng đưa vào
		switch (direction) {
		case UP:
			entity.setPosition(entity.getX() + 1, entity.getY() - 3);
			break;
		case RIGHT:
			entity.setPosition(entity.getX() + 3, entity.getY() + 1);
			break;
		case DOWN:
			entity.setPosition(entity.getX() + 1, entity.getY() + 3);
			break;
		case LEFT:
			entity.setPosition(entity.getX() - 3, entity.getY() + 1);
			break;
		default:
			return true;
		}
		
		// Kiểm tra đối tượng Sprite tạm sau khi dịch chuyển có va chạm
		// với 1 trong những khối tile mang thuộc tính ROCK hay không
		// trả về true nếu có
		for (Rectangle rock : rocks) {
			if (entity.collidesWith(rock)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Phương thức xác định va chạm giữa 2 {@link Sprite}
	 * 
	 * @param mEngine {@link Engine} sử dụng trong game
	 * @param direction Hướng của vật thể di chuyển
	 * @param mSprite {@link Sprite} vật thể di chuyển
	 * @param pSprite {@link Sprite} vật thể làm mốc
	 * @return true nếu 2 vật thể có xảy ra và chạm, false nếu ngược lại
	 */
	public static boolean isCollision(Engine mEngine, int direction, Sprite mSprite, Sprite pSprite) {

		// Tạo 1 đối tượng Rectangle tạm thời từ Sprite nguôn để kiểm tra
		Rectangle entity = new Rectangle(mSprite.getX(), mSprite.getY(), 
				mSprite.getWidth() - 2, mSprite.getHeight() - 2, mEngine.getVertexBufferObjectManager());

		// Dịch chuyển đối tượng Sprite tạm theo hướng đưa vào
		switch (direction) {
		case UP:
			entity.setPosition(entity.getX(), entity.getY() - 2);
			break;
		case RIGHT:
			entity.setPosition(entity.getX() + 2, entity.getY());
			break;
		case DOWN:
			entity.setPosition(entity.getX(), entity.getY() + 2);
			break;
		case LEFT:
			entity.setPosition(entity.getX() - 2, entity.getY());
			break;
		default:
			break;
		}
		
		// Trả về trạng thái va chạm giữa đối tượng Sprite tạm
		// và đối tượng Sprite làm mốc
		return entity.collidesWith(pSprite);
	}
}
