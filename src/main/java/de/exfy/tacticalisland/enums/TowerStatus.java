package de.exfy.tacticalisland.enums;

public enum TowerStatus {

    NEUTRAL("§aNeutral"),
    TAKEN("§cBesetzt"),
    PROCESS("§4In Eroberung");

    private final String niceName;

    TowerStatus(String niceName){
        this.niceName = niceName;
    }

    public String getNiceName() {
        return niceName;
    }
}
