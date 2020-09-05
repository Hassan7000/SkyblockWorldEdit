package me.enchantbook.skyblockworldedit.Listeners;

import me.enchantbook.skyblockworldedit.Handlers.InventoryHandler;
import me.enchantbook.skyblockworldedit.Nbt.NBTItem;
import me.enchantbook.skyblockworldedit.SkyblockWorldEdit;
import me.enchantbook.skyblockworldedit.Utils.CompatUtils;
import me.enchantbook.skyblockworldedit.Utils.ItemBuilder;
import me.enchantbook.skyblockworldedit.Utils.LocationsUtil;
import me.enchantbook.skyblockworldedit.Utils.Methods;
import net.brcdev.shopgui.ShopGuiPlusApi;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Commands implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd , String label, String args[]) {
        
        
        if (cmd.getName().equalsIgnoreCase("wand")) {
        	

            if (args.length < 1) {
                for (String s : SkyblockWorldEdit.getInstance().language.getConfiguration().getStringList("commands.SkyblockWand.help")) {
                    sender.sendMessage(Methods.color(s));
                }
            }  else if(args.length == 1) {

                if (args[0].equalsIgnoreCase("list")) {
                    InventoryHandler handler = new InventoryHandler();
                    
                } else {
                    for (String s : SkyblockWorldEdit.getInstance().language.getConfiguration().getStringList("commands.SkyblockWand.help")) {
                        sender.sendMessage(Methods.color(s));
                    }
                }

            } else if (args.length == 3) {
                if (args[0].equalsIgnoreCase("set")) {
                	Player p = (Player) sender;
                	int data = 0;
                  //  SkyblockWorldEdit.getInstance().getSMethods().set(p, Material.STONE, data, new ItemStack(Material.AIR));
                    return true;
                }else if (!args[0].equalsIgnoreCase("give")
                        || Bukkit.getPlayer(args[1]) == null) {
                    for (String s : SkyblockWorldEdit.getInstance().language.getConfiguration().getStringList("commands.SkyblockWand.help")) {
                        sender.sendMessage(Methods.color(s));
                    }
                    return true;
                }


                Player target = Bukkit.getPlayer(args[1]);

                Material material = Material.matchMaterial(SkyblockWorldEdit.getInstance().getConfig().getString("wand.Material"));
                String name = SkyblockWorldEdit.getInstance().getConfig().getString("wand.Name");
                List<String> lore = SkyblockWorldEdit.getInstance().getConfig().getStringList("wand.Lore");
                
                
                if(Methods.isInt(args[2])) {
                	
                	int uses = Integer.valueOf(args[2]);
                	
                	List<String> formattedLore = new ArrayList<>();
                    
                    for(String l : lore) {
                    	l = l.replace("{uses}", String.valueOf(uses));
                    	formattedLore.add(l);
                    }
                    
                    ItemStack item = new ItemBuilder(material, 0).setName(name).setLore(formattedLore).setTag("skyblockwand", "sbw")
                    .setTag("uses", String.valueOf(uses)).build();
                    
                    target.getInventory().addItem(item);
                	sender.sendMessage(Methods.color(SkyblockWorldEdit.getInstance().language.getConfiguration().getString("givewand").replace("%target%", target.getName())));
                }else {
                	
                	String uses = SkyblockWorldEdit.getInstance().getConfig().getString("UsesLore");
                	
                	List<String> formattedLore = new ArrayList<>();
                    
                    for(String l : lore) {
                    	l = l.replace("{uses}", String.valueOf(uses));
                    	formattedLore.add(l);
                    }
                    
                    ItemStack item = new ItemBuilder(material, 0).setName(name).setLore(formattedLore).setTag("skyblockwand", "sbw")
                    .setTag("unlimited", String.valueOf(uses)).build();
                    
                    target.getInventory().addItem(item);
                    sender.sendMessage(Methods.color(SkyblockWorldEdit.getInstance().language.getConfiguration().getString("givewand").replace("%target%", target.getName())));
                }
                
                
                

                
            } else if (args.length == 3) {
                if (args[0].equalsIgnoreCase("replace")) {
                	Player p =(Player) sender;

                }
            }
        }
        if(cmd.getName().equalsIgnoreCase("set")) {
        	Player p =(Player) sender;
        	if(args.length == 1) {
        		if(!SkyblockWorldEdit.getInstance().blackList().contains(args[0].toUpperCase())) {
        			Material setMaterial = Material.AIR;

        			String[] block = args[0].split(":");
        			int data = 0;
        			if(args[0].contains(":")){
        				if(Methods.isInt(block[0]) && Material.getMaterial(Integer.valueOf(block[0])) != null)  {
							setMaterial = Material.getMaterial(Integer.valueOf(block[0]));
							data = Integer.valueOf(block[1]);
						}else{
        					if(Material.matchMaterial(block[0].toUpperCase()) != null) {
        						setMaterial = Material.matchMaterial(block[0].toUpperCase());
        						data = Integer.valueOf(block[1]);
							}else{
								p.sendMessage(ChatColor.RED + "The material does not exist");
								return false;
							}
						}

        			}else{
        				if(Material.matchMaterial(args[0].toUpperCase()) != null){
							setMaterial = Material.matchMaterial(args[0].toUpperCase());
						}else{
        					p.sendMessage(ChatColor.RED + "The material does not exist");
        					return false;
						}

					}

        				if(!setMaterial.isBlock()){
							p.sendMessage(ChatColor.RED + "Please type a material that can be placed");
							return false;
						}


        				 ItemStack item = p.getItemInHand();
             			
             			if(item != null && item.getType() != Material.AIR) {
             				
             				NBTItem nbtItem = new NBTItem(item);
             				
             				if(nbtItem != null && nbtItem.hasNBTData()) {
             					
             					if(nbtItem.hasKey("uses")) {
             						
             						int uses = Integer.valueOf(nbtItem.getString("uses"));
             						
             						if(uses > 1) {
             							if(ShopGuiPlusApi.getItemStackPriceBuy(new ItemStack(setMaterial)) > 0){

											int formattedUses = uses - 1;

											Material material = Material.matchMaterial(SkyblockWorldEdit.getInstance().getConfig().getString("wand.Material"));
											String name = SkyblockWorldEdit.getInstance().getConfig().getString("wand.Name");
											List<String> lore = SkyblockWorldEdit.getInstance().getConfig().getStringList("wand.Lore");



											List<String> formattedLore = new ArrayList<>();

											for(String l : lore) {
												l = l.replace("{uses}", String.valueOf(formattedUses));
												formattedLore.add(l);
											}



											ItemStack wandItem = new ItemBuilder(material, 0).setName(name).setLore(formattedLore).setTag("skyblockwand", "sbw")
													.setTag("uses", String.valueOf(formattedUses)).build();


								//			CompatUtils.setItemInHand(p, wandItem);
											//  target.getInventory().addItem(item);
											SkyblockWorldEdit.getInstance().getSMethods().set(p, setMaterial, data, wandItem);
										}else{
											String message = SkyblockWorldEdit.getInstance().language.getConfiguration().getString("commands.general.nonValidMaterial");
											p.sendMessage(Methods.color(message));
										}


             						}else {
             							
             							if(uses == 1) {
             							
                				                
                				                
                				            //    CompatUtils.setItemInHand(p, null);
                				              //  target.getInventory().addItem(item);
											SkyblockWorldEdit.getInstance().getSMethods().set(p, setMaterial, data, null);
             								p.sendMessage(Methods.color(SkyblockWorldEdit.getInstance().language.getConfiguration().getString("wandbroke")));
             							}
             						}
             						
             					}else {
             						if(nbtItem.hasKey("unlimited")) {
										SkyblockWorldEdit.getInstance().getSMethods().set(p, setMaterial, data, new ItemStack(Material.AIR));
             						}
             					}
             				}
             				
             				
             			}
        			
        			
        			
        			
        			
        		}else {
        			p.sendMessage(Methods.color(SkyblockWorldEdit.getInstance().language.getConfiguration().getString("blockblacklisted")));
        		}
        		
        	}
        	
        }
        if(cmd.getName().equalsIgnoreCase("replace")) {
        	Player player = (Player) sender;
        	player.sendMessage(Methods.color("&cThis command has been disabled"));
        	//if(args.length == 2) {
        	//	Player p =(Player) sender;
			//	Material setMaterial = Material.STONE;
			//	Material replaceMaterial = Material.STONE;

			//	String[] block = args[0].split(":");
			//	String[] replace = args[1].split(":");
			//	int setData = 0;
			//	int replaceData = 0;
			//	if(args[0].contains(":")){
			//		setMaterial = Material.getMaterial(Integer.valueOf(block[0]));
			//		setData = Integer.valueOf(block[1]);
			//	}else{
			//		setMaterial = Material.matchMaterial(args[0].toUpperCase());
			//	}

			//	if(args[1].contains(":")){
			//		replaceMaterial = Material.getMaterial(Integer.valueOf(replace[0]));
			//		replaceData = Integer.valueOf(replace[1]);
			//	}else{
			//		replaceMaterial = Material.matchMaterial(args[1].toUpperCase());
			//	}

        	//	SkyblockWorldEdit.getInstance().getSMethods().replace(p, setMaterial, replaceMaterial, setData, replaceData);

        	
        }
        if(cmd.getName().equalsIgnoreCase("cancel")) {
        	
        	if(args.length == 0) {
        		Player p =(Player) sender;
        		if(SkyblockWorldEdit.getInstance().inTask.containsKey(p)) {
        			
        			if(SkyblockWorldEdit.getInstance().inTask.get(p)) {
        				p.sendMessage(Methods.color(SkyblockWorldEdit.getInstance().language.getConfiguration().getString("actioncanceled")));
        				SkyblockWorldEdit.getInstance().inTask.put(p, false);
        			}else {
        				p.sendMessage(Methods.color(SkyblockWorldEdit.getInstance().language.getConfiguration().getString("noaction")));
        			}
        		}
        	}
        }


        return true;
    }
}
