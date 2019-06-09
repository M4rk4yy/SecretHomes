package de.m4rk4yy.SecretHome.cmds;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.m4rk4yy.SecretHome.MethodManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Homes implements CommandExecutor{
	
	MethodManager mm = new MethodManager();

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		Player p = (Player) cs;
		
		if(args.length > 1) {
			p.sendMessage(mm.getErrorPrefix() + "Syntax: /homes [Spielername]");
			return true;
		}
		
		if(args.length == 0) {
			String[] list = mm.getAllHomes(p.getUniqueId());
			p.sendMessage(mm.getPrefix() + "Deine Homes [Klick für TP]:");
			
			for (String s : list) {
			
				String homename = s.toLowerCase();
				TextComponent prefix = new TextComponent(mm.getPrefix());
				TextComponent msg = new TextComponent(s);
				
				if(homename.equalsIgnoreCase("Home")) {
					msg.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/home"));
					msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT
							, new ComponentBuilder("Klicken zum TP")
							.color(ChatColor.DARK_AQUA).create()));
					
					prefix.addExtra(msg);
					p.spigot().sendMessage(prefix);
					continue;
				}
				
				msg.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/home " + homename));
				msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT
						, new ComponentBuilder("Klicken zum TP")
						.color(ChatColor.DARK_AQUA).create()));
				
				prefix.addExtra(msg);
				p.spigot().sendMessage(prefix);
				
			}
			
			return true;
			
			
		}
		
		if(args.length == 1) {
			
			if(!p.hasPermission("secretcraft.homes.other") && !p.isOp() && !p.hasPermission("*")) {
				p.sendMessage(mm.getErrorPrefix() + "Keine Permission");
				return true;
			}
			
			Player test = Bukkit.getPlayerExact(args[0]);
			OfflinePlayer t = null;
			
			if(test == null) {
				t = Bukkit.getOfflinePlayer(args[0]);
			}
			
			if(!t.hasPlayedBefore()) {
				p.sendMessage(mm.getErrorPrefix() + "Der Spieler " + args[0] + " hat noch nie gespielt.");
				return true;
			}

			
			if(!mm.userExists(t.getUniqueId())) {
				p.sendMessage(mm.getErrorPrefix() + "Der Spieler " + args[0] + " hat keine Homes");
				return true;
			}
			
			String[] list = mm.getAllHomes(t.getUniqueId());
			p.sendMessage(mm.getPrefix() + t.getName() + "'s Homes [Klick für TP]:");
			
			for (String s : list) {
			
				String homename = s.toLowerCase();
				TextComponent prefix = new TextComponent(mm.getPrefix());
				TextComponent msg = new TextComponent(s);
				
				if(homename.equalsIgnoreCase("Home")) {
					msg.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/home -o " + t.getName()));
					msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT
							, new ComponentBuilder("Klicken zum TP")
							.color(ChatColor.DARK_AQUA).create()));
					
					prefix.addExtra(msg);
					p.spigot().sendMessage(prefix);
					continue;
				}
				
				msg.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/home -o " + t.getName() + " " + homename));
				msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT
						, new ComponentBuilder("Klicken zum TP")
						.color(ChatColor.DARK_AQUA).create()));
				
				prefix.addExtra(msg);
				p.spigot().sendMessage(prefix);
				
			}
			
			return true;
			
			
		}
		
		
		
		
		
		
		return true;
		
		
	}
	

}
