package de.exfy.tacticalisland.maps;

public interface CountdownListener {

    void onSleep();
    void onSleepStart();
    void onSleepStop();

    void onUpdate(int seconds);
    void onStop();
    void onStart();

}
