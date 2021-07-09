package me.Woool.FishingQuest;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerRightClickEntityListener implements Listener {
	
	public Utils utils = new Utils();
	
	@EventHandler
	public void rightClickEntity(PlayerInteractAtEntityEvent e) {
		
		Player plr = e.getPlayer();
		Entity clicked = e.getRightClicked();
		
		if (clicked.getCustomName().equals("DriedFish")) {
			
			e.setCancelled(true);
			
			if (Utils.playerPhase.get(plr) == null) {
				
				plr.sendMessage(ChatColor.GRAY + "You might want to talk to the Fisherman if you want to find out what this is about!");
				return;
				
			}
			
			if (Utils.playerPhase.get(plr) == 2) {
				
				ItemStack fish = new ItemStack(Material.COOKED_FISH);
				ItemMeta fishmeta = fish.getItemMeta();
				
				fishmeta.setDisplayName(ChatColor.YELLOW + "Dried Fish");
				fish.setItemMeta(fishmeta);
				
				if (Utils.hasAvaliableSlot(plr) == true) {
					
					plr.getInventory().addItem(fish);
					plr.sendMessage(ChatColor.GREEN + "Dried fish obtained!");
					
				}
				else { 
					
					plr.getWorld().dropItem(plr.getLocation(), fish);
					plr.sendMessage(ChatColor.RED + "No space in inventory! Dried fish dropped!");
					
				}
				
			}
			
		}
		
	}
	
}
