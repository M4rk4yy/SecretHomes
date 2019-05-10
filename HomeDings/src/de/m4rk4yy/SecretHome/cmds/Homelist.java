package de.m4rk4yy.SecretHome.cmds;

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

public class Homelist implements CommandExecutor{
	
	MethodManager mm = new MethodManager();

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		Player p = (Player) cs;
		
		if(args.length > 0) {
			p.sendMessage(mm.getErrorPrefix() + "Syntax: /homelist");
			return true;
		}
		
		if(args.length == 0) {
			String[] list = mm.getAllHomes(p.getUniqueId());
			p.sendMessage(mm.getPrefix() + "Deine Homes [Klick für TP]:");
			
			for (String s : list) {
				if(s.equals("home")) continue;

				TextComponent prefix = new TextComponent(mm.getPrefix());
				TextComponent msg = new TextComponent(s);
				
				msg.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/home " + s));
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
