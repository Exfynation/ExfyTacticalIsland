package de.exfy.tacticalisland.helper;

import de.exfy.tacticalisland.inventory.TeamShop;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryHelper {

    private static TeamShop teamShop;

    public static void setDefaultInventory(Player player) {
        player.getInventory().clear();

        player.getInventory().addItem(new ItemHelper(Material.STONE_SWORD).getItemStack());
        player.getInventory().addItem(new ItemHelper(Material.BOW).getItemStack());
        player.getInventory().addItem(new ItemHelper(Material.COBBLESTONE).setAmount(32).getItemStack());
        player.getInventory().setItem(8, new ItemHelper(Material.EMERALD).setDisplayName("§6Team Shop").getItemStack());

        player.getInventory().setHelmet(new ItemHelper(Material.CHAINMAIL_HELMET).getItemStack());
        player.getInventory().setChestplate(new ItemHelper(Material.CHAINMAIL_CHESTPLATE).getItemStack());
    }

    public static ItemStack removeLore(ItemStack itemStack) {

        ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(null);
        itemStack.setItemMeta(meta);

        return itemStack;

    }

}
