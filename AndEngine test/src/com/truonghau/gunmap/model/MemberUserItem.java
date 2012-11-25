package com.truonghau.gunmap.model;

public class MemberUserItem {
	String user_id;
	int mau;
	int dan_id;
	String tendan;
	int tocdo,domanh;
	public String getTendan() {
		return tendan;
	}
	public void setTendan(String tendan) {
		this.tendan = tendan;
	}
	public int getTocdo() {
		return tocdo;
	}
	public void setTocdo(int tocdo) {
		this.tocdo = tocdo;
	}
	public int getDomanh() {
		return domanh;
	}
	public void setDomanh(int domanh) {
		this.domanh = domanh;
	}
	public MemberUserItem() {
		super();
	}
	int luongdan;
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getMau() {
		return mau;
	}
	public void setMau(int mau) {
		this.mau = mau;
	}
	public int getDan_id() {
		return dan_id;
	}
	public void setDan_id(int dan_id) {
		this.dan_id = dan_id;
	}
	public int getLuongdan() {
		return luongdan;
	}
	public void setLuongdan(int luongdan) {
		this.luongdan = luongdan;
	}
	
}
