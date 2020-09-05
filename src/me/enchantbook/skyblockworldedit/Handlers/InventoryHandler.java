package me.enchantbook.skyblockworldedit.Handlers;

import me.enchantbook.skyblockworldedit.SkyblockWorldEdit;
import me.enchantbook.skyblockworldedit.Utils.ItemBuilder;
import me.enchantbook.skyblockworldedit.Utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InventoryHandler {

    public void openInventory(Player player) {

        Inventory inv = Bukkit.createInventory(null, 54, "World edit blocks");
        addItems(inv);
        player.openInventory(inv);
    }

    public void addItems(Inventory inv) {
        int count = 0;
        ConfigurationSection section = SkyblockWorldEdit.getInstance().getConfig().getConfigurationSection("prices");
        for (String s : section.getKeys(false)) {

            if (Material.matchMaterial(s) != null) {
                String name = s;

                if (s.contains("SUGAR_CANE_BLOCK")) {
                    name = "SUGAR_CANE";
                }
                if (!s.contains("AIR")) {
                    Material mat = Material.matchMaterial(name);
                    inv.setItem(count, new ItemBuilder(mat,(byte)0).setName(Methods.color("&6&l(!) &f" + s)).build());

                }
                count++;
            }
        }
    }
}
