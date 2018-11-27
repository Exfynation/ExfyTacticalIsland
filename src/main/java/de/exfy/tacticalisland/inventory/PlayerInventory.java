package de.exfy.tacticalisland.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public abstract class PlayerInventory implements Listener {

    protected String Name;
    protected int size;
    protected Inventory inventory;
    protected HashMap<ItemStack, Integer> inventoryItems = new HashMap<>();

    public abstract void open(Player player);
    public abstract void close(Player player);
    protected abstract Inventory create();

    protected void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return Name;
    }

    protected void setName(String name) {
        Name = name;
    }

    public Inventory getInventory() {
        return inventory;
    }

    protected void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @EventHandler
    public abstract void onInvClick(InventoryClickEvent event);

}
