package me.Woool.FishingQuest;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FishingQuestCommand implements CommandExecutor {
	
	public Utils utils = new Utils();
	private final Main plugin;
	
	public FishingQuestCommand(Main plugin) {
		this.plugin = plugin;
	}
	
	
	// Dialogue
	
	public void promptFishermanDialogue(Player p, int delay, String msg) {
		
        new BukkitRunnable() {
            
            @Override
            public void run() {
            	
            	p.playSound(p.getLocation(), Sound.VILLAGER_HAGGLE, 2, 1f);
            	p.sendMessage(ChatColor.AQUA + "[NPC] Fisherman: " + ChatColor.RESET + msg);
            	
            }
            
        }.runTaskLater(this.plugin, (40*delay));
		
	}
	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		
		if (sender instanceof Player) {
			
			Player plr = (Player) sender;
			
			//Initial Check for all the locations (These are needed for the teleporting mechanic that I'm using!)
			boolean checkSuccessful = Utils.checkForLocations(plr);
			if (checkSuccessful == true) {
				//continue!
			}
			else {
				
				plr.sendMessage(ChatColor.RED + "Sorry! Not all locations have been specified on this World.");
				plr.sendMessage(ChatColor.RED + "If you are an Admin, please contact Woool!");
				return true;
				
			}
			
			// Checking if a player is in the playerPhase HashMap 
			// I'm also treating this as the "quest start", since they've obviously not started the quest yet if they're not in the main variable
			if (Utils.playerPhase.get(plr) == null) {
				
				//This is for the fishing rod item
				if (!(Utils.hasAvaliableSlot(plr) == true)) {
					plr.sendMessage(ChatColor.RED + "Clear some inventory space first!");
					return true;
				}
				
				Utils.playerPhase.put(plr, 1);
				Utils.hasTeleported.put(plr, false);

				
				//Dialogue #1
				
				/*
				 * This is for the most part just a bunch of dialogue!
				 * 
				 * 
				 */
				String[] dialogue1 = {"Ey! Watch ye’ step kid! Sold all ‘f my stuff so I could fish in peace ‘nd yet all you-!",
						"--Leave it, ‘f you got no business with me, take ya leave now.", "Eh? You sayin’ ya got lost ‘n this time period? ",
						"Fishin’s much more than it seems, ya know. I’ll get ya outta here in no time if you gi’me some help.",
						"Take this fishin’ rod, it’s much more special than it seems.",
						"For now, get yer hands on som’" + ChatColor.GOLD + " tropical fish"};
				
				for (int i = 0; i < dialogue1.length; i++) {
					
					this.promptFishermanDialogue(plr, i, dialogue1[i]);
					
				}
				
		        new BukkitRunnable() {
		        	
		        	public void run() {
		        		
		        		utils.givePlayerRod(plr);
		        		
		        	}
		        	
		        	
		        }.runTaskLaterAsynchronously(plugin, 4*40);
				
		        new BukkitRunnable() {
		            
		            @Override
		            public void run() {
		            	
		            	Utils.sendTitle(plr, ChatColor.DARK_PURPLE + "Quest Started", ChatColor.WHITE + "Fisherman Troubles!", 10, 30, 15);
						plr.playSound(plr.getLocation(), Sound.LEVEL_UP, 2, 1f);
						
		            }
		            
		        }.runTaskLater(this.plugin, (40*5+5));
		        
		        
				
				
				return true;
			}
			

			
			// I use a hashmap with the player's 'phase' to dictate what dialogue is supposed to be playing and what the
			// plugin is supposed to be checking for.
			switch (Utils.playerPhase.get(plr)) {
			
			case 1:
				
				if (plr.getItemInHand().getItemMeta() != null && plr.getItemInHand().getItemMeta().equals(Utils.getTropicalFish().getItemMeta())) {
					
					if (plr.getItemInHand().getAmount() > 1) {
						plr.getItemInHand().setAmount(plr.getItemInHand().getAmount() - 1);
					}
					else {
						plr.getInventory().removeItem(Utils.getTropicalFish());
					}

					plr.sendMessage(ChatColor.GREEN + "Item taken!");
					
				}
				else {
					
					plr.sendMessage(ChatColor.AQUA + "[NPC] Fisherman: " + ChatColor.RESET + "Ey? What 're you doing 'ere?! I asked ya for somethin' didn't I?");
					plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 2, 1f);
					plr.sendMessage(ChatColor.GRAY + "Hold the tropical fish in your hand, if you have it!");
					return true;
					
				}
				
				

				// Since I return true whenever the player doesn't have the item, anything under here won't trigger
				// Dialogue #2	
				
				String[] dialogue2 = {"Oh? Ya actually did it? G-Good job, kiddo.",
						"Now let’s see, hrm -erm..",
						"...",
						"Uh- Ah- Be a good lad and brin’ me som’ " + ChatColor.YELLOW + "dried fish" + ChatColor.RESET + ", will ya?.",
						};
				
				for (int i = 0; i < dialogue2.length; i++) {
					
					this.promptFishermanDialogue(plr, i, dialogue2[i]);
					
				}
				
				// Setting the player's objective to #2
		        
		        Utils.playerPhase.replace(plr, 2);
		        
				break;
			case 2:
				
				
				if (plr.getItemInHand().getItemMeta() != null && plr.getItemInHand().getItemMeta().equals(Utils.getDriedFish().getItemMeta())) {
					
					if (plr.getItemInHand().getAmount() > 1) {
						plr.getItemInHand().setAmount(plr.getItemInHand().getAmount() - 1);
					}
					else {
						plr.getInventory().removeItem(plr.getItemInHand());
					}
					
					plr.sendMessage(ChatColor.GREEN + "Item taken!");
					
				}
				else {
					
					plr.sendMessage(ChatColor.AQUA + "[NPC] Fisherman: " + ChatColor.RESET + "Ey! Stop lazin' 'round and get me what I asked for!");
					plr.sendMessage(ChatColor.GRAY + "(You must hold the Dried Fish in your hand)");
					return true;
					
				}
				
				
				// Dialogue #3
				
				String[] dialogue3 = {"Aaaaah- I give up! I was rude, I was annoying and yet-- Why won’t you let me fish in peace?? Do you know how hard it was to speak like that?",
						"What will make you go away? Uh, here just take all my gold, and this fishing rod. I don’t want them, just please go away!"};
				
				for (int i = 0; i < dialogue3.length; i++) {
					
					this.promptFishermanDialogue(plr, i, dialogue3[i]);
					
				}
				
				new BukkitRunnable() {
		            
		            @Override
		            public void run() {
		            	
		            	plr.playSound(plr.getLocation(), Sound.LEVEL_UP, 2, 1f);
		            	plr.sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Quest Complete: " + ChatColor.RESET + "Fisherman troubles");
		            	plr.sendMessage(ChatColor.LIGHT_PURPLE + "Rewards: ");
		            	plr.sendMessage(ChatColor.RED + "- Combat Rod");
		            	plr.sendMessage(ChatColor.AQUA + "- +5,000XP");
		            	plr.sendMessage(ChatColor.GOLD + "- +5,000g");
		            	
		            }
		            
		        }.runTaskLater(this.plugin, (70));
		        
		        Utils.playerPhase.replace(plr, 3);
		        
				break;
		        
			case 3:
				
				plr.sendMessage(ChatColor.GRAY + "You remember what happened last time you talked to the Fisherman. You decide it's best not to disturb him anymore.");;
				
				break;
			}
		
			
		}
		else {
			
			sender.sendMessage("Unfortunately, only players have access to this command");
			
		}
		
		
		return true;
	}

}
