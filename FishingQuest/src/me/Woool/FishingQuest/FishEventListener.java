package me.Woool.FishingQuest;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class FishEventListener implements Listener {
	
	/*
	 * This is the class you can mess around with the most I guess mr Creeper Arti
	 * 
	 * 
	 */
	
	Random rand = new Random();
	
	@EventHandler
	public void onPlayerFish(PlayerFishEvent e) {
		
		Player plr = e.getPlayer();
		
		//If player caught a fish, removing the item.
		if (e.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
			
			e.getCaught().remove();
			
			// This basically checks if the player is in the designated area (so I can apply the special loot table)
			if (Utils.playerPhase.get(plr) == 1 && Utils.hasTeleported.get(plr) == true) {
				
				int random = rand.nextInt(100);

				// Random selection
				if (random <= 40) {

					// MAKE GOLD PLS ARTI
					plr.sendMessage(ChatColor.GOLD + "+100g" + ChatColor.GRAY + " from fishing!");
					// blatantly copy+pasted code
					ItemStack junk = new ItemStack(Material.GOLD_INGOT);
					ItemMeta junkmeta = junk.getItemMeta();
					junkmeta.setDisplayName(ChatColor.GOLD + "Not 100g");
					junk.setItemMeta(junkmeta);

					plr.getWorld().dropItem(plr.getLocation().add(new Vector(0,1,0)), junk);
				}
				else if(random <= 70) {

					plr.sendMessage(ChatColor.GRAY + "You found rubbish!");

					ItemStack junk = new ItemStack(Material.INK_SACK);
					ItemMeta junkmeta = junk.getItemMeta();
					junkmeta.setDisplayName(ChatColor.GRAY + "Rubbish");
					junk.setItemMeta(junkmeta);

					plr.getWorld().dropItem(plr.getLocation().add(new Vector(0,1,0)), junk);

				}
				else {

					ItemStack fish = new ItemStack(Material.RAW_FISH);
					fish.setDurability((short) 3);
					ItemMeta fishmeta = fish.getItemMeta();

					fishmeta.setDisplayName(ChatColor.GOLD + "Tropical Fish");
					fish.setItemMeta(fishmeta);

					plr.getWorld().dropItem(plr.getLocation().add(new Vector(0,1,0)), fish);

				}
				
				
			}
			else { 
				
				// Here's normal fishing, you can mess around with this in any way you'd like
				int random = rand.nextInt(100);

				if (random <= 60) {

					// MAKE GOLD PLS ARTI
					plr.sendMessage(ChatColor.GOLD + "+100g" + ChatColor.GRAY + " from fishing!");
					// blatantly copy+pasted code
					ItemStack junk = new ItemStack(Material.GOLD_INGOT);
					ItemMeta junkmeta = junk.getItemMeta();
					junkmeta.setDisplayName(ChatColor.GOLD + "Not 100g");
					junk.setItemMeta(junkmeta);

					plr.getWorld().dropItem(plr.getLocation().add(new Vector(0,1,0)), junk);

				}
				else {

					plr.sendMessage(ChatColor.GRAY + "You found rubbish");

					ItemStack junk = new ItemStack(Material.INK_SACK);
					ItemMeta junkmeta = junk.getItemMeta();
					junkmeta.setDisplayName(ChatColor.GRAY + "Rubbish");
					junk.setItemMeta(junkmeta);

					plr.getWorld().dropItem(plr.getLocation().add(new Vector(0,1,0)), junk);

				}
				
			}
			
		}

		
		
	}
	
}
