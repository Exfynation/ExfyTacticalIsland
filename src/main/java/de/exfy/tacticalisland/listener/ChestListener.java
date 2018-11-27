package de.exfy.tacticalisland.listener;

import de.exfy.tacticalisland.TacticalIsland;
import de.exfy.tacticalisland.helper.ItemSkulls;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Skull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Stream;

public class ChestListener implements Listener {

    private Map<Material, Double> items;
    private HashMap<Location, Long> chests = new HashMap<>();

    public ChestListener() {
        items = new HashMap<>();
        items.put(Material.IRON_SWORD, 1D);
        items.put(Material.ARROW, 4D);
        items.put(Material.COBBLESTONE, 5D);
        items.put(Material.DIAMOND_SWORD, 0.2D);
        items.put(Material.WEB, 3D);

        Bukkit.getScheduler().runTaskTimer(TacticalIsland.getInstance(), () -> {
            for (Map.Entry<Location, Long> chest : chests.entrySet()) {
                if (chest.getValue() != 0 && System.currentTimeMillis() - chest.getValue() > 20 * 60 * 10) {
                    chest.setValue(0l);

                    ItemSkulls.setBlock(chest.getKey().getBlock().getLocation(), "http://textures.minecraft.net/texture/1185657c38acdd8f95e1d2cd1115bb0f11139ad2b3ce442267e69706d916e");

                    chest.getKey().getWorld().playEffect(chest.getKey(), Effect.ENDER_SIGNAL, 4);
                }
            }
        }, 1, 3 * 20);
    }



    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        if (e.getClickedBlock().getType() != Material.SKULL) return;

        Location location = e.getClickedBlock().getLocation();
        Long time = chests.get(location);
        if (!(time == null || time == 0)) return;

        Random random = new Random();
        Material material = getRandomMaterial(items.entrySet().stream(), random);


        ItemStack stack = new ItemStack(material);
        stack.setAmount(1);

        if(material == Material.ARROW || material == Material.COBBLESTONE) stack.setAmount(8);
        if(material == Material.WEB) stack.setAmount(2);

        e.getPlayer().getInventory().addItem(stack);
        e.getPlayer().updateInventory();

        time = System.currentTimeMillis();
        location.getBlock().setType(Material.AIR);
        location.getWorld().playEffect(location, Effect.ENDER_SIGNAL, 4);
        chests.put(location, time);

    }

    public static <E> E getRandomMaterial(Stream<Map.Entry<E, Double>> weights, Random random) {
        return weights
                .map(e -> new AbstractMap.SimpleEntry<>(e.getKey(), -Math.log(random.nextDouble()) / e.getValue()))
                .min(Comparator.comparing(AbstractMap.SimpleEntry::getValue))
                .orElseThrow(IllegalArgumentException::new).getKey();
    }
}
