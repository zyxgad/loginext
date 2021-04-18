
package com.github.zyxgad.loginext.player;

public enum Level{
	BADLY (-6, "BADLY"),
	VISE  (-4, "VISE"),
	FALL  (-1, "PLAYER"),
	PLAYER( 0, "PLAYER"),
	FINE  ( 1, "FINE"),
	WONDER( 2, "WONDER"),
	VIP   ( 3, "VIP"),
	CHIEF ( 5, "CHIEF"),
	OPER  ( 7, "OPER"),
	SUPER ( 8, "SUPER");

	private final int auth;
	private final String name;

	private Level(int auth, String name){
		this.auth = auth;
		this.name = name;
	}

	public int Auth(){
		return this.auth;
	}

	public String getDis(){
		return this.name;
	}
}
