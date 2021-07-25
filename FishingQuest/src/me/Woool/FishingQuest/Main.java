package me.Woool.FishingQuest;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	// Cool little trick to get a static reference of your plugin
	private static Main instance;
	
	@Override
	public void onEnable() {
		instance = this;
		System.out.println("------------------------------------------------------------");
		System.out.println("Fishing Quest v1.0");
		System.out.println("Hi Arti, I tried my best, but I'm kind of new to OOP");
		System.out.println("So some of the things I did might seem quite inefficient or simply stupid");
		System.out.println("------------------------------------------------------------");
		
		this.getCommand("fishingquest").setExecutor(new FishingQuestCommand(this));
		this.getCommand("fcreate").setExecutor(new FCreate());
		saveDefaultConfig();
		
		getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
		getServer().getPluginManager().registerEvents(new FishEventListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerRightClickEntityListener(), this);
		
	}

	public static Main getInstance() {
		return instance;
	}
	
}
