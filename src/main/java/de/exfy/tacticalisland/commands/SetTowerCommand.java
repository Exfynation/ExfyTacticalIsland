package de.exfy.tacticalisland.commands;

import com.sk89q.intake.Command;
import com.sk89q.intake.CommandException;
import com.sk89q.intake.Require;
import de.exfy.core.modules.intake.module.classifier.Sender;
import de.exfy.tacticalisland.TacticalIsland;
import de.exfy.tacticalisland.enums.TeamInstance;
import de.exfy.tacticalisland.teams.Team;
import org.bukkit.entity.Player;

public class SetTowerCommand {

    @Command(aliases = "maptower", desc = "Location des Map-Towers setzen")
    @Require("exfy.maptower")
    public void setSpawn(@Sender Player sender) throws CommandException {
        TacticalIsland.getMapManager().setTower(sender.getLocation());
    }

}
