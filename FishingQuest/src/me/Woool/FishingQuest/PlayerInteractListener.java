package me.Woool.FishingQuest;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerInteractListener implements Listener {
	
	/*
	 * 
	 * Hello Arti! This class basically handles everything to do with the teleportation mechanic of the Rod.
	 * How it works:
	 * There's basically 2 states that the player can be in; those being "Teleported" and "Not Teleported"
	 * The rod teleports the player to the Objective of the current phase (of the quest) and then back to the return point using the
	 * Teleported&NotTPed state as a guide.
	 * I also use the 2 states in order to check if the player is in the objective area, since both Objective areas are secluded using barriers
	 * (This only applies for the special fishing part of the quest)
	 * 
	 */
	
	@EventHandler
	public void LeftClickRod(PlayerInteractEvent e) {
		
		Player plr = e.getPlayer();
		
		
		if ((e.getAction().equals(Action.LEFT_CLICK_AIR) && plr.isSneaking())) {
			
			
			if (plr.getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Time O'Rod")) {
				
				Utils.checkForLocations(plr);
				
				if (Utils.hasTeleported.get(plr) == false) {
					
					switch (Utils.playerPhase.get(plr)) {
					
					case 1:
						plr.teleport(Utils.ObjectiveA);
						plr.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "WARPED! " + ChatColor.GRAY + "You were warped by your Time O'Rod");
						plr.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 50, 2));
						plr.playSound(plr.getLocation(), Sound.ANVIL_LAND, 2, 0);
						
						break;
					case 2:
						plr.teleport(Utils.ObjectiveB);
						plr.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "WARPED! " + ChatColor.GRAY + "You were warped by your Time O'Rod");
						plr.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 50, 2));
						plr.playSound(plr.getLocation(), Sound.ANVIL_LAND, 2, 0);
						
						break;
						
					}
					
					Utils.hasTeleported.replace(plr, true);
					
				}
				else if (Utils.hasTeleported.get(plr) == true){
					
					plr.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "WARPED! " + ChatColor.GRAY + "You were warped by your Time O'Rod");
					plr.teleport(Utils.ReturnPoint);
					plr.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 50, 2));
					plr.playSound(plr.getLocation(), Sound.ANVIL_LAND, 2, 0);
					
					Utils.hasTeleported.replace(plr, false);
					
				}
				
				
			}
			
		}
		
	}
	
}
