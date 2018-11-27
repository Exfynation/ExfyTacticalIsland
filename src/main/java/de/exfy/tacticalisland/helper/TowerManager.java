package de.exfy.tacticalisland.helper;

import de.exfy.tacticalisland.TacticalIsland;
import de.exfy.tacticalisland.enums.TowerStatus;
import de.exfy.tacticalisland.maps.Map;
import de.exfy.tacticalisland.teams.Team;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class TowerManager {

    @Getter @Setter private TowerStatus status = TowerStatus.NEUTRAL;
    @Getter @Setter private Team leadingTeam;

    //TODO: Ajust values
    @Getter private Location middle = new Location(Bukkit.getWorld(""), 0, 0, 0);
    @Getter private float range = 10.5f;
    @Getter private final long neededTicks = 20*10;
    @Getter private int takenTicks = 0;
    private BukkitTask idleTask;
    @Getter @Setter private boolean idling;
    private RewardManager rewardManager;

    public TowerManager(RewardManager rewardManager){
        idling = false;
        this.rewardManager = rewardManager;
    }

    public void switchMap(Map newMap){
        middle = newMap.getTower();
        range = 5f;
        status = TowerStatus.NEUTRAL;
        leadingTeam = null;
        takenTicks = 0;
        startIdle();
    }

    public void startIdle() {
        idling = true;
        idleTask = Bukkit.getScheduler().runTaskTimer(TacticalIsland.getInstance(), () -> {
            Team currentBest = calculateBestTeam();
            Team lastLeadingTeam = leadingTeam;
            TowerStatus lastStatus = status;

            //TacticalIsland.getInstance().getLogger().info("Ticks in process: " + takenTicks);
            switch (status){
                case NEUTRAL:
                    if(currentBest != null) {
                        leadingTeam = currentBest;
                        takenTicks = 0;
                        status = TowerStatus.PROCESS;
                        TitleHelper.sendTitleToAll(status.getNiceName(), "§7Der Turm wird gerade von Team " + leadingTeam.getTeamPrefix() + "§7erobert!");
                    }
                    break;
                case PROCESS:
                    if(leadingTeam == currentBest) {
                        takenTicks+=2;
                        if(takenTicks >= neededTicks) {
                            status = TowerStatus.TAKEN;
                            TitleHelper.sendTitleToAll(status.getNiceName(), "§7Der Turm wurde von Team " + leadingTeam.getTeamPrefix() + "§7erobert!");
                            if (!rewardManager.isRunning())
                                rewardManager.run(leadingTeam);

                        }

                    } else {
                        status = TowerStatus.NEUTRAL;
                        TitleHelper.sendTitleToAll( status.getNiceName(), "§7Der Versuch des Teams " + leadingTeam.getTeamPrefix() + "§7wurde gestoppt!");
                        takenTicks = 0;
                        leadingTeam = null;
                    }
                    break;
                case TAKEN:
                    if(leadingTeam != currentBest) {
                        if(currentBest == null) return;
                        status = TowerStatus.NEUTRAL;
                        takenTicks = 0;
                        TitleHelper.sendTitleToAll(status.getNiceName(), "§7Der Turm wurde von " + currentBest.getTeamPrefix() + " §7geleert!");
                        if(rewardManager.isRunning())
                            rewardManager.stop();
                    }
                    break;
            }
            if(lastLeadingTeam != leadingTeam || lastStatus != status)
                TacticalIsland.getScoreboardManager().updateAll();
        }, 1, 2);
    }

    public void cancelIdle() {
        idling = false;
        idleTask.cancel();
        if(rewardManager.isRunning())
            rewardManager.stop();
    }

    public void updateStatus(TowerStatus status) {
        this.status = status;
        TacticalIsland.getScoreboardManager().updateAll();
    }

    private Team calculateBestTeam() {
        Team bestTeam = null;
        int bestPlayersAtTower = 0;
        for(Team team : TacticalIsland.getTeamManager().getTeamList()){
            int playersAtTower = 0;
            for(Player player : team.getPlayerList())
                if (player.getWorld().getName().equals(middle.getWorld().getName()) && player.getLocation().distance(middle) < range)
                    playersAtTower++;

            if(playersAtTower > 0 && (bestTeam == null || bestPlayersAtTower < playersAtTower)) {
                bestTeam = team;
                bestPlayersAtTower = playersAtTower;
            }
        }

        for(Team team : TacticalIsland.getTeamManager().getTeamList()) {

            int playersAtTower = 0;
            for(Player player : team.getPlayerList())
                if (player.getWorld().getName().equals(middle.getWorld().getName()) && player.getLocation().distance(middle) < range)
                    playersAtTower++;

            if(playersAtTower > 0 && playersAtTower == bestPlayersAtTower && team != bestTeam)
                bestTeam = null;

        }

        return bestTeam;
    }
}
