
package com.github.zyxgad.loginext.player;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.bukkit.entity.Player;

import com.github.zyxgad.loginext.error.AccountBaseError;
import com.github.zyxgad.loginext.error.LoginError;
import com.github.zyxgad.loginext.player.Level;
import com.github.zyxgad.loginext.handle.AccountSql;


public final class Account{

	private static final Map<UUID, Account> ACCOUNT_MAP = new TreeMap<>();
	private static final Lock mapLock = new ReentrantLock();

	public static void clearAccounts(){
		ACCOUNT_MAP.clear();
	}

	public static boolean hasUUID(UUID uuid){
		return ACCOUNT_MAP.containsKey(uuid);
	}

	public static boolean hasAccount(Account account){
		return hasUUID(account.getUUID());
	}

	public static boolean hasPlayer(Player player){
		return hasUUID(player.getUniqueId());
	}

	private static boolean putAccount(Account account){
		if(hasAccount(account)){
			return false;
		}
		mapLock.lock();
		ACCOUNT_MAP.put(account.getUUID(), account);
		mapLock.unlock();
		return true;
	}

	public static Account getAccountByPlayer(Player player){
		return getAccountByUUID(player.getUniqueId());
	}

	public static Account getAccountByUUID(UUID uuid){
		return ACCOUNT_MAP.get(uuid);
	}

	public static Collection<Account> getAllAccounts(){
		return ACCOUNT_MAP.values();
	}

	public static void removeAccountByUUID(UUID uuid){
		mapLock.lock();
		ACCOUNT_MAP.remove(uuid);
		mapLock.unlock();
	}

	public static void removeAccount(Account account){
		removeAccountByUUID(account.getUUID());
	}

	public static void removePlayer(Player player){
		removeAccountByUUID(player.getUniqueId());
	}


	private final UUID uuid;
	private final String name;
	private Level level;
	private Player player;

	private Account(UUID uuid){
		AccountSql.Res res = AccountSql.getInstance().getAccount(uuid);
		if(res == null){
			// return;
		}

		this.uuid = uuid;
		this.name = "steve";//res.username;
		this.level = Level.PLAYER;
		this.player = null;
	}

	public UUID getUUID(){
		return this.uuid;
	}

	public String getName(){
		return this.name;
	}

	public Level getLevel(){
		return this.level;
	}

	public void setLevel(Level level){
		this.level = level;
	}

	public Player getPlayer(){
		return this.player;
	}

	private void setPlayer(Player player){
		this.player = player;
	}

	public static Account registerUUID(UUID uuid) throws AccountBaseError{
		if(hasUUID(uuid)){
			throw LoginError.UUID_IS_ALIVE;
		}
		Account account = new Account(uuid);
		putAccount(account);
		return account;
	}

	public static Account registerPlayer(Player player) throws AccountBaseError{
		Account account = getAccountByUUID(player.getUniqueId());
		if(account == null){
			throw LoginError.UUID_NOT_RECORD;
		}
		if((!account.getUUID().equals(player.getUniqueId())) || (account.getPlayer() != null)){
			throw LoginError.UUID_IS_WRONG;
		}
		account.setPlayer(player);
		return account;
	}

}