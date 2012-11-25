package com.zk.andengine.test;

import java.util.ArrayList;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;

import android.content.Context;

import com.truonghau.gunmap.interfaces.GameConstants;
import com.truonghau.gunmap.interfaces.IGunMap;
import com.truonghau.gunmap.model.MemberUserItem;

/**
 * Class mô tả cách thức tạo menu chọn vũ khí
 * và phương thức xử lý khi nhấn chọn trên menu
 * 
 * @author zk-Home
 * @since 20/11/2012
 */
public class RocketMenu implements IGunMap, GameConstants, IOnMenuItemClickListener {
	// Tổng số đối tượng có trên menu
	private int itemCount;
	// đối tượng để xây dựng menu
	private MenuScene mMenu;
	//--------------------------------------------------------------------------------------//
	// Các thuộc tính cần thiết để xây dựng các mục hình ảnh lên menu
	private BitmapTextureAtlas mAtlas;
	private ArrayList<TextureRegion> mRegions;
	private ArrayList<String> mPaths;
	private int[] mIDs;
	private ArrayList<SpriteMenuItem> mItems;
	//--------------------------------------------------------------------------------------//
	/**
	 * Hàm tạo, khởi tạo các thuộc tính cần thiết
	 * @param mUserItem 1 Đối tượng {@link MemberUserItem}
	 * @param itemCount Tổng số đối tượng muốn dựng trên menu
	 */
	public RocketMenu(MemberUserItem mUserItem, int itemCount) {
		this.itemCount = itemCount;
		// Tạo các mảng với số lượng tương ứng với itemCount
		this.mRegions = new ArrayList<TextureRegion>(this.itemCount);
		this.mPaths = new ArrayList<String>(this.itemCount);
		this.mItems = new ArrayList<SpriteMenuItem>(this.itemCount);
		this.mIDs = new int[this.itemCount];
		// Tạo đường dẫn từ thuộc tính getTendan() của MemberUserItem
		this.mPaths.add(mUserItem.getTendan() + ".png");
		// Lưu chỉ số ID vào mảng
		this.mIDs[0] = mUserItem.getDan_id();
	}
	//--------------------------------------------------------------------------------------//
	@Override
	public void onCreateResource(Engine mEngine, Context context) {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("images/");
		this.mAtlas = new BitmapTextureAtlas(mEngine.getTextureManager(), this.itemCount * 200, 128);
		for (int i = 0; i < this.itemCount; i++) {
			String pPath = mPaths.get(i);
			mRegions.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAtlas, context, pPath, i * 170, 0));
		}
		this.mAtlas.load();
	}
	//--------------------------------------------------------------------------------------//
	@Override
	public void onCreateScene(Engine mEngine, Scene mScene) {
		// Tạo Scene cho Menu
		this.mMenu = new MenuScene(mEngine.getCamera());
		
		for (int i = 0; i < this.itemCount; i++) {
			// Tạo các đối tượng trên Menu
			this.mItems.add(new SpriteMenuItem(mIDs[i], mRegions.get(i), mEngine.getVertexBufferObjectManager()));
			// Thay đổi vị trí của các đối tượng cho phù hợp
			mItems.get(i).setPosition(50 + i * 170, 220);
			// Gán các đối tượng vào trong Menu
			this.mMenu.addMenuItem(this.mItems.get(i));
		}
		// Đẩy menu lên màn hình
		mScene.setChildScene(mMenu);
		this.mMenu.setBackgroundEnabled(false);
		this.mMenu.setOnMenuItemClickListener(this);
	}
	//--------------------------------------------------------------------------------------//
	/* 
	 * Phương thức xử lý khi có sự kiện chạm vào các mục trong menu
	 */
	@Override
	public boolean onMenuItemClicked(MenuScene arg0, IMenuItem arg1,
			float arg2, float arg3) {
		// TODO Auto-generated method stub
		return false;
	}
	//--------------------------------------------------------------------------------------//
}
