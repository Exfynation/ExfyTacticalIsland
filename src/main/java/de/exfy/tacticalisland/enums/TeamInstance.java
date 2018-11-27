package de.exfy.tacticalisland.enums;

import org.bukkit.ChatColor;

public enum TeamInstance {
    BLUE("Blau", "§9§lBlau ", "§9", ChatColor.BLUE),
    GREEN("Pink","§d§lPink ", "§d", ChatColor.DARK_GREEN),
    RED("Rot","§c§lRot ", "§c", ChatColor.RED),
    YELLOW("Gelb","§e§lGelb ", "§e", ChatColor.YELLOW);

    private String prefix;
    private String name;
    private String chatColor;
    private ChatColor realChatColor;

    TeamInstance(String name, String prefix, String chatColor, ChatColor realChatColor){
        this.name = name;
        this.prefix = prefix;
        this.chatColor = chatColor;
        this.realChatColor = realChatColor;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getName() {
        return name;
    }

    public String getChatColor() {
        return chatColor;
    }

    public ChatColor getRealChatColor() {
        return realChatColor;
    }
}
