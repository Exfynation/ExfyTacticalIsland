package de.exfy.tacticalisland.listener;

import de.exfy.tacticalisland.TacticalIsland;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        String format = TacticalIsland.getTeamManager().getPlayerTeam(event.getPlayer()).getChatColor();
        event.setFormat(format + "%s §8➟ §7%s");

    }
}
