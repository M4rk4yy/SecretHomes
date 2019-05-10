package de.m4rk4yy.SecretHome;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

public class MethodManager {

	public boolean checkForUnsafeTP(Location loc) {

		boolean isNotSafe = false;

		List<Material> blocksAround = getNearbyBlocks(loc, 1);
		ArrayList<Material> mats = new ArrayList<Material>();
		mats.add(Material.LAVA);
		mats.add(Material.WATER);
		mats.add(Material.MAGMA_BLOCK);
		mats.add(Material.FIRE);
		mats.add(Material.CACTUS);
		mats.add(Material.TRIPWIRE);
		

		for (int i = 0; i < blocksAround.size(); i++) {
			Material m = blocksAround.get(i);
			
			for (Material mat : mats) {
				if (m == mat) {
					isNotSafe = true;
				}
			}

		}

		Material[] blocksunder = new Material[5];

		for (int i = 1; i < blocksunder.length; i++) {
			blocksunder[i - 1] = loc.getWorld().getBlockAt((int) loc.getX(), (int) loc.getY() - i, (int) loc.getZ())
					.getType();
		}

		Material legs = loc.getWorld().getBlockAt(loc).getType();
		Material head = loc.getWorld().getBlockAt((int) loc.getX(), (int) loc.getY() + 1, (int) loc.getZ()).getType();

		if (blocksunder[0] == Material.CHEST) {
			isNotSafe = true;
		}
		
		boolean blockUnderisSafe = false;
		int dangerCounter = 0;
		for (int i = 0; i < blocksunder.length; i++) {

			if (mats.contains(blocksunder[i])) {
				blockUnderisSafe = true;
				
			}

			if (blocksunder[i] == Material.AIR) {
				dangerCounter++;
			}

		}
		
		if (blockUnderisSafe || dangerCounter > 3)
			isNotSafe = true;

		if (legs.isSolid() || head.isSolid()) {
			isNotSafe = true;
		}

		return isNotSafe;
	}

	public static List<Material> getNearbyBlocks(Location location, int radius) {
		List<Material> blocks = new ArrayList<Material>();
		for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
			for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
				for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
					blocks.add(location.getWorld().getBlockAt(x, y, z).getType());
				}
			}
		}
		return blocks;
	}

	public boolean deleteHome(UUID uuid, String name) {
		String uid = uuid.toString();
		File file = new File("plugins//SecretHomes", uid);
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		if (!nameExists(uuid, name)) {
			return false;
		} else {
			cfg.set(name, null);
			try {
				cfg.save(file);
				return true;
			} catch (IOException e) {
				return false;
			}

		}
	}

	public String[] getFromFile(UUID uuid, String name) {
		String uid = uuid.toString();
		File file = new File("plugins//SecretHomes", uid);
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
			}
		}

		Object[] x = cfg.getStringList(name).toArray();
		String[] a = new String[6];
		int i = 0;

		for (Object o : x) {
			a[i] = (String) o;
			i++;
		}

		return a;
	}

	public boolean nameExists(UUID uuid, String name) {
		String uid = uuid.toString();
		File file = new File("plugins//SecretHomes", uid);
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		Set<String> existingNames = cfg.getConfigurationSection("").getKeys(false);

		if (existingNames.contains(name))
			return true;
		else
			return false;
	}
	
	public int homeCount(UUID uuid) {
		String uid = uuid.toString();
		File file = new File("plugins//SecretHomes", uid);
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		Set<String> existingNames = cfg.getConfigurationSection("").getKeys(false);

		return existingNames.size();
	}

	public String[] getAllHomes(UUID uuid) {
		String uid = uuid.toString();
		File file = new File("plugins//SecretHomes", uid);
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		Set<String> nameSet = cfg.getConfigurationSection("").getKeys(false);

		String[] nameArray = new String[nameSet.size()];

		int i = 0;
		for (String s : nameSet)
			nameArray[i++] = s;

		return nameArray;
	}

	public void writeToFile(UUID uuid, String name, World w, double x, double y, double z, float yaw, float pitch) {
		String uid = uuid.toString();
		File file = new File("plugins//SecretHomes", uid);
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
			}
		}

		String[] list = { w.getName(), Double.toString(x), Double.toString(y), Double.toString(z), Float.toString(yaw),
				Float.toString(pitch) };

		cfg.set(name, list);

		try {
			cfg.save(file);
		} catch (IOException e) {

		}
	}
	
	public void writeToFileUnsaveTP(UUID uuid, String homename) {
		String uid = uuid.toString();
		File file = new File("plugins//SecretHomes", "UnsafeTPs");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
			}
		}
		
		Timestamp stamp = new Timestamp(System.currentTimeMillis());
		Date date = new Date(stamp.getTime());
		SimpleDateFormat dt = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		
		String formatted = dt.format(date);
		
		String[] homedata = this.getFromFile(uuid, homename);
		
		String[] write = {
			Bukkit.getPlayer(uuid).getName(),
			formatted,
			homename,
			homedata[0],
			homedata[1],
			homedata[2],
			homedata[3],
			homedata[4],
			homedata[5]
			
		};

		cfg.set(uuid.toString(), write);

		try {
			cfg.save(file);
		} catch (IOException e) {

		}
	}

	public String getPrefix() {
		return "§6SecretHome §7>> §3";
	}

	public String getErrorPrefix() {
		return "§6SecretHome §7>> §c";
	}
}
