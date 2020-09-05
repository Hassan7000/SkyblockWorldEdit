package me.enchantbook.skyblockworldedit.Commands;

import com.sun.org.apache.bcel.internal.generic.MethodGen;
import me.enchantbook.skyblockworldedit.Hooks.Vault;
import me.enchantbook.skyblockworldedit.SkyblockWorldEdit;
import me.enchantbook.skyblockworldedit.Utils.CompatUtils;
import me.enchantbook.skyblockworldedit.Utils.FastBlockUpdate;
import me.enchantbook.skyblockworldedit.Utils.LocationsUtil;
import me.enchantbook.skyblockworldedit.Utils.Methods;
import net.brcdev.shopgui.ShopGuiPlusApi;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class SetMethods {
	
	
	
	
    public void set(Player p, Material material, int data, ItemStack wandItem) {
    	int mPrice = SkyblockWorldEdit.getInstance().getConfig().getInt("MultiplyPrice");

            if(!SkyblockWorldEdit.selectedLocations.containsKey(p)) {
                SkyblockWorldEdit.selectedLocations.put(p, new HashMap<>());
            }
            if (SkyblockWorldEdit.selectedLocations.get(p).size() == 2) {

                if (SkyblockWorldEdit.getInstance().inTask.containsKey(p)) {

                    if (SkyblockWorldEdit.getInstance().inTask.get(p)) {
                    	p.sendMessage(Methods.color(SkyblockWorldEdit.getInstance().language.getConfiguration().getString("placingalreadyrunning")));
                        return;
                    }

                }

                List<Block> blocks = LocationsUtil.blocksFromTwoPoints(SkyblockWorldEdit.selectedLocations.get(p).get(0), SkyblockWorldEdit.selectedLocations.get(p).get(1),null, material);
                if (blocks.size() > SkyblockWorldEdit.getInstance().getConfig().getInt("wand.maxBlocks")) {
                    String message = SkyblockWorldEdit.getInstance().language.getConfiguration().getString("commands.general.tooManyBlocks");
                    message = message.replace("%selectedBlocks%", blocks.size() +"");
                    p.sendMessage(Methods.color(message));
                    return;
                }
                if (SkyblockWorldEdit.getInstance().getSMethods().hasPrice(p, new ItemStack(material)) == false)  {
                    String message = SkyblockWorldEdit.getInstance().language.getConfiguration().getString("commands.general.nonValidMaterial");
                    p.sendMessage(Methods.color(message));
                    return;
                }

                if(SkyblockWorldEdit.getInstance().getSMethods().totalPrice(p, blocks, material, data) == 0){
                    p.sendMessage(Methods.color("&d&lRift&5&lMC &8>> &cYou can't do that."));
                    return;
                }

                if (SkyblockWorldEdit.getInstance().GetVault().takeMoney(p, SkyblockWorldEdit.getInstance().getSMethods().totalPrice(p, blocks, material, data) * mPrice)) {

                    if(material == Material.AIR){
                        String message = SkyblockWorldEdit.getInstance().language.getConfiguration().getString("commands.general.nonValidMaterial");
                        p.sendMessage(Methods.color(message));
                        return;

                    }

                    if(wandItem == null){
                        CompatUtils.setItemInHand(p, null);

                    }

                    if(wandItem.getType() != Material.AIR){
                        CompatUtils.setItemInHand(p, wandItem);
                    }



                    SkyblockWorldEdit.getInstance().getSMethods().runPaste(p, material, null, data, mPrice, SkyblockWorldEdit.getInstance().getSMethods().totalPrice(p, blocks, material, data) * mPrice, blocks);

                }

            }



    }

    public void replace(Player p, Material beforeMat, Material afterMat, int data, int replaceData) {
    	int mPrice = SkyblockWorldEdit.getInstance().getConfig().getInt("MultiplyPrice");
            Material oldmat = beforeMat;
            Material newmat = afterMat;
            if(!SkyblockWorldEdit.selectedLocations.containsKey(p)) {
                SkyblockWorldEdit.selectedLocations.put(p, new HashMap<>());
            }
            if (SkyblockWorldEdit.selectedLocations.get(p).size() == 2) {

                if (SkyblockWorldEdit.getInstance().inTask.containsKey(p)) {

                    if (SkyblockWorldEdit.getInstance().inTask.get(p)) {
                    	p.sendMessage(Methods.color(SkyblockWorldEdit.getInstance().language.getConfiguration().getString("placingalreadyrunning")));
                        return;
                    }

                }

                List<Block> blocks = LocationsUtil.blocksFromTwoPoints(SkyblockWorldEdit.selectedLocations.get(p).get(0),
                        SkyblockWorldEdit.selectedLocations.get(p).get(1),oldmat, beforeMat);
                List<Block> wrongBlocks = new ArrayList<>();
                for (int i = 0; i < blocks.size(); i++) {
                    Block block = blocks.get(i);
                    if (block.getType() != oldmat && block.getTypeId() != replaceData) {
                        wrongBlocks.add(block);
                    }
                }
                for (Block b : wrongBlocks) {
                    blocks.remove(b);
                }
                if (blocks.size() > 1000) {
                    String message = SkyblockWorldEdit.getInstance().language.getConfiguration().getString("commands.general.tooManyBlocks");
                    message = message.replace("%selectedBlocks%", blocks.size() +"");
                    p.sendMessage(Methods.color(message));
                    return;
                }
                if (SkyblockWorldEdit.getInstance().getSMethods().hasPrice(p, new ItemStack(newmat)) == false)  {
                    String message = SkyblockWorldEdit.getInstance().language.getConfiguration().getString("commands.general.nonValidMaterial");
                    p.sendMessage(Methods.color(message));
                    return;
                }
                if (SkyblockWorldEdit.getInstance().GetVault().takeMoney(p,SkyblockWorldEdit.getInstance().getSMethods().totalPrice(p, blocks, newmat, data) * mPrice)) {

                    SkyblockWorldEdit.getInstance().getSMethods().runPaste(p, newmat, oldmat, replaceData, mPrice, SkyblockWorldEdit.getInstance().getSMethods().totalPrice(p, blocks, newmat, data) * mPrice, blocks);

                }

            }



    }
    public void setBlocks(List<Block> list , Material material) {
        for (int i = 0; i < list.size(); i++) {

            list.get(i).setType(material);
        }

    }


    public void runPaste(Player p, Material material, Material replacemat, int data, int mPrice, int price, List<Block> blocks) {
        SkyblockWorldEdit.getInstance().inTask.put(p, true);
        new BukkitRunnable() {
            List<Block> blocksList = LocationsUtil.blocksFromTwoPoints(SkyblockWorldEdit.selectedLocations.get(p).get(0), SkyblockWorldEdit.selectedLocations.get(p).get(1),replacemat, material);
            final ListIterator<Block> iterator = blocksList.listIterator();
            List<Material> blocksPlaced = new ArrayList<>();
            public void run() {
                if (iterator.hasNext()) {
                    Block block  = iterator.next();
                    
                    if(SkyblockWorldEdit.getInstance().inTask.containsKey(p) && SkyblockWorldEdit.getInstance().inTask.get(p)) {
                    	if (block.getType() != material && Methods.isBlacklisted(block.getType()) == false) {
                            block.setTypeIdAndData(material.getId(), (byte) data, true);
                            blocksPlaced.add(material);
                        }
                    }else {
                    	cancel();

                    	if(blocksList.size() == 0){
                    	    p.sendMessage("You haven't gotten any money since no blocks was placed");
                    	    return;
                        }

                    	int amountPlaced = blocks.size() - blocksPlaced.size();
                        int totalMoney = 0;

                        for (int i = 0; i < amountPlaced; i++) {
                            if(hasPrice(p, new ItemStack(material))){
                                totalMoney += getPrice(p, new ItemStack(material)) * mPrice;
                            }
                        }
                        int moneyPlaced = 0;
                        for(Material blockPlaced : blocksPlaced){
                            if(hasPrice(p, new ItemStack(material))){
                                moneyPlaced += getPrice(p, new ItemStack(material));
                            }
                        }

                      //  totalMoney = (totalMoney/2) - moneyPlaced;
                        if(totalMoney > 0){
                            p.sendMessage(Methods.color("&d&lRift&5&lMC &8>> &d You have gotten $" + Methods.formatNumbers(totalMoney) + " back"));
                            Vault.getEconomy().depositPlayer(p, totalMoney);
                        }else{
                            p.sendMessage(Methods.color("&d&lRift&5&lMC &8>> &d You have gotten $" + Methods.formatNumbers(price) + " back since no blocks was placed"));
                            Vault.getEconomy().depositPlayer(p, price);

                        }


                    }
                    
                    

                } else {
                    cancel();
                    SkyblockWorldEdit.getInstance().inTask.put(p, false);
                    p.sendMessage(Methods.color(SkyblockWorldEdit.getInstance().language.getConfiguration().getString("actioncomplete")));;

                }
            }
        }.runTaskTimer(SkyblockWorldEdit.getInstance(), 2, 2);
    }
    public int totalPrice(Player player, List<Block> list, Material material, int data) {
        int totalMoney = 0;
        for (int i = 0; i < list.size(); i++) {
            if (hasPrice(player, new ItemStack(material, 1, (short) data))) {
                if (list.get(i).getType() != material) {
                    totalMoney += getPrice(player, new ItemStack(material));
                }


            }

        }
        return totalMoney;
    }
    
    public boolean hasPrice(Player player, ItemStack item) {
		  double sellPrice = ShopGuiPlusApi.getItemStackPriceBuy(item);
		  return (sellPrice > 0);
		}
    
    private int getPrice(Player player, ItemStack item) {
    	return (int) ShopGuiPlusApi.getItemStackPriceBuy(item);
    }
    public boolean setLocations(Player p, Location loc, int number) {
        HashMap<Integer, Location> map = new HashMap<>();
        map.put(number, loc);
        return true;
    }
}
