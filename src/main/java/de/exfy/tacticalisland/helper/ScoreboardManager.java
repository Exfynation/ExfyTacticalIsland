package de.exfy.tacticalisland.helper;

import de.exfy.core.helper.player.InfoScoreboard;
import de.exfy.tacticalisland.TacticalIsland;
import de.exfy.tacticalisland.teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;

public class ScoreboardManager {

    private HashMap<Player, InfoScoreboard> scoreboards = new HashMap<>();

    public void updateScoreboard(Player player){
        if(!scoreboards.containsKey(player)) initScoreboard(player);
        InfoScoreboard scoreboard = scoreboards.get(player);
        Team team = TacticalIsland.getTeamManager().getPlayerTeam(player);
        Team leadingTeam = TacticalIsland.getTowerManager().getLeadingTeam();

        if(team == null) return;

        scoreboard.getEntry("team").get().setValue(team.getTeamPrefix());
        scoreboard.getEntry("player").get().setValue("§7" + Bukkit.getOnlinePlayers().size());
        scoreboard.getEntry("points").get().setValue("§7" + TacticalIsland.getPointsManager().getScore(player));
        scoreboard.getEntry("leedingteam").get().setValue(leadingTeam == null?"§7Kein Team":leadingTeam.getTeamPrefix());
        scoreboard.getEntry("status").get().setValue(TacticalIsland.getTowerManager().getStatus().getNiceName());
        scoreboard.getEntry("map").get().setValue(TacticalIsland.getMapManager().getCurrentMap().getName());

        scoreboard.update();
    }

    private void initScoreboard(Player player){
        InfoScoreboard scoreboard = new InfoScoreboard(player, "TacticalIsland");
        scoreboard.new InfoEntry("team", "§aTeam");
        scoreboard.new InfoEntry("player", "§aSpieler");
        scoreboard.new InfoEntry("points", "§aPunkte");
        scoreboard.new InfoEntry("leedingteam", "§aFührung");
        scoreboard.new InfoEntry("status", "§aTower");
        scoreboard.new InfoEntry("map", "§aMap");
        scoreboards.put(player, scoreboard);
    }

    public void updateAll(){
        Bukkit.getOnlinePlayers().forEach(this::updateScoreboard);
    }
}
