package me.Woool.FishingQuest;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;

public class Utils {
	
	public Utils() {
		super();
	}
	
	// Itemstackers!
	
	public static ItemStack getTropicalFish() {
		
		ItemStack fish = new ItemStack(Material.RAW_FISH);
		fish.setDurability((short) 3);
		ItemMeta fishmeta = fish.getItemMeta();
		
		fishmeta.setDisplayName(ChatColor.GOLD + "Tropical Fish");
		fish.setItemMeta(fishmeta);
		
		return fish;
	}
	
	public static ItemStack getDriedFish() {
		
		ItemStack fish = new ItemStack(Material.COOKED_FISH);
		ItemMeta fishmeta = fish.getItemMeta();
		
		fishmeta.setDisplayName(ChatColor.YELLOW + "Dried Fish");
		fish.setItemMeta(fishmeta);
		
		return fish;
	}
	
	// Player Phase will be used in a switch case
	public static HashMap<Player, Integer> playerPhase  = new HashMap<Player, Integer>();
	
	
	// In order to know where to teleport the player (ReturnPoint or current objective??)
	public static HashMap<Player, Boolean> hasTeleported = new HashMap<Player, Boolean>();
	
	// Locations - Needed for Setup
	public static Location ObjectiveA;
	public static Location ObjectiveB;
	public static Location ReturnPoint;
	
	// Methods
	
	//Gives player the all-mighty Time o'Rod
	
	public void givePlayerRod(Player p) {
		
		p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "NOTICE! " + ChatColor.GRAY + "Spigot, for some reason, views casting your rod the same way as a left click. (Don't ask me-- I'm also clueless!) This means that (as of right now) shifting and casting your rod will teleport you in the same way that left-clicking and shifting does. Please keep that in mind, and I apologise for this!");
		ItemStack rod = new ItemStack(Material.FISHING_ROD);
		ItemMeta rodmeta = rod.getItemMeta();
		rodmeta.setDisplayName(ChatColor.AQUA + "Time O'Rod");
		
		rodmeta.spigot().setUnbreakable(true);
		
		ArrayList<String> lore = new ArrayList<String>();
		
		lore.add(ChatColor.LIGHT_PURPLE + "Quest Item");
		lore.add(ChatColor.GRAY + "Kept on death");
		lore.add("");
		lore.add(ChatColor.BLUE + "Fisherman's Secret");
		lore.add(ChatColor.GRAY + "Shift Left-clicking teleports you");
		lore.add(ChatColor.GRAY + "to your current objective");
		
		rodmeta.setLore(lore);
		rod.setItemMeta(rodmeta);
		
		p.getInventory().addItem(rod);
		
	}
	
	//Checks if player has available slots
	public static boolean hasAvaliableSlot(Player player){
		
	    Inventory inv = player.getInventory();
	    
	    for (ItemStack item: inv.getContents()) {
	    	
	         if(item == null) {
	        	 
	                 return true;
	                 
	         }
	         
	     }
	    
	return false;
	
	}
	

	
	//Yoinked from Arti lol (tysm)
	/**
	 * Sends a title to the player with designated title and subtitle
	 * @param player Player
	 * @param title Title
	 * @param subtitle Subtitle
	 * @param fadeIn Time it takes to fade in in ticks
	 * @param stay Time it stays on the screen in ticks
	 * @param fadeOut Time it takes to fade out in ticks
	 */
	public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
		PacketPlayOutTitle titlepacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, ChatSerializer.a("{\"text\": \"" + title + "\"}"));
		PacketPlayOutTitle subtitlepacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, ChatSerializer.a("{\"text\": \"" + subtitle + "\"}"));
		PacketPlayOutTitle length = new PacketPlayOutTitle(fadeIn, stay, fadeOut);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(titlepacket);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(length);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(subtitlepacket);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(length);
	}
	
	//Checks if all Location Armor Stands are in the Player's World, then proceeds to set the locations.
	public static boolean checkForLocations(Player p) {
		
		int Locations = 0;
		
		for (Entity ent : p.getWorld().getEntities()) {
			
			if (ent.getType().equals(EntityType.ARMOR_STAND)) {
				
				if (ent.getCustomName().equals("ObjectiveA")) {
					
					Locations += 1;
					
					Utils.ObjectiveA = ent.getLocation();
					
				}
				else if (ent.getCustomName().equals("ObjectiveB")) {
					
					Locations += 1;
					
					Utils.ObjectiveB = ent.getLocation();
					
				}
				else if (ent.getCustomName().equals("ReturnPoint")) {
					
					Locations += 1;
					
					Utils.ReturnPoint = ent.getLocation();
					
				}
				
			}
			
		}
		
		if (Locations == 3) {
			
			return true;
			
		}
		else {
			
			p.sendMessage("Locations not specified");
			return false;
			
		}
		
		
	}
}
