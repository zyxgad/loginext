
package com.github.zyxgad.loginext;

import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.zyxgad.loginext.command.CommandRunner;
import com.github.zyxgad.loginext.error.AccountBaseError;
import com.github.zyxgad.loginext.handle.AccountSql;
import com.github.zyxgad.loginext.player.Account;
import com.github.zyxgad.loginext.player.PlayerStatusListener;


public final class LoginExt extends JavaPlugin{
	public static LoginExt INSTANCE = null;
	public static Logger LOGGER = null;

	public static final String PLUGIN_NAME = "LoginExt";

	public LoginExt(){}

	@Override
	public void onLoad(){
		if(LoginExt.INSTANCE != this){
			LoginExt.INSTANCE = this;
			LoginExt.LOGGER = this.getLogger();
		}
		LOGGER.info("Login extend is on load");

		AccountSql.getInstance().init();
	}

	@Override
	public void onEnable(){
		LOGGER.info("Login extend is on enable");

		Account.clearAccounts();
		for(Player p: Bukkit.getOnlinePlayers()){
			try{
				Account.registerUUID(p.getUniqueId());
				Account.registerPlayer(p);
			}catch(AccountBaseError e){
				p.kickPlayer(e.getReason());
				continue;
			}
		}

		this.addListener(Bukkit.getPluginManager());
		this.bindCommands();
	}

	@Override
	public void onDisable(){
		LOGGER.info("Login extend is on disable");

		LoginExt.INSTANCE = null;
		LoginExt.LOGGER = null;
	}

	private void addListener(PluginManager manager){
		manager.registerEvents(new PlayerStatusListener(), this);
	}

	private void bindCommands(){
		CommandRunner cmdrunner = new CommandRunner();
		this.getCommand("myinfo").setExecutor(cmdrunner);
		this.getCommand("players").setExecutor(cmdrunner);
	}
}