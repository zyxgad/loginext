
package com.github.zyxgad.loginext.error;

public class AccountBaseError extends Exception{

	private String reason;

	public AccountBaseError(){
		super();
		this.reason = "Unknow reason";
	}

	public AccountBaseError(String reason){
		super(reason);
		this.reason = reason;
	}

	public String getReason(){
		return this.reason;
	}
}
