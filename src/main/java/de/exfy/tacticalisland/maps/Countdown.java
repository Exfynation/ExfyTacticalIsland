package de.exfy.tacticalisland.maps;

import de.exfy.tacticalisland.TacticalIsland;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class Countdown {

    private CountdownListener listener;
    private boolean sleeping;
    private BukkitTask task;
    private Plugin plugin;
    private int seconds;

    public Countdown(CountdownListener listener){
        this(listener, TacticalIsland.getInstance());
    }

    public Countdown(CountdownListener listener, Plugin plugin){
        this.plugin = plugin;
        this.listener = listener;
        sleeping = true;
        listener.onSleepStart();
        task = Bukkit.getScheduler().runTaskTimer(plugin, listener::onSleep, 20, 20);
    }

    public void start(int seconds){
        this.seconds = seconds;
        if(sleeping){
            task.cancel();
            sleeping = false;
            listener.onSleepStop();
        }
        listener.onStart();
        task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if(this.seconds > 0) {
                this.seconds--;
                listener.onUpdate(seconds);
            }
            else {
                task.cancel();
                listener.onStop();
            }
        }, 20, 20);
    }

    public void stop(boolean callListener){
        if(!sleeping) {
            sleeping = true;
            if (callListener) {
                listener.onStop();
                listener.onSleepStart();
            }
            task = Bukkit.getScheduler().runTaskTimer(plugin, listener::onSleep, 20, 20);
        }
    }

    public void restart(int seconds){
        stop(false);
        listener.onStop();
        start(seconds);
    }

    public void sleep(){
        stop(true);
    }
}
