package de.m4rk4yy.SecretHome.cmds;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.m4rk4yy.SecretHome.MethodManager;

public class Sethome implements CommandExecutor {

	MethodManager mm = new MethodManager();

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {

		Player p = (Player) cs;
		int countOfHomes = mm.homeCount(p.getUniqueId());
		int allowedHomes = 2;

		if (p.hasPermission("cmi.command.sethome.2") && !p.isOp() && !p.hasPermission("*"))
			allowedHomes = 2;
		if (p.hasPermission("cmi.command.sethome.8") && !p.isOp() && !p.hasPermission("*"))
			allowedHomes = 8;
		if (p.hasPermission("cmi.command.sethome.10") && !p.isOp() && !p.hasPermission("*"))
			allowedHomes = 10;
		if(p.hasPermission("*") || p.isOp())
			allowedHomes = 1000000;

		if (allowedHomes - countOfHomes == 0) {
			p.sendMessage(mm.getErrorPrefix() + "Maximale Anzahl an Homes erreicht.");
			return true;
		}

		if (args.length == 0) {
			Location loc = p.getLocation();

			World w = loc.getWorld();
			double x = loc.getX();
			double y = loc.getY();
			double z = loc.getZ();
			float yaw = loc.getYaw();
			float pitch = loc.getPitch();

			mm.writeToFile(p.getUniqueId(), "Home", w, x, y, z, yaw, pitch);
			p.sendMessage(mm.getPrefix() + "Home wurde gesetzt.");
			return true;
		}

		if (args.length == 1 && !args[0].contains("-")) {
			String homename = args[0].toLowerCase();
			Location loc = p.getLocation();

			World w = loc.getWorld();
			double x = loc.getX();
			double y = loc.getY();
			double z = loc.getZ();
			float yaw = loc.getYaw();
			float pitch = loc.getPitch();

			/*
			 * if(mm.nameExists(p.getUniqueId(), homename)) {
			 * p.sendMessage(mm.getErrorPrefix() + "Das Home " + homename +
			 * " existiert bereits."); return true; }
			 */

			mm.writeToFile(p.getUniqueId(), homename, w, x, y, z, yaw, pitch);
			p.sendMessage(mm.getPrefix() + "Home " + homename + " wurde gesetzt.");
			return true;
		}

		if (args.length > 1) {
			p.sendMessage(mm.getErrorPrefix() + "Syntax: /sethome [Name]");
			p.sendMessage(mm.getErrorPrefix() + "Hinweis: Homenames d�rfen kein Minus (-) beinhalten");
			return true;
		}
		return true;
	}

}
