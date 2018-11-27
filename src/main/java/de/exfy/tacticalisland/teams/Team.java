package de.exfy.tacticalisland.teams;

import de.exfy.tacticalisland.enums.TeamInstance;
import de.exfy.tacticalisland.helper.LocationHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Team {

    private List<Player> playerList;
    private TeamInstance teamInstance;
    private Location spawn;

    public Team(TeamInstance teamInstance) {
        this.teamInstance = teamInstance;
        playerList = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        if(!playerList.contains(player))
            playerList.add(player);
    }

    public void removePlayer(Player player) {
        if(playerList.contains(player))
            playerList.remove(player);
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public String getTeamName() {
        return this.teamInstance.getName();
    }

    public String getTeamPrefix() {
        return this.teamInstance.getPrefix();
    }

    public void setTeamPrefix(String teamPrefix) {
        this.teamInstance.setPrefix(teamPrefix);
    }

    public Location getTeamSpawn() {
        return spawn;
    }

    public String getChatColor() { return this.teamInstance.getChatColor(); }

    public ChatColor getRealChatColor() { return this.teamInstance.getRealChatColor(); }


    public void setTeamSpawn(Location location) {
        this.spawn = location;
    }

}
