package com.zk.lastexam.entitys;

public class Rock {
	private float mX;
	private float mY;
	private int mColumn;
	private int mRow;
	/**
	 * Hàm tạo class Rock
	 * 
	 * @param mX Tọa độ X của đối tượng
	 * @param mY Tọa độ Y của đối tượng
	 */
	public Rock(float mX, float mY, int pColumn, int pRow) {
		this.mX = mX;
		this.mY = mY;
		this.mColumn = pColumn;
		this.mRow = pRow;
	}	
	/**
	 * @return Tọa độ X của đối tượng
	 */
	public float getX() {
		return this.mX;
	}	
	/**
	 * @param pX Tọa độ X của đối tượng
	 */
	public void setX(float pX) {
		this.mX = pX;
	}
	
	/**
	 * @return Tọa độ Y của đối tượng
	 */
	public float getY() {
		return this.mY;
	}
	
	/**
	 * @param pY Tọa độ Y của đối tượng
	 */
	public void setY(float pY) {
		this.mY = pY;
	}
	/**
	 * @return Chỉ số cột của đối tượng
	 */
	public float getColumn() {
		return this.mColumn;
	}	
	/**
	 * @param pColumn
	 */
	public void setColumn(float pColumn) {
		this.mX = pColumn;
	}
	
	/**
	 * @return Chỉ số dòng của đối tượng
	 */
	public float getRow() {
		return this.mRow;
	}
	
	/**
	 * @param pRow
	 */
	public void setRow(float pRow) {
		this.mY = pRow;
	}
}
