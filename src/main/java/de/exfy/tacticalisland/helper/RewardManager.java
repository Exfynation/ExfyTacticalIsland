package de.exfy.tacticalisland.helper;

import de.exfy.core.modules.Coins;
import de.exfy.core.modules.Stats;
import de.exfy.tacticalisland.TacticalIsland;
import de.exfy.tacticalisland.teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class RewardManager {

    private BukkitTask taskID;
    private boolean running;
    private int reward;

    private Team team;

    public void run(Team team) {

        this.team = team;
        running = true;

        taskID = Bukkit.getScheduler().runTaskLater(TacticalIsland.getInstance(), () -> {

            if(Bukkit.getOnlinePlayers().size() < 2) this.stop();
            rewardPlayers();
            this.run(team);

        }, 60*15);

    }

    public void stop() {
        taskID.cancel();
        running = false;
    }

    public boolean isRunning() {
        return running;
    }

    private void rewardPlayers() {

        for(Player player : team.getPlayerList()) {
            TacticalIsland.getPointsManager().addPoints(player, 5);
            player.sendMessage(TacticalIsland.getGamePrefix() + "Dein Team und du erhalten  §a" + reward + "§7 ⛀ §7für das Besetzen des Turmes!");
            Coins.addCoins(player.getUniqueId(), reward);
            Stats.getStats(player.getUniqueId()).getGameStats("TacticalIsland").getStat("stat.points").addScore(1);
            Stats.getStats(player.getUniqueId()).getGameStats("TacticalIsland").updateAllStats();
        }

    }

    public void setReward(int reward) {
        this.reward = reward;
    }
}
