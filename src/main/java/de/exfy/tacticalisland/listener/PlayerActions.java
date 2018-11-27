package de.exfy.tacticalisland.listener;

import de.exfy.tacticalisland.TacticalIsland;
import de.exfy.tacticalisland.teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerActions implements Listener {

    private List<Material> allowedBlocks = new ArrayList<>();
    private HashMap<Location, Long> blocks = new HashMap<>();

    public PlayerActions() {
        allowedBlocks.add(Material.COBBLESTONE);
        allowedBlocks.add(Material.WEB);

        Bukkit.getScheduler().runTaskTimer(TacticalIsland.getInstance(), () -> {
            for (Map.Entry<Location, Long> chest : blocks.entrySet()) {
                if (chest.getValue() != 0 && System.currentTimeMillis() - chest.getValue() > 20 * 60 * 10) {
                    chest.setValue(0l);
                    chest.getKey().getBlock().setType(Material.AIR);
                }
            }
        }, 1, 2 * 20);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if(event.getPlayer() != null) {
            if(!allowedBlocks.contains(event.getBlockPlaced().getType()))
                event.setCancelled(true);
        }

        blocks.put(event.getBlockPlaced().getLocation(), System.currentTimeMillis());
        event.getBlockPlaced().setMetadata("placed", new FixedMetadataValue(TacticalIsland.getInstance(), true));
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if(event.getPlayer() != null) {
            if(event.getBlock().hasMetadata("placed")) {
                blocks.remove(event.getBlock().getLocation());
                boolean cancel = !event.getBlock().getMetadata("placed").get(0).asBoolean();
                event.setCancelled(cancel);
            }
            else {
                event.setCancelled(true);
                return;
            }

        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        //if(!(event.getDamager() instanceof Player) || event.getDamager() == null) return;
        if(!(event.getEntity() instanceof Player) || event.getEntity() == null) return;

        Player damager = null;
        if(event.getDamager() instanceof Player) {
            damager = (Player) event.getDamager();
        } else if (event.getDamager() instanceof Projectile) {
            Projectile p = (Projectile) event.getDamager();
            damager = (Player) p.getShooter();
        }

        if(damager == null) return;

        Player player = (Player) event.getEntity();


        String damagerTeam = TacticalIsland.getTeamManager().getPlayerTeam(damager).getTeamName();
        String playerTeam = TacticalIsland.getTeamManager().getPlayerTeam(player).getTeamName();

        if (damagerTeam.equalsIgnoreCase(playerTeam))
            event.setCancelled(true);


    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

}
