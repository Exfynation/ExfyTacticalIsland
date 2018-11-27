package de.exfy.tacticalisland.helper;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ItemHelper {

    private ItemStack itemStack;

    public ItemHelper(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemHelper(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemHelper setDisplayName(String displayName) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(displayName);
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemHelper makePotion(PotionEffectType type, int duration, int amp){
        itemStack.setType(Material.POTION);
        PotionMeta meta = (PotionMeta) itemStack.getItemMeta();
        meta.addCustomEffect(new PotionEffect(type, duration, amp), true);
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemHelper addEnchantment(Enchantment enchantment, int level) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.addEnchant(enchantment, level, false);
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemHelper addEnchantment(Enchantment enchantment, int level, boolean upMax) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.addEnchant(enchantment, level, upMax);
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemHelper setLore(List<String> lore) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemHelper setLore(String... loreStrings) {
        List<String> lore = new ArrayList<>();
        for(String s : loreStrings)
            lore.add(s);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemHelper setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
