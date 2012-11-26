package com.zk.tank.interfaces;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.TextureRegion;

public abstract class Tank {
	protected BitmapTextureAtlas mAtlas;
	protected TextureRegion mRegion;
	protected Sprite mSprite;
	protected int mDirection;
	
	public abstract void move(boolean running);
	public abstract void fire();
	public abstract void die();
}
