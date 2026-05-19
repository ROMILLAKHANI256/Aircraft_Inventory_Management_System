

package com.fx.Aircraft_Inventory_Management_System.model;

import java.io.Serializable;

public class Outsourced extends Part implements Serializable {
    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
}