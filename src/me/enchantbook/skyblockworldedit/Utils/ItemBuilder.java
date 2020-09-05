package me.enchantbook.skyblockworldedit.Utils;

import me.enchantbook.skyblockworldedit.Nbt.NBTItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    private ItemStack itemStack;

    public ItemBuilder(Material mat, int data) {
        this.itemStack = new ItemStack(mat, 1, (short) data);

        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        this.itemStack.setItemMeta(itemMeta);
    }

    public ItemBuilder setType(Material mat) {
        this.itemStack.setType(mat);
        return this;
    }

    public ItemBuilder setName(String dislpayName) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(Methods.color(dislpayName));
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        List<String> list = new ArrayList<>();
        lore.forEach(l -> list.add(Methods.color(l)));
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addEnchant(Enchantment.SILK_TOUCH, 1, false);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.setLore(list);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    // NMS tag
    public ItemBuilder setTag(String key, String value) {
        NBTItem nbtItem = new NBTItem(this.itemStack);
        nbtItem.setString(key,value);
        this.itemStack = nbtItem.getItem();
        return this;
    }

    public ItemStack build() {
        return this.itemStack;
    }
}
