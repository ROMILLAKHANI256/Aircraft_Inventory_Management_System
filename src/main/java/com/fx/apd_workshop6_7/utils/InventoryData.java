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
package com.fx.apd_workshop6_7.utils;



import com.fx.apd_workshop6_7.model.Part;
import com.fx.apd_workshop6_7.model.Product;

import java.io.Serializable;
import java.util.List;

public class InventoryData implements Serializable {
    private List<Part> parts;
    private List<Product> products;

    public InventoryData(List<Part> parts, List<Product> products) {
        this.parts = parts;
        this.products = products;
    }

    public List<Part> getParts() {
        return parts;
    }

    public List<Product> getProducts() {
        return products;
    }
}
