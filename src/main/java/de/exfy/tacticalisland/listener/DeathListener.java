package de.exfy.tacticalisland.listener;

import de.exfy.core.ExfyCore;
import de.exfy.core.modules.Stats;
import de.exfy.core.modules.stats.GameStat;
import de.exfy.tacticalisland.TacticalIsland;
import de.exfy.tacticalisland.helper.InventoryHelper;
import de.exfy.tacticalisland.helper.ItemHelper;
import de.exfy.tacticalisland.teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class DeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        Player killer = event.getEntity().getKiller();

        event.setKeepInventory(true);

        GameStat death = Stats.getStats(player.getUniqueId()).getGameStats("TacticalIsland").getStat("stat.deaths");
        death.addScore(1);
        Stats.getStats(player.getUniqueId()).getGameStats("TacticalIsland").updateAllStats();

        if(killer != null) {
            killer.sendMessage(TacticalIsland.getGamePrefix() + "Du hast §a" + player.getName() + " §7getötet und hast deshalb 8 Pfeile und einen Punkt erhalten!");
            killer.getInventory().addItem(new ItemHelper(Material.ARROW).setAmount(8).getItemStack());
            TacticalIsland.getPointsManager().addPoint(killer);
            GameStat killerStats = Stats.getStats(killer.getUniqueId()).getGameStats("TacticalIsland").getStat("stat.kills");
            killerStats.addScore(1);
            Stats.getStats(killer.getUniqueId()).getGameStats("TacticalIsland").updateAllStats();

            event.setDeathMessage(TacticalIsland.getGamePrefix() + getChatColor(player) + player.getName() + " §7wurde von " + getChatColor(killer) + killer.getName() + " §7getötet.");
        } else
            event.setDeathMessage(TacticalIsland.getGamePrefix() + getChatColor(player) + player.getName() + " §7ist gestorben!");

    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event){
        InventoryHelper.setDefaultInventory(event.getPlayer());
        Team team = TacticalIsland.getTeamManager().getPlayerTeam(event.getPlayer());
        if(team == null)return;
        event.setRespawnLocation(team.getTeamSpawn());
        //event.getPlayer().teleport(team.getTeamSpawn());
    }

    private String getChatColor(Player player) {
        return TacticalIsland.getTeamManager().getPlayerTeam(player).getChatColor();
    }

}
