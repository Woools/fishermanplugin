package me.Woool.FishingQuest;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FCreate implements CommandExecutor {
	
	public void createPlaceholder(String name, Player plr, boolean isFish) {
		
		ArmorStand as = (ArmorStand) plr.getWorld().spawnEntity(plr.getLocation(), EntityType.ARMOR_STAND);
		as.setCustomName(name);
		as.setCustomNameVisible(true);
		as.setGravity(false);
		as.setVisible(false);
		// Apparently there's no as.setInvulnerable(true); in 1.8 LOL
		// Due to this, I'm setting the ArmorStand's health to the integer max limit. 
		as.setMaxHealth(2147483647);
		as.setHealth(as.getMaxHealth());
		
		if (isFish) {
			
			as.setItemInHand(new ItemStack(Material.COOKED_FISH));
			
		}
		
		plr.sendMessage(ChatColor.GREEN + name + " has been initiated");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		// Only players can use this!
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players have access to this command");
			return true;
		}
		// Commands needs an extra argument
		if (args.length == 0) {
			return false;
		}
		
		Player plr = (Player) sender;
		
		/*
		 *  Depending on argument [0], it spawns different armorstands. Apart from "fish" which is for phase 2 of the quest, all of the armorstands
		 *  have something to do with the teleporting mechanic
		 *  
		 */
		
		if (args[0].equalsIgnoreCase("a")) {
			
			this.createPlaceholder("ObjectiveA", plr, false);
			
		}
		else if (args[0].equalsIgnoreCase("b")) {
			
			this.createPlaceholder("ObjectiveB", plr, false);
			
		}
		else if (args[0].equalsIgnoreCase("return")) {
			
			this.createPlaceholder("ReturnPoint", plr, false);
			
		}
		else if (args[0].equalsIgnoreCase("fish")) {
			
			this.createPlaceholder("DriedFish", plr, true);
			
		}
		
		
		return true;
	}

}
