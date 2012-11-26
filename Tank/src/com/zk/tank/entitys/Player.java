package com.zk.tank.entitys;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;

import android.content.Context;

import com.zk.tank.constant.GameConstants;
import com.zk.tank.interfaces.IAndEngine;
import com.zk.tank.interfaces.Tank;

public class Player extends Tank implements GameConstants, IAndEngine {
	//=================================================================================//
	//									CONSTRUCTORS
	//=================================================================================//
	

	//=================================================================================//
	//										METHODs
	//=================================================================================//
	@Override
	public void onCreateResource(Engine mEngine, Context context) {
		
	}

	@Override
	public void onCreateScene(Engine mEngine, Scene mScene) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(boolean running) {
		this.mSprite.registerUpdateHandler(new TimerHandler(0.03f, true, new ITimerCallback() {

			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				switch (Player.this.mDirection) {
				case UP:
					break;
				case RIGHT:
					break;
				case DOWN:
					break;
				case LEFT:
					break;
				default:
					break;
				}
			}
		}));
	}

	@Override
	public void fire() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

}
