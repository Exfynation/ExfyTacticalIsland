package de.exfy.tacticalisland.commands;

import com.sk89q.intake.Command;
import com.sk89q.intake.CommandException;
import com.sk89q.intake.Require;
import de.exfy.core.modules.intake.module.classifier.Sender;
import de.exfy.tacticalisland.TacticalIsland;
import de.exfy.tacticalisland.enums.TeamInstance;
import de.exfy.tacticalisland.teams.Team;
import org.bukkit.entity.Player;

public class SetSpawnCommand {

    @Command(aliases = "spawn", desc = "Füge einen Spawn hinzu", min = 2, max = 2)
    @Require("exfy.spawn")
    public void setSpawn(@Sender Player sender, String mapName, String teamName) throws CommandException {

        TeamInstance teamInstance = null;
        for (TeamInstance instance : TeamInstance.values()) {

            if(instance.getName().equalsIgnoreCase(teamName))
                teamInstance = instance;

        }

        if(teamInstance == null)
            throw new CommandException("Invalid Team");

        if (TacticalIsland.getMapManager().getCurrentMap().getName().equalsIgnoreCase(mapName)) {
            Team team = TacticalIsland.getTeamManager().getTeamByInstance(teamInstance);
            if (team == null) throw new CommandException("Das Team gibt es! Aber nicht in dieser Map!");
            team.setTeamSpawn(sender.getLocation());
        }
        TacticalIsland.getTeamManager().setSpawn(mapName, teamName, sender.getLocation());
    }

}
