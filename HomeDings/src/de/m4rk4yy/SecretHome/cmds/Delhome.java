package de.m4rk4yy.SecretHome.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.m4rk4yy.SecretHome.MethodManager;

public class Delhome implements CommandExecutor {

	MethodManager mm = new MethodManager();
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		Player p = (Player) cs;
		if(args.length == 0) {
			if(mm.deleteHome(p.getUniqueId(), "Home")) {
				p.sendMessage(mm.getPrefix() + "Dein Home wurde gel�scht.");
				return true;
			} else {
				p.sendMessage(mm.getErrorPrefix() + "Du hast kein Home gesetzt.");
				return true;
			}
		}
		
		if(args.length == 1) {
			String homename = args[0].toLowerCase();

			if(mm.deleteHome(p.getUniqueId(), homename)) {
				p.sendMessage(mm.getPrefix() + "Dein Home " + homename + " wurde gel�scht.");
				return true;
			} else {
				p.sendMessage(mm.getErrorPrefix() + "Das Home " + homename + " existiert nicht.");
			}
		}
		
		if(args.length > 1) {
			p.sendMessage(mm.getErrorPrefix() + "Syntax: /delhome [Name]");
			return true;
		}
		
		
		
		return true;
	}
}
