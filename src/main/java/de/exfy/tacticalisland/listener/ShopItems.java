package de.exfy.tacticalisland.listener;

import de.exfy.tacticalisland.TacticalIsland;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.UUID;

public class ShopItems implements Listener {

    private HashMap<UUID, Long> freez = new HashMap<>();

    @EventHandler(ignoreCancelled = true)
    public void onMove(final PlayerMoveEvent event){
        final UUID uuid = event.getPlayer().getUniqueId();
        if(!freez.containsKey(uuid))return;
        if(System.currentTimeMillis()-freez.get(uuid) > 3000){
            freez.remove(uuid);
            return;
        }
        if (!(event.getFrom().getBlockX() == event.getTo().getBlockX() && event.getFrom().getBlockY() == event.getTo().getBlockY() && event.getFrom().getBlockZ() == event.getTo().getBlockZ())) {
            Location loc = event.getFrom();
            loc.setYaw(event.getTo().getYaw());
            loc.setPitch(event.getTo().getPitch());
            event.setTo(loc);
        }
    }

    @EventHandler
    public void onSnowball(final EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Snowball && event.getEntity() instanceof Player) {
            final Player player = (Player) event.getEntity();
            Player sender = (Player) ((Snowball)event.getDamager()).getShooter();

            String damagerTeam = TacticalIsland.getTeamManager().getPlayerTeam(sender).getTeamName();
            String playerTeam = TacticalIsland.getTeamManager().getPlayerTeam(player).getTeamName();
            if (damagerTeam.equalsIgnoreCase(playerTeam))
                event.setCancelled(true);

            freez.put(player.getUniqueId(), System.currentTimeMillis());

            player.getWorld().playEffect(player.getLocation(), Effect.SNOWBALL_BREAK, 2);
        }
    }


    @EventHandler
    public void onFireBall(final ProjectileHitEvent event){

        if(event.getEntity() instanceof Egg) {
            Location loc = event.getEntity().getLocation();
            event.getEntity().getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 3, false, false);
        }

    }

    @EventHandler
    public void onUpdate(BlockPhysicsEvent event) {
        if(event.getBlock().getType().equals(Material.LADDER)) event.setCancelled(true);
    }

}
