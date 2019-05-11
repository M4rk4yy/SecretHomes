package de.m4rk4yy.SecretHome;

import java.io.File;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.m4rk4yy.SecretHome.cmds.Delhome;
import de.m4rk4yy.SecretHome.cmds.Home;
import de.m4rk4yy.SecretHome.cmds.Homes;
import de.m4rk4yy.SecretHome.cmds.Sethome;

public class Main extends JavaPlugin{

	
	@Override
	public void onDisable() {
		
	}

	@Override
	public void onEnable() {
		createFiles();
		registerCommands();
		registerListeners();
	}
	
	private void createFiles() {
		File ordner = new File("plugins//SecretHomes");
		
		if(!ordner.exists()) {
			ordner.mkdir();
		}
	}
	

	
	private void registerListeners() {
		PluginManager pm = this.getServer().getPluginManager();	
	}

	private void registerCommands() {
		this.getCommand("sethome").setExecutor(new Sethome());
		this.getCommand("home").setExecutor(new Home());
		this.getCommand("homes").setExecutor(new Homes());
		this.getCommand("delhome").setExecutor(new Delhome());
	}
	
	
	

}
