package me.enchantbook.skyblockworldedit.Hooks;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public class SS {

    public boolean isOnIsland(Player player, Location location) {

        if (SuperiorSkyblockAPI.getIslandAt(location) == null) {
            return false;
        }
        if (SuperiorSkyblockAPI.getIslandAt(location).isMember(SuperiorSkyblockAPI.getPlayer(player.getUniqueId()))) {
            return true;
        }
        return false;

    }
}
