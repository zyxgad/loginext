
package com.github.zyxgad.loginext.player;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.zyxgad.loginext.LoginExt;
import com.github.zyxgad.loginext.error.AccountBaseError;
import com.github.zyxgad.loginext.player.Account;
import com.github.zyxgad.loginext.util.Regexp;
import com.github.zyxgad.loginext.util.ColorTextBuilder;


public final class PlayerStatusListener implements Listener{
	public PlayerStatusListener(){}

	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event){
		UUID uuid = event.getUniqueId();

		Account account = null;
		try{
			account = Account.registerUUID(uuid);
		}catch(AccountBaseError e){
			event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, e.getReason());
			return;
		}
	}

	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerLogin(PlayerLoginEvent event){
		Account account = null;
		try{
			account = Account.registerPlayer(event.getPlayer());
		}catch(AccountBaseError e){
			event.disallow(PlayerLoginEvent.Result.KICK_OTHER, e.getReason());
			return;
		}

		if(!Regexp.testUsername(account.getName())){
			event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "Username is illegality");
			return;
		}
	}

	@EventHandler(priority=EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent event){
		Account account = Account.getAccountByPlayer(event.getPlayer());

		account.getPlayer().sendRawMessage(new ColorTextBuilder()
			.line("=====================================================")
			.add("--Hello ").green(account.getName()).line()
			.line("--Welcome to play on this server")
			.line("--Have a nice day!")
			.add("=====================================================")
			.toString());

		event.setJoinMessage(new ColorTextBuilder()
			.blue("[SERVER] ")
			.green(account.getPlayer().getName())
			.blue(" is wake up")
			.toString());
	}

	@EventHandler(priority=EventPriority.NORMAL)
	public void onPlayerLeave(PlayerQuitEvent event){
		Account account = Account.getAccountByPlayer(event.getPlayer());

		if(!Account.hasAccount(account)){
			return;
		}

		event.setQuitMessage(new ColorTextBuilder()
			.blue("[SERVER] ")
			.green(account.getPlayer().getName())
			.blue(" is go to sleep")
			.toString());

		Account.removeAccount(account);
	}


	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerChat(AsyncPlayerChatEvent event){
		Account account = Account.getAccountByPlayer(event.getPlayer());

		event.setFormat(new ColorTextBuilder()
			.add('[').yellow(account.getLevel().getDis())
			.add('/').green(account.getPlayer().getName()).add("]: ")
			.add("%2$s")
			.toString());
	}
}