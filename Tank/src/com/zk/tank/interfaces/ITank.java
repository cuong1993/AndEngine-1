package com.zk.tank.interfaces;

/**
 * Interface mô tả các phương thức của 1 đối tượng Tank
 * 
 * @author zk
 * @since 26/11/2012
 */
public interface ITank {
	public void move(boolean running);
	public void fire();
	public void die();
}
