package de.exfy.tacticalisland.listener;

import de.exfy.tacticalisland.TacticalIsland;
import de.exfy.tacticalisland.helper.InventoryHelper;
import de.exfy.tacticalisland.inventory.TeamShop;
import de.exfy.tacticalisland.teams.Team;
import de.exfy.tacticalisland.teams.TeamManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent event){
        if(event.getItem() == null) return;
        if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(event.getItem().getType().equals(Material.EMERALD)) {
            Team team = TacticalIsland.getTeamManager().getPlayerTeam(event.getPlayer());
            TeamShop teamShop = new TeamShop(team.getChatColor());
            teamShop.open(event.getPlayer());
        }
    }
}
