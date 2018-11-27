package de.exfy.tacticalisland.inventory;

import de.exfy.tacticalisland.TacticalIsland;
import de.exfy.tacticalisland.helper.InventoryHelper;
import de.exfy.tacticalisland.helper.ItemHelper;
import de.exfy.tacticalisland.helper.PointsManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

public class TeamShop extends PlayerInventory {

    private final String PREFIX = "§aShop §r§8➟ §7";

    public TeamShop() {
        this("§a");
    }

    public TeamShop(String chatColor) {
        super();
        setName(chatColor + "Team Shop");
        setSize(9);

        inventoryItems.put(new ItemHelper(Material.ENDER_PEARL).setDisplayName("§5Enderperle").setAmount(1)
                        .setLore("§8Preis: §a" + 10 + " Punkte").getItemStack()
        , 10);
        inventoryItems.put(new ItemHelper(Material.SNOW_BALL).setDisplayName("§bSchneeball").setAmount(1)
                        .setLore("§8Preis: §a" + 5 + " Punkte", "§7Eine kleine Eiskugel,", "§7die einen getroffenen Spieler", "§a3 Sekunden §7lange einfriert," ,  "§7sodass er sich nicht", "§7mehr bewegen kann.").getItemStack()
        , 5);
        inventoryItems.put(new ItemHelper(Material.EGG).setDisplayName("§cGranate").setAmount(1)
                        .setLore("§8Preis: §a" + 13 + " Punkte", "§7Eine mächtige Waffe,", "§7die allen Spielern in", "§7einem Radius von §a3 Blöcken", "§7einen gewaltigen Schaden zufügt.").getItemStack()
        , 13);

        inventoryItems.put(new ItemHelper(Material.POTION).setDisplayName("§5Stärke").makePotion(PotionEffectType.INCREASE_DAMAGE, 30*20, 1)
                .setLore("§8Preis: §a15 Punkte", "§7Ein Trank, welcher dich", "§a30 Sekunden §7lang zum", "§7stärksten Spieler macht.").getItemStack(), 15);
        inventoryItems.put(new ItemHelper(Material.POTION).setDisplayName("§5Heilung").makePotion(PotionEffectType.HEAL, 2, 2)
                .setLore("§8Preis: §a3 Punkte", "§7Du bist verwundet und" , "§7es gibt keinen Ausweg?" , "§7Heile dich §aschnell §7und", "§asofort §7mit diesem Trank!").getItemStack(), 3);
    }

    @Override
    public void open(Player player) {
        //TacticalIsland.getInstance().getLogger().info("Teamshop opened by " + player.getName());
        player.openInventory(create());
    }

    @Override
    public void close(Player player) {
        player.closeInventory();
    }

    @Override
    protected Inventory create() {
        Inventory inv = Bukkit.createInventory(null, getSize(), getName());

        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, new ItemHelper(Material.STAINED_GLASS_PANE)
                    .setDisplayName("§7")
                    .setAmount(1)
                    .getItemStack()
            );
        }

        int i = 0;
        for(ItemStack itemStack : inventoryItems.keySet()) {
            if(i > getSize()) continue;
            inv.setItem(i, itemStack);
            i=i+2;
        }

        return inv;
    }

    @Override
    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        if(event.getWhoClicked() instanceof Player) {

            if(event.getCurrentItem() == null) return;
            if(event.getCurrentItem().getType().equals(Material.EMERALD)) event.setCancelled(true);

            if(!event.getInventory().getTitle().contains("Shop")) return;

            Player player = (Player) event.getWhoClicked();

            event.setCancelled(true);

            if(event.getCurrentItem().getType() == Material.STAINED_GLASS_PANE)
                return;

            if(!inventoryItems.containsKey(event.getCurrentItem()))
                return;

            int price = inventoryItems.get(event.getCurrentItem());
            if(TacticalIsland.getPointsManager().removePoints(player, price)) {
                player.sendMessage(PREFIX + "Kauf erfolgreich. Du hast noch §a" + TacticalIsland.getPointsManager().getScore(player) + " §7Punkte!");
                player.getInventory().addItem(InventoryHelper.removeLore(event.getCurrentItem()));
            } else {
                player.sendMessage(PREFIX + "§cNicht genug Punkte!");
            }

            player.closeInventory();
        }
    }
}
