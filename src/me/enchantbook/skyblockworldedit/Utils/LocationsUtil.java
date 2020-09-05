package me.enchantbook.skyblockworldedit.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class LocationsUtil {
    public static List<Block> blocksFromTwoPoints(Location loc1, Location loc2, Material replacemat, Material material)
    {
        List<Block> blocks = new ArrayList<Block>();

        int topBlockX = (loc1.getBlockX() < loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
        int bottomBlockX = (loc1.getBlockX() > loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());

        int topBlockY = (loc1.getBlockY() < loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
        int bottomBlockY = (loc1.getBlockY() > loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());

        int topBlockZ = (loc1.getBlockZ() < loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
        int bottomBlockZ = (loc1.getBlockZ() > loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());

        for(int x = bottomBlockX; x <= topBlockX; x++)
        {
            for(int z = bottomBlockZ; z <= topBlockZ; z++)
            {
                for(int y = bottomBlockY; y <= topBlockY; y++)
                {

                    Block block = loc1.getWorld().getBlockAt(x, y, z);
                    if(block.getType() != material && Methods.isBlacklisted(block.getType()) == false) {
                        blocks.add(block);
                    }

                    	
                }
            }
        }

        if (replacemat != null) {
            List<Block> wrongBlocks = new ArrayList<>();
            for (int i = 0; i < blocks.size(); i++) {
                Block block = blocks.get(i);
                if (block.getType() != replacemat) {
                    wrongBlocks.add(block);
                }
            }
            for (Block b : wrongBlocks) {
                blocks.remove(b);
            }
        }

        return blocks;
    }
}
