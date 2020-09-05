package me.enchantbook.skyblockworldedit.Listeners;

import me.enchantbook.skyblockworldedit.Nbt.NBTItem;
import me.enchantbook.skyblockworldedit.SkyblockWorldEdit;
import me.enchantbook.skyblockworldedit.Utils.ItemBuilder;
import me.enchantbook.skyblockworldedit.Utils.Methods;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class InteractEvent implements Listener {

    @EventHandler
    public void Interact(PlayerInteractEvent e) {
        if (e.isCancelled()) return;
        if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            Material material = Material.matchMaterial(SkyblockWorldEdit.getInstance().getConfig().getString("wand.Material"));
            String name = SkyblockWorldEdit.getInstance().getConfig().getString("wand.Name");
            List<String> lore = SkyblockWorldEdit.getInstance().getConfig().getStringList("wand.Lore");

            ItemStack item = e.getPlayer().getItemInHand();
            NBTItem nbtItem = new NBTItem(e.getPlayer().getInventory().getItemInHand());
            if (nbtItem.getItem() != null && nbtItem.hasNBTData()) {
            	 if(nbtItem.hasKey("skyblockwand")) {
              	   if(!SkyblockWorldEdit.selectedLocations.containsKey(e.getPlayer())) {
                         SkyblockWorldEdit.selectedLocations.put(e.getPlayer(), new HashMap<>());
                     }
                     e.setCancelled(true);
                     if (SkyblockWorldEdit.getInstance().ss != null) {
                         if (!SkyblockWorldEdit.getInstance().ss.isOnIsland(e.getPlayer(),e.getClickedBlock().getLocation())) {
                             e.getPlayer().sendMessage(Methods.color(SkyblockWorldEdit.getInstance().language.getConfiguration().getString("onAnotherIsland")));
                             return;
                         }
                     }


                     SkyblockWorldEdit.selectedLocations.get(e.getPlayer()).put(0, e.getClickedBlock().getLocation());


                     String message = SkyblockWorldEdit.getInstance().language.getConfiguration().getString("locationSet");
                     message = Methods.replace(message, "%pos%", "Left");
                     message = Methods.replace(message, "%prefix%", SkyblockWorldEdit.getInstance().language.getConfiguration().getString("prefix"));
                     e.getPlayer().sendMessage(Methods.color(message));
                 }
            }
            	
            
          



            

        } else if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

            Material material = Material.matchMaterial(SkyblockWorldEdit.getInstance().getConfig().getString("wand.Material"));
            String name = SkyblockWorldEdit.getInstance().getConfig().getString("wand.Name");
            List<String> lore = SkyblockWorldEdit.getInstance().getConfig().getStringList("wand.Lore");
            
            ItemStack item = e.getPlayer().getItemInHand();
            NBTItem nbtItem = new NBTItem(e.getPlayer().getInventory().getItemInHand());
            if (nbtItem.getItem() != null && nbtItem.hasNBTData()) {
            	if(nbtItem.hasKey("skyblockwand")) {
            		e.setCancelled(true);
                    if (SkyblockWorldEdit.getInstance().ss != null) {
                        if (!SkyblockWorldEdit.getInstance().ss.isOnIsland(e.getPlayer(),e.getClickedBlock().getLocation())) {
                            e.getPlayer().sendMessage(Methods.color(SkyblockWorldEdit.getInstance().language.getConfiguration().getString("onAnotherIsland")));
                            return;
                        }
                    }

                    SkyblockWorldEdit.selectedLocations.get(e.getPlayer()).put(1, e.getClickedBlock().getLocation());

                    String message = SkyblockWorldEdit.getInstance().language.getConfiguration().getString("locationSet");
                    message = Methods.replace(message, "%pos%", "Right");
                    message = Methods.replace(message, "%prefix%", SkyblockWorldEdit.getInstance().language.getConfiguration().getString("prefix"));
                    e.getPlayer().sendMessage(Methods.color(message));
           	 	}
            }
            	 

            
        }
    }
}
