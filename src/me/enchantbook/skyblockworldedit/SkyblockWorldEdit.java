package me.enchantbook.skyblockworldedit;

import me.enchantbook.skyblockworldedit.Commands.SetMethods;
import me.enchantbook.skyblockworldedit.Configurations.FileManager;
import me.enchantbook.skyblockworldedit.Hooks.SS;
import me.enchantbook.skyblockworldedit.Hooks.Vault;
import me.enchantbook.skyblockworldedit.Listeners.Commands;
import me.enchantbook.skyblockworldedit.Listeners.InteractEvent;
import me.enchantbook.skyblockworldedit.Listeners.InventoryClick;
import me.enchantbook.skyblockworldedit.Utils.FastBlockUpdate;
import me.enchantbook.skyblockworldedit.Utils.LocationsUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SkyblockWorldEdit extends JavaPlugin {

    private static SkyblockWorldEdit instance;
    private Vault v = new Vault();
    private SetMethods sMethods = new SetMethods();

    public static HashMap<Player, HashMap<Integer, Location>> selectedLocations = new HashMap<>();
    public HashMap<Player, Boolean> inTask = new HashMap<>();
    public HashMap<UUID, List<Location>> undoBlocks = new HashMap<>();
    public FileManager language;
    public LocationsUtil locationsutils = new LocationsUtil();
    public SS ss;
    public HashMap<UUID, FastBlockUpdate> placeBlockMap = new HashMap<>();

    public void onEnable() {
        instance = this;
        if (!v.setupEconomy() ) {

            System.out.println(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
        }
        if (Bukkit.getServer().getPluginManager().getPlugin("SuperiorSkyblock2") != null) {
            ss = new SS();
        } 
        saveDefaultConfig();
        language = new FileManager(this, "language", getDataFolder().getAbsolutePath());
        registerCommands("wand", "set", "replace", "cancel");
        registerListeners(new InteractEvent(), new InventoryClick());


    }

    public void onDisable() {
        instance = null;
        inTask.clear();
        undoBlocks.clear();
    }


    public SetMethods getSMethods() {
        return sMethods;
    }
    public Vault GetVault() {
        return v;
    }
    public static SkyblockWorldEdit getInstance() {
        return instance;
    }

    private void registerCommands(String...cmd) {
        for (String c: cmd) getCommand(c).setExecutor(new Commands());
    }
    private void registerListeners(Listener...listeners) {
        for (Listener lis: listeners) getServer().getPluginManager().registerEvents(lis, this);
    }
    
    public List<String> blackList(){
    	List<String> blackList = new ArrayList<>();
    	for(String blocks : this.getConfig().getStringList("BlackList")) {
    		blackList.add(blocks);
    	}
    	return blackList;
    	
    }
}
