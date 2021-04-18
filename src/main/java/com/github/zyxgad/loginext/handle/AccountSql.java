
package com.github.zyxgad.loginext.handle;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;

import com.github.zyxgad.loginext.LoginExt;
import com.github.zyxgad.loginext.handle.ConfigReader;
import com.github.zyxgad.loginext.util.ErrorStackGetter;


public final class AccountSql{
	private static final AccountSql INSTANCE = new AccountSql();

	public static AccountSql getInstance(){
		return INSTANCE;
	}

	private String HOST = "";
	private int    PORT = 0;
	private Socket conn;

	private AccountSql(){}

	public void init(){
		final String[] lines = ConfigReader.getInstance().readFileLines("/config/sql_conf.txt");
		this.HOST = lines[0];
		this.PORT = Integer.parseInt(lines[1]);
		LoginExt.LOGGER.info("Sql address: " + this.HOST + ":" + this.PORT);
		try{
			this.conn = new Socket(this.HOST, this.PORT);
		}catch(IOException e){
			LoginExt.LOGGER.severe("Sql conn error:\n" + ErrorStackGetter.getErrorStack(e));
		}
	}

	public void close(){
		try{
			this.conn.close();
		}catch(IOException e){
			//
		}
		this.conn = null;
	}

	public Res getAccount(UUID uuid){
		return null;
	}

	public static class Res{
		public UUID uuid;
		public String username;
	}
}
