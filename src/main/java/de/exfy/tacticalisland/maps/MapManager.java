package de.exfy.tacticalisland.maps;

import de.exfy.core.ExfyCore;
import de.exfy.tacticalisland.TacticalIsland;
import de.exfy.tacticalisland.helper.LocationHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MapManager {

    private Map currentMap;
    private ArrayList<Map> maps = new ArrayList<>();
    private File mapFile = new File(TacticalIsland.getInstance().getDataFolder() + "/maps.yml");
    private Countdown countdown;

    private void loadFile(){
        mapFile.getParentFile().mkdirs();
        try {
            mapFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MapManager() {
        loadFile();
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(mapFile);
        for(String key : yaml.getKeys(false)){
            String name = yaml.getString(key + ".name");
            String[] teams = yaml.getStringList(key + ".teams").toArray(new String[]{});
            Location tower = LocationHelper.getLocation(mapFile, key + ".tower");
            //float towerRange = yaml.getFloat(key + ".towerRange");
            float towerRange = 8f;
            Map map = new Map(name, teams, tower, towerRange);
            maps.add(map);
            TacticalIsland.getInstance().getLogger().info("SuccessFully Loaded Map " + map.toString());
        }

        countdown = new Countdown(new CountdownListener() {
            @Override
            public void onUpdate(int seconds) {
                switch (seconds){
                    case 1:
                        Bukkit.broadcastMessage(ExfyCore.getPrefix() + "Die Map wechselt in ein §aeiner §7Sekunde!");
                        break;
                    case 2: case 3: case 4: case 5: case 10: case 15: case 20: case 30: case 45: case 60:
                        Bukkit.broadcastMessage(ExfyCore.getPrefix() + "Die Map wechselt in §a" + seconds + " §7Sekunden!");
                        break;
                    case 60*2: case 60*5: case 60*10:
                        Bukkit.broadcastMessage(ExfyCore.getPrefix() + "Die Map wechselt in §a" + seconds/60 + " §7Minuten!");
                        break;
                }
            }

            @Override
            public void onStop() {
                nextMap();
                Bukkit.broadcastMessage(ExfyCore.getPrefix() + "Die Map wechselt jetzt!");
                countdown.start(60*15);
            }

            @Override public void onStart() {}
            @Override public void onSleepStop() {}
            @Override public void onSleep() {}
            @Override public void onSleepStart() {}
        });
        //countdown.start(60*15);
        //Bukkit.getScheduler().runTaskTimer(TacticalIsland.getInstance(), this::nextMap, 1, 20*60*15);
        nextMap();
    }

    private void nextMap(){
        if(TacticalIsland.getTowerManager().isIdling())
            TacticalIsland.getTowerManager().cancelIdle();
        Map lastMap = currentMap;
        while (lastMap == currentMap)
            this.currentMap = maps.get(new Random().nextInt(maps.size()));
        loadMap(currentMap);
    }

    public void loadMap(Map map){
        TacticalIsland.getTeamManager().enableTeams(map.getTeams());
        TacticalIsland.getTeamManager().shuffleTeams();
        TacticalIsland.getTeamManager().loadSpawns(map.getName());
        TacticalIsland.getTowerManager().switchMap(map);
        TacticalIsland.getScoreboardManager().updateAll();
        TacticalIsland.getTeamManager().respawnAll();
    }

    public Map getMapByName(String name){
        for(Map map : maps)
            if(map.getName().equalsIgnoreCase(name))
                return map;
        return null;
    }

    public void setTower(Location location) {
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(mapFile);
        String mapName = getCurrentMap().getName();
        for(String key : yaml.getKeys(false)) {
            if(yaml.getString(key + ".name").equalsIgnoreCase(mapName)) {
                LocationHelper.saveLocation(mapFile, key + ".tower", location);
            }
        }
    }

    public Map getCurrentMap() {
        return currentMap;
    }

    public ArrayList<Map> getMaps() {
        return maps;
    }
}
