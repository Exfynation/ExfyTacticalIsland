package de.exfy.tacticalisland;

import com.sk89q.intake.Intake;
import de.exfy.core.ExfyCore;
import de.exfy.core.modules.intake.IntakeModule;
import de.exfy.tacticalisland.commands.ForceMapCommand;
import de.exfy.tacticalisland.commands.PointsCommand;
import de.exfy.tacticalisland.commands.SetSpawnCommand;
import de.exfy.tacticalisland.commands.SetTowerCommand;
import de.exfy.tacticalisland.helper.PointsManager;
import de.exfy.tacticalisland.helper.RewardManager;
import de.exfy.tacticalisland.helper.ScoreboardManager;
import de.exfy.tacticalisland.helper.TowerManager;
import de.exfy.tacticalisland.inventory.TeamShop;
import de.exfy.tacticalisland.listener.*;
import de.exfy.tacticalisland.maps.MapManager;
import de.exfy.tacticalisland.teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class TacticalIsland extends JavaPlugin {

    private static final String GAME_PREFIX = "§aTacticalIsland §8➟ §7";

    private static PointsManager pointsManager;
    private static TeamManager teamManager;
    private static ScoreboardManager scoreboardManager;
    private static TowerManager towerManager;
    private static MapManager mapManager;
    private RewardManager rewardManager;

    //private static ChestManager chestManager;

    private Set<Listener> listeners = new HashSet<>();

    private static TacticalIsland instance;

    @Override
    public void onEnable() {
        instance = this;
        System.out.println(ExfyCore.getPrefix() + "Tactical Island wurde aktiviert.");

        IntakeModule.getCommandGraph().commands()
                .registerMethods(new ForceMapCommand())
                .registerMethods(new SetSpawnCommand())
                .registerMethods(new PointsCommand())
                .registerMethods(new SetTowerCommand());
        
        pointsManager = new PointsManager();

        rewardManager = new RewardManager();
        rewardManager.setReward(100);

        teamManager = new TeamManager();
        scoreboardManager = new ScoreboardManager();
        towerManager = new TowerManager(rewardManager);
        mapManager = new MapManager();

        listeners.add(new ChestListener());
        listeners.add(new ChatListener());
        listeners.add(new ConnectionListener());
        listeners.add(new DeathListener());
        listeners.add(new InteractListener());
        listeners.add(new PlayerActions());
        listeners.add(new TeamShop());
        listeners.add(new ShopItems());
        listeners.forEach(l -> Bukkit.getPluginManager().registerEvents(l, ExfyCore.getInstance()));

        if(Bukkit.getOnlinePlayers().size() > 0 && !towerManager.isIdling())
            towerManager.startIdle();

        //ExfyCloud.setCustomNameFormat("TacticalIsland");
    }

    public static String getGamePrefix() {
        return GAME_PREFIX;
    }

    @Override
    public void onDisable() {
        System.out.println(ExfyCore.getPrefix() + "Tactical Island wurde deaktiviert.");
    }

    public static PointsManager getPointsManager() {
        return pointsManager;
    }

    public static TeamManager getTeamManager() {
        return teamManager;
    }

    public static TacticalIsland getInstance() {
        return instance;
    }

    public static ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public static TowerManager getTowerManager() {
        return towerManager;
    }

    public static MapManager getMapManager() {
        return mapManager;
    }
}

