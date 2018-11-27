package de.exfy.tacticalisland.helper;

import de.exfy.tacticalisland.TacticalIsland;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PointsManager {

    private HashMap<UUID, Integer> playerPoints;

    public PointsManager() {
        this.playerPoints = new HashMap<>();
    }

    public void addPoint(Player player) {
        if(hasAccount(player))
            playerPoints.put(player.getUniqueId(), getScore(player)+1 );
        else {
            playerPoints.put(player.getUniqueId(), 1 );
        }
        TacticalIsland.getScoreboardManager().updateScoreboard(player);
    }

    public void addPoints(Player player, int amount) {
        if(hasAccount(player))
            playerPoints.put(player.getUniqueId(), getScore(player)+amount );
        else {
            playerPoints.put(player.getUniqueId(), amount );
        }
        TacticalIsland.getScoreboardManager().updateScoreboard(player);
    }

    public boolean removePoints(Player player, int amount) {
        if(hasEnough(player, amount)) {
            playerPoints.put(player.getUniqueId(), getScore(player) - amount);
            TacticalIsland.getScoreboardManager().updateScoreboard(player);
            return true;
        } else {
            return false;
        }
    }

    private boolean hasEnough(Player player, int amount) {
        if(!hasAccount(player))
            return false;

        return(getScore(player) >= amount);
    }

    private boolean hasAccount(Player player) {
        return playerPoints.containsKey(player.getUniqueId());
    }

    public int getScore(Player player) {
        if(!hasAccount(player))
            return 0;
        return playerPoints.get(player.getUniqueId());
    }

    public void clear(Player player) {
        if(hasAccount(player))
            playerPoints.remove(player);
    }

}
