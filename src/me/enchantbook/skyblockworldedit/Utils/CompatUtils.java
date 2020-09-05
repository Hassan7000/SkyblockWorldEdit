package me.enchantbook.skyblockworldedit.Utils;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public final class CompatUtils {

    public static Sound ITEM_BREAK_SOUND;
    private static boolean mMaterial_IsItem;
    private static boolean mPlayerInventory_setItemInMainHand;
    private static boolean mPlayerInteractEvent_getHand;
    private static boolean mItemMeta_setUnbreakable;

    static {
        for (Sound sound : Sound.class.getEnumConstants()) {
            if (sound.name().equals("ITEM_BREAK") || sound.name().equals("ENTITY_ITEM_BREAK")) {
                ITEM_BREAK_SOUND = sound;
                break;
            }
        }

        try {
            Material.class.getMethod("isItem");
            mMaterial_IsItem = true;
        } catch (NoSuchMethodException ex) {
            mMaterial_IsItem = false;
        }

        try {
            PlayerInventory.class.getMethod("setItemInMainHand", ItemStack.class);
            mPlayerInventory_setItemInMainHand = true;
        } catch (NoSuchMethodException ex) {
            mPlayerInventory_setItemInMainHand = false;
        }

        try {
            PlayerInteractEvent.class.getMethod("getHand");
            mPlayerInteractEvent_getHand = true;
        } catch (NoSuchMethodException ex) {
            mPlayerInteractEvent_getHand = false;
        }

        try {
            ItemMeta.class.getMethod("setUnbreakable", boolean.class);
            mItemMeta_setUnbreakable = true;
        } catch (NoSuchMethodException ex) {
            mItemMeta_setUnbreakable = false;
        }
    }

    private CompatUtils() {
    }

   

    public static boolean isNotMainHand(PlayerInteractEvent event) {
        if (mPlayerInteractEvent_getHand) {
            return event.getHand() != EquipmentSlot.HAND;
        }
        return false;
    }

    public static void setItemInHand(Player player, ItemStack newWand) {
        if (mPlayerInventory_setItemInMainHand) {
            player.getInventory().setItemInMainHand(newWand);
        } else {
            player.setItemInHand(newWand);
        }
    }

    public static void setItemMetaUnbreakable(ItemMeta itemMeta) {
        if (mItemMeta_setUnbreakable) {
            itemMeta.setUnbreakable(true);
        } else {
            itemMeta.spigot().setUnbreakable(true);
        }
    }
}

