

package com.fx.Aircraft_Inventory_Management_System.model;
import java.io.Serializable;

public class InHouse extends Part implements Serializable {
    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    public int getMachineId() { return machineId; }
    public void setMachineId(int machineId) { this.machineId = machineId; }
}
