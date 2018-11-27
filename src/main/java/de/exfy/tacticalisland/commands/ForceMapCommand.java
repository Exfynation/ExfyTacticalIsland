package de.exfy.tacticalisland.commands;

import com.sk89q.intake.Command;
import com.sk89q.intake.CommandException;
import com.sk89q.intake.Require;
import de.exfy.core.ExfyCore;
import de.exfy.core.modules.intake.module.classifier.Sender;
import de.exfy.tacticalisland.TacticalIsland;
import de.exfy.tacticalisland.maps.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ForceMapCommand {

    @Command(aliases = "forcemap", desc = "Spiele eine bestimte Map", min = 1, max = 1)
    @Require("exfy.forcemap")
    public void forceMap(@Sender Player sender, String mapName) throws CommandException {
        Map map = TacticalIsland.getMapManager().getMapByName(mapName);
        if(map == null) throw new CommandException("Die Map '" + mapName + "' existiert nicht!");
        Bukkit.broadcastMessage(ExfyCore.getPrefix() + "Die Map wechselt jetzt auf '" + map.getName() + "'!");
        TacticalIsland.getMapManager().loadMap(map);
    }
}
