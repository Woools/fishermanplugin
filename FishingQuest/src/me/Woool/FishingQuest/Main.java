package me.Woool.FishingQuest;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	
	@Override
	public void onEnable() {
		
		System.out.println("------------------------------------------------------------");
		System.out.println("Fishing Quest v1.0");
		System.out.println("Hi Arti, I tried my best, but I'm kind of new to OOP");
		System.out.println("So some of the things I did might seem quite inefficient or simply stupid");
		System.out.println("------------------------------------------------------------");
		
		this.getCommand("fishingquest").setExecutor(new FishingQuestCommand(this));
		this.getCommand("fcreate").setExecutor(new FCreate());
		
		getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
		getServer().getPluginManager().registerEvents(new FishEventListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerRightClickEntityListener(), this);
		
	}
	
}
