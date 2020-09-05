package me.enchantbook.skyblockworldedit.Hooks;

import me.enchantbook.skyblockworldedit.SkyblockWorldEdit;
import me.enchantbook.skyblockworldedit.Utils.Methods;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Vault {

    private static Economy econ = null;

    public boolean setupEconomy() {
        if (SkyblockWorldEdit.getInstance().getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = SkyblockWorldEdit.getInstance().getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    public boolean takeMoney(Player p, double money) {
        if (getEconomy().getBalance(p) >= money) {

            getEconomy().withdrawPlayer(p, money);

            String message = SkyblockWorldEdit.getInstance().language.getConfiguration().getString("commands.general.successful");

            int moneyInt = (int) money;
            message = message.replace("%money%", Methods.formatNumbers(moneyInt));
            message = message.replace("%prefix%", SkyblockWorldEdit.getInstance().language.getConfiguration().getString("prefix"));
            p.sendMessage(Methods.color(message));

            return true;
        } else {
            String message = SkyblockWorldEdit.getInstance().language.getConfiguration().getString("commands.general.notEnoughFunds");

            int moneyInt = (int) money;

            message = message.replace("%money%", Methods.formatNumbers(moneyInt));
            message = message.replace("%prefix%", SkyblockWorldEdit.getInstance().language.getConfiguration().getString("prefix"));
            p.sendMessage(Methods.color(message));
            return false;
        }
    }


    public static Economy getEconomy() {
        return econ;
    }
}
