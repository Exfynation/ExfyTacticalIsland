package de.exfy.tacticalisland.listener;

import de.exfy.core.ExfyCore;
import de.exfy.core.modules.TabList;
import de.exfy.tacticalisland.TacticalIsland;
import de.exfy.tacticalisland.enums.TowerStatus;
import de.exfy.tacticalisland.helper.InventoryHelper;
import de.exfy.tacticalisland.helper.PointsManager;
import de.exfy.tacticalisland.teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player p = event.getPlayer();

        if(!TacticalIsland.getTowerManager().isIdling())
            TacticalIsland.getTowerManager().startIdle();

        InventoryHelper.setDefaultInventory(p);
        p.setGameMode(GameMode.SURVIVAL);
        TacticalIsland.getTeamManager().addToRandomTeam(event.getPlayer());
        Team team = TacticalIsland.getTeamManager().getPlayerTeam(p);
        event.setJoinMessage(TacticalIsland.getGamePrefix() + "§a"+ p.getName() + " §7hat das Spiel betreten.");
        p.teleport(team.getTeamSpawn());
        p.sendMessage(TacticalIsland.getGamePrefix() + "Du bist im Team " + team.getTeamPrefix() + "§7, erobere den Turm!");

        TabList.setCustomTabColor(p, team.getRealChatColor());

        TacticalIsland.getScoreboardManager().updateAll();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        Player p = event.getPlayer();

        if(TacticalIsland.getTowerManager().isIdling() && Bukkit.getOnlinePlayers().size() < 2) {
            TacticalIsland.getTowerManager().cancelIdle();
            TacticalIsland.getTowerManager().updateStatus(TowerStatus.NEUTRAL);
        }

        event.setQuitMessage(TacticalIsland.getGamePrefix() + "§c"+ p.getName() + " §7hat das Spiel verlassen.");

        TacticalIsland.getTeamManager().remove(p);
        TacticalIsland.getPointsManager().clear(p);

        TacticalIsland.getTeamManager().updateTeams();
        TacticalIsland.getScoreboardManager().updateAll();
    }

}
