package de.exfy.tacticalisland.commands;

import com.sk89q.intake.Command;
import com.sk89q.intake.CommandException;
import com.sk89q.intake.Require;
import de.exfy.core.modules.intake.module.classifier.Sender;
import de.exfy.tacticalisland.TacticalIsland;
import org.bukkit.entity.Player;

public class PointsCommand {

    @Command(aliases = "cheatpoints", desc = "Füge einen Spawn hinzu", min = 1, max = 1)
    @Require("exfy.cheatpoints")
    public void cheatPoints(@Sender Player sender, int points) throws CommandException {

        TacticalIsland.getPointsManager().addPoints(sender, points);
    }
}
