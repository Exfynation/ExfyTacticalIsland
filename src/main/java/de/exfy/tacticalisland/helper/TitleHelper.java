package de.exfy.tacticalisland.helper;

import de.exfy.core.modules.TitleApi;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class TitleHelper {

    public static void sendTitle(Player player, String title, String subtitle){
        TitleApi.sendTitleTimes(player, 10, 40, 10);
        TitleApi.sendSubTitle(player, subtitle);
        TitleApi.sendTitle(player, title);
    }

    public static void sendTitleToAll(String title){
        Bukkit.getOnlinePlayers().forEach((player) -> TitleApi.sendTitle(player, title));
    }

    public static void sendSubTitleToAll(String subtitle){
        Bukkit.getOnlinePlayers().forEach((player) -> TitleApi.sendTitle(player, subtitle));
    }

    public static void sendTitleToAll(String title, String subtitle){
        Bukkit.getOnlinePlayers().forEach((player) -> sendTitle(player, title, subtitle));
    }
}
