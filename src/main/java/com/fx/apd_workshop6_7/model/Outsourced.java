/**********************************************
 Workshop # 6 and 7
 Course: APD545
 Semester: 5
 Last Name: Lakhani
 First Name: Romil
 Student ID: 171612229
 Section: NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature: Romil Lakhani
 Date: 01-08-2025
 **********************************************/

package com.fx.apd_workshop6_7.model;

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