
package com.github.zyxgad.loginext.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

import com.github.zyxgad.loginext.player.Account;
import com.github.zyxgad.loginext.util.ColorTextBuilder;


public class CommandRunner implements CommandExecutor{
  public CommandRunner(){}

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
    final String CMD_NAME = cmd.getName();
    if(CMD_NAME.equals("myinfo")){
      if(sender instanceof Player){
        Account player = Account.getAccountByPlayer((Player)sender);
        sender.sendMessage(new ColorTextBuilder()
          .add("Yours name is ").green(player.getName()).line()
          .add("Yours authority is ").blue(player.getLevel().getDis()).line()
          .toString());
      }
      return true;
    }
    if(CMD_NAME.equals("players")){
      int player_count = 0;
      ColorTextBuilder builder = new ColorTextBuilder();
      builder.line("Player list:");
      for(Account a: Account.getAllAccounts()){
        builder.add(a.getName()).line();
        player_count++;
      }
      builder.add("Player count: ").add(player_count).line();
      sender.sendMessage(builder.toString());
      return true;
    }
    return false;
  }

}
