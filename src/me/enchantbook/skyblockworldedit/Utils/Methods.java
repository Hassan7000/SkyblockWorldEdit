package me.enchantbook.skyblockworldedit.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import me.enchantbook.skyblockworldedit.SkyblockWorldEdit;

public class Methods {


    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    public static String replace(String text, String old, String replacement) {
        if (text.contains(old)) {
            String[] split = text.split(old);
            text = split[0] + replacement + split[1];
        }
        return text;

    }
    
    public static boolean isInt(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException nfe) {
			return false;
		}
		
		return true;
	}

	public static Boolean isBlacklisted(Material material){
    	List<Material> list = new ArrayList<>();
    	for(String materials : SkyblockWorldEdit.getInstance().getConfig().getStringList("SetBlackList")){
    		list.add(Material.matchMaterial(materials));
		}

    	return list.contains(material);
	}
    
    public static void addUndoBlocks(UUID u, Location location) {
    	
    	if(SkyblockWorldEdit.getInstance().undoBlocks.containsKey(u)) {
    		SkyblockWorldEdit.getInstance().undoBlocks.get(u).add(location);
    	}else {
    		List<Location> list = new ArrayList<>();
    		list.add(location);
    		SkyblockWorldEdit.getInstance().undoBlocks.put(u, list);
    	}
    }

    public static String formatNumbers(int number){
		String formatted = new DecimalFormat("#,###.##").format(Double.parseDouble(String.valueOf(number)));
		return formatted;
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch(NumberFormatException e) {
			return false;
		} catch(NullPointerException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}
    
    public static boolean undoTask(UUID u) {
    	
    	if(SkyblockWorldEdit.getInstance().undoBlocks.containsKey(u)) {
    		
    		for(Location location : SkyblockWorldEdit.getInstance().undoBlocks.get(u)) {
    			Block block = location.getBlock();
    			block.setType(Material.AIR);
    		}
    		
    	}else {
    		return false;
    	}
		return false;
    }
}
