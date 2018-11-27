package de.exfy.tacticalisland.teams;

import de.exfy.core.modules.TabList;
import de.exfy.tacticalisland.TacticalIsland;
import de.exfy.tacticalisland.enums.TeamInstance;
import de.exfy.tacticalisland.helper.LocationHelper;
import de.exfy.tacticalisland.helper.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TeamManager {

    private List<Team> teamList;
    private File file = new File(TacticalIsland.getInstance().getDataFolder() + "/spawns.yml");
    private YamlConfiguration yamlFile;

    public TeamManager() {
        this.teamList = new ArrayList<>();
        loadFile();
        yamlFile = YamlConfiguration.loadConfiguration(file);
    }

    public void loadFile(){
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enableTeams(String[] names) {
        teamList.clear();
        List<String> list = Arrays.asList(names);
        for (TeamInstance teamInstance : TeamInstance.values()) {
            if(list.contains(teamInstance.getName())) {
                Team t = new Team(teamInstance);
                addTeam(t);
            }
        }
    }

    public void shuffleTeams() {
        for(Team team : teamList) {
            team.getPlayerList().clear();
        }

        for(Player player : Bukkit.getOnlinePlayers()) {
            addToRandomTeam(player);
            Team team = getPlayerTeam(player);
            player.setPlayerListName(team.getChatColor() + player.getName());
            player.sendMessage(TacticalIsland.getGamePrefix() + "§7Die Teams wurden gewechselt. Du bist jetzt in Team " + team.getTeamPrefix() + "§7!");
        }

    }

    public void setSpawn(String map, String team, Location location){
        LocationHelper.saveLocation(file, map + "." + team, location);
    }

    public void addToRandomTeam(Player p) {
        Random r = new Random();
        if(!hasTeam(p)) {
            Team optimalTeam = null;
            for(Team team : teamList){
                if(optimalTeam == null || team.getPlayerList().size() < optimalTeam.getPlayerList().size())
                    optimalTeam = team;
            }
            ArrayList<Team> optimalTeams = new ArrayList<>();
            for(Team team : teamList)
                if(team.getPlayerList().size() == optimalTeam.getPlayerList().size()) optimalTeams.add(team);
            optimalTeams.get(r.nextInt(optimalTeams.size())).addPlayer(p);
        }
    }

    public void respawnAll(){
        for(Player player : Bukkit.getOnlinePlayers())
            player.teleport(getPlayerTeam(player).getTeamSpawn());
    }

    public void addTeam(Team team) {
        if(!teamList.contains(team))
            teamList.add(team);
    }

    public boolean hasTeam(Player player) {
        for(Team team : teamList) {
            if(team.getPlayerList().contains(player))
                return true;
        }
        return false;
    }

    public Team getTeamByName(String teamName) {

        for(Team team : teamList) {
            if(team.getTeamName().equalsIgnoreCase(teamName))
                return team;
        }
        return null;

    }

    public Team getTeamByInstance(TeamInstance teamInstance) {
        return getTeamByName(teamInstance.getName());
    }


    public void remove(Player player) {
        if(hasTeam(player))
            getPlayerTeam(player).removePlayer(player);
    }

    public Team getPlayerTeam(Player player) {
        for(Team team : teamList) {
            if(team.getPlayerList().contains(player))
                return team;
        }
        return null;
    }

    public void loadSpawns(String map){
        for(String key : yamlFile.getConfigurationSection(map).getKeys(false)){
            Team team = getTeamByName(key);
            if(team == null) {
                System.err.println("Can not find team '" + key + "'!");
                return;
            }

            Location spawn = LocationHelper.getLocation(file, map + "." + key);

            team.setTeamSpawn(spawn);
            TacticalIsland.getInstance().getLogger().info("Config Key: " + key);
            TacticalIsland.getInstance().getLogger().info("Assigned team spawn of team " + team.getTeamName() + " to location: " + team.getTeamSpawn());

        }
    }

    public void updateTeams() {
        if(Bukkit.getOnlinePlayers().size() <= 1)
            return;
        Team t = teamList.get(0);
        for(Team team : teamList) {
            if(team.getPlayerList().size() == 0 || (getBiggestTeam().getPlayerList().size() - team.getPlayerList().size()) > 2 )
                updateTeams(team);
        }

    }

    private void updateTeams(Team team) {
        Random r = new Random();
        Team biggest = getBiggestTeam();

        ArrayList<Team> optimalTeams = new ArrayList<>();
        for(Team t : teamList)
            if(t.getPlayerList().size() == biggest.getPlayerList().size()) optimalTeams.add(t);

        List<Player> pList = optimalTeams.get(r.nextInt(optimalTeams.size())).getPlayerList();
        Player p = pList.get(r.nextInt(pList.size()));
        biggest.removePlayer(p);
        team.addPlayer(p);
        p.teleport(team.getTeamSpawn());
        p.sendMessage(TacticalIsland.getGamePrefix() + "§7Du wurdest einem anderen Team zugewiesen. Du spielst jetzt für " + team.getTeamPrefix() + " §7!");


        TabList.setCustomTabColor(p, team.getRealChatColor());

        TacticalIsland.getScoreboardManager().updateScoreboard(p);

        if((biggest.getPlayerList().size() - team.getPlayerList().size()) > 2)
            updateTeams(team);


    }

    private Team getBiggestTeam() {
        Team biggest = null;
        for(Team t : teamList) {
            if(biggest == null || t.getPlayerList().size() > biggest.getPlayerList().size())
                biggest = t;
        }
        return biggest;
    }

    public List<Team> getTeamList() {
        return teamList;
    }
}
