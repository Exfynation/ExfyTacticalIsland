package de.exfy.tacticalisland.helper;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public final class LocationHelper {

    public static void saveLocation(File file, String path, Location loc) {
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        cfg.set(path, loc.getWorld().getName() + ":" + loc.getX() + ":" + loc.getY() + ":" + loc.getZ() + ":"
                + loc.getYaw() + ":" + loc.getPitch());
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Location getLocation(File file, String path) {
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        if (!cfg.contains(path)) {
            return null;
        }
        String[] args = cfg.getString(path).split(":");
        World world = Bukkit.getServer().createWorld(new WorldCreator(args[0]));
        double x = Double.valueOf(args[1]);
        double y = Double.valueOf(args[2]);
        double z = Double.valueOf(args[3]);
        float yaw = Float.valueOf(args[4]);
        float pitch = Float.valueOf(args[5]);
        return new Location(world, x, y, z, yaw, pitch);
    }
}
