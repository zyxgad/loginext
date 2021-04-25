
package com.github.zyxgad.loginext.error;

public class LoginError extends AccountBaseError{

	public static final LoginError UUID_IS_ALIVE   = new LoginError("Player's uuid is alive");
	public static final LoginError UUID_NOT_RECORD = new LoginError("Player's uuid isn't registered");
	public static final LoginError UUID_IS_WRONG   = new LoginError("Player's uuid is wrong");

	public LoginError(){
		super();
	}

	public LoginError(String reason){
		super(reason);
	}
}
