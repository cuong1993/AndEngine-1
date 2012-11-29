package com.zk.tank.components;

import java.util.ArrayList;

import org.andengine.engine.Engine;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXProperties;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.extension.tmx.TMXTileProperty;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.util.debug.Debug;

import android.content.Context;
import android.content.res.AssetManager;

import com.zk.tank.constant.GameConstants;
import com.zk.tank.interfaces.IAndEngine;

/**
 * @author zk
 * @since 27/11/2012
 */
public class TiledMapRender implements GameConstants, IAndEngine {
	private AssetManager mAssetManager;
	private TMXTiledMap mTMXTiledMap;
	
	ArrayList<Rectangle> rocks;

	//=================================================================================//
	//									CONSTRUCTORS
	//=================================================================================//
	/**
	 * Hàm tạo class {@link TiledMapRender} khởi tạo quyền truy cập thư mực asset
	 * 
	 * @param assetManager {@link AssetManager} của Game
	 */
	public TiledMapRender(AssetManager assetManager) {
		this.mAssetManager = assetManager;
		this.rocks = new ArrayList<Rectangle>();
	}

	//=================================================================================//
	//										METHODS
	//=================================================================================//
	@Override
	public void onCreateResource(Engine mEngine, Context context) {
		
	}	

	/**
	 * Phương thức dựng đồ họa đối tượng lên màn hình
	 * 
	 * @param mEngine {@link Enigne} sử dụng trong Game
	 * @param mScene {@link Scene} sử dụng trong Game
	 */
	@Override
	public void onCreateScene(final Engine mEngine, Scene mScene) {
		try {
			final TMXLoader tmxLoader = new TMXLoader(this.mAssetManager, mEngine.getTextureManager(), 
					mEngine.getVertexBufferObjectManager(), 
					new TMXLoader.ITMXTilePropertiesListener() {
				
				/* 
				 * Phương thức xử lý khi 1 Tiled có thuộc tính được tạo
				 * Lưu lại tọa độ của các Tiled có thuộc tính là ROCK dưới dạng 1 đối tượng Rect 
				 */
				@Override
				public void onTMXTileWithPropertiesCreated(TMXTiledMap pTMXTiledMap, TMXLayer pTMXLayer,
						TMXTile pTMXTile, TMXProperties<TMXTileProperty> pTMXProperties) {
					
					int left, top;
					
					// Lọc các đối tượng có kiểu ROCK
					if (pTMXTile.getTMXTileProperties(pTMXTiledMap).containsTMXProperty("type", "ROCK")
							|| pTMXTile.getTMXTileProperties(pTMXTiledMap).containsTMXProperty("type", "WONDER")) {
						
						// Tính tọa độ các Tiled này trên màn hình
						left = 48 + pTMXTile.getTileColumn() * TILED_WIDTH;
						top = 8 + pTMXTile.getTileRow() * TILED_HEIGHT;
						
						// Lưu các tọa độ vừa tính vào 1 mảng
						TiledMapRender.this.rocks.add(new Rectangle(left, top, TILED_WIDTH, TILED_HEIGHT, mEngine.getVertexBufferObjectManager()));
					}
				}
			});
			// Đọc dữ liệu từ file TMX trong thư mục assets
			this.mTMXTiledMap = tmxLoader.loadFromAsset("tmx/map_2.tmx");
			
		} catch (final TMXLoadException e) {
			Debug.e(e);
		}

		// Thiết kế lại các lớp đồ họa cho phù hợp
		for (TMXLayer tmxLayer : this.mTMXTiledMap.getTMXLayers()) {
			
			// Đặt lớp BUSH lên trên lớp TANK khi dựng hình
			if (tmxLayer.getTMXLayerProperties().containsTMXProperty("type", "BUSH")) {
				tmxLayer.setPosition(160, 80);
				tmxLayer.setScale(1.5f);
				mScene.getChildByIndex(LAYER_BUSH).attachChild(tmxLayer);
			}
			
			// Đặt các lớp còn lại bên dưới lớp TANK khi dựng hình
			else {
				tmxLayer.setPosition(160, 80);
				tmxLayer.setScale(1.5f);
				mScene.getChildByIndex(LAYER_ROCK).attachChild(tmxLayer);
			}
		}
	}
	
	//===================================================================//
	//							GETTER & SETTER
	//===================================================================//
	public ArrayList<Rectangle> getRocks() {
		return rocks;
	}

	public void setRocks(ArrayList<Rectangle> rocks) {
		this.rocks = rocks;
	}
}
