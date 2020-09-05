package me.enchantbook.skyblockworldedit.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener {

    @EventHandler
    public void Inven(InventoryClickEvent e) {

        if (e.getClickedInventory() == null) {
            return;
        }

        if (e.getClickedInventory().getName() == null) return;
        if (e.getClickedInventory().getName().equals("World edit blocks")) e.setCancelled(true);
    }
}
