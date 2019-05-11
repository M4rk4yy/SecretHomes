package de.m4rk4yy.SecretHome.cmds;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.m4rk4yy.SecretHome.MethodManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent.Action;

public class Home implements CommandExecutor {

	MethodManager mm = new MethodManager();

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {

		Player p = (Player) cs;

		if (args.length == 0) {

			if (!mm.nameExists(p.getUniqueId(), "Home")) {
				p.sendMessage(mm.getErrorPrefix() + "Du hast kein Home gesetzt.");
				return true;
			}

			String[] list = mm.getFromFile(p.getUniqueId(), "Home");

			World w = Bukkit.getServer().getWorld(list[0]);
			double x = Double.parseDouble(list[1]);
			double y = Double.parseDouble(list[2]);
			double z = Double.parseDouble(list[3]);
			float yaw = Float.parseFloat(list[4]);
			float pitch = Float.parseFloat(list[5]);

			Location loc = new Location(w, x, y, z, yaw, pitch);

			if (mm.checkForUnsafeTP(loc)) {
				writeUnsaveMessage(p);
				return true;
			}

			p.teleport(loc);
			p.sendMessage(mm.getPrefix() + "Du wurdest zum Home teleportiert.");
			return true;
		}

		if (args.length == 1 && !args[0].equalsIgnoreCase("-f")) {
			String homename = args[0].substring(0, 1).toUpperCase() + args[0].substring(1).toLowerCase();
			String[] list = mm.getFromFile(p.getUniqueId(), homename);

			if (!mm.nameExists(p.getUniqueId(), homename)) {
				p.sendMessage(mm.getErrorPrefix() + "Du hast kein Home " + homename + " gesetzt.");
				return true;
			}

			World w = Bukkit.getServer().getWorld(list[0]);
			double x = Double.parseDouble(list[1]);
			double y = Double.parseDouble(list[2]);
			double z = Double.parseDouble(list[3]);
			float yaw = Float.parseFloat(list[4]);
			float pitch = Float.parseFloat(list[5]);

			Location loc = new Location(w, x, y, z, yaw, pitch);

			if (mm.checkForUnsafeTP(loc)) {
				writeUnsaveMessage(p, homename);
				return true;
			}

			p.teleport(loc);
			p.sendMessage(mm.getPrefix() + "Du wurdest zum Home " + homename + " teleportiert.");
			return true;
		}

		if (args.length == 1 && args[0].equalsIgnoreCase("-f")) {
			mm.writeToFileUnsaveTP(p.getUniqueId(), "Home");
			teleportHome(p);
			return true;
		}

		if (args.length == 2 && args[1].equalsIgnoreCase("-f")) {
			String homename = args[0].substring(0, 1).toUpperCase() + args[0].substring(1).toLowerCase();
			mm.writeToFileUnsaveTP(p.getUniqueId(), homename);
			teleportHome(p, homename);
			return true;
		}

		return true;

	}

	public void writeUnsaveMessage(Player p) {
		p.sendMessage("§4WARNUNG: §cDein Home wurde vom Plugin als unsicher eingestuft. "
				+ "Dies passiert, wenn sich Lava, Druckplatten oder Ähnliches um den Homepunkt befinden. "
				+ "Wenn du dir sicher bist, dass das Home sicher ist, klicke unten auf den Link. "
				+ "Solltest du trotz dieser Warnung dich teleportieren und Sterben werden wir keine "
				+ "Gegenstände oder Ähnliches ersetzen.");
		p.sendMessage("");
		
		TextComponent msg = new TextComponent("§4§lTROTZDEM TELEPORTIEREN");
		
		msg.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/home -f"));
		msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT
				, new ComponentBuilder("/home")
				.color(ChatColor.DARK_AQUA).create()));
		
		p.spigot().sendMessage(msg);

	}
	
	public void writeUnsaveMessage(Player p, String homename) {
		p.sendMessage("§4WARNUNG: §cDein Home wurde vom Plugin als unsicher eingestuft. "
				+ "Dies passiert, wenn sich Lava, Druckplatten oder Ähnliches um den Homepunkt befinden. "
				+ "Wenn du dir sicher bist, dass das Home sicher ist, klicke unten auf den Text. "
				+ "Solltest du trotz dieser Warnung dich teleportieren und Sterben werden wir keine "
				+ "Gegenstände oder Ähnliches ersetzen.");
		p.sendMessage("");
		
		TextComponent msg = new TextComponent("§4§lTROTZDEM TELEPORTIEREN");
		
		msg.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/home " + homename + " -f"));
		msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT
				, new ComponentBuilder("/home " + homename)
				.color(ChatColor.RED).create()));
		p.spigot().sendMessage(msg);

	}

	public void teleportHome(Player p) {

		String[] list = mm.getFromFile(p.getUniqueId(), "Home");

		World w = Bukkit.getServer().getWorld(list[0]);
		double x = Double.parseDouble(list[1]);
		double y = Double.parseDouble(list[2]);
		double z = Double.parseDouble(list[3]);
		float yaw = Float.parseFloat(list[4]);
		float pitch = Float.parseFloat(list[5]);

		Location loc = new Location(w, x, y, z, yaw, pitch);

		p.teleport(loc);
		p.sendMessage(mm.getPrefix() + "Du wurdest zum Home teleportiert.");

	}

	public void teleportHome(Player p, String homename) {

		String[] list = mm.getFromFile(p.getUniqueId(), homename);

		World w = Bukkit.getServer().getWorld(list[0]);
		double x = Double.parseDouble(list[1]);
		double y = Double.parseDouble(list[2]);
		double z = Double.parseDouble(list[3]);
		float yaw = Float.parseFloat(list[4]);
		float pitch = Float.parseFloat(list[5]);

		Location loc = new Location(w, x, y, z, yaw, pitch);

		p.teleport(loc);
		p.sendMessage(mm.getPrefix() + "Du wurdest zum Home " + homename + " teleportiert.");

	}

}
