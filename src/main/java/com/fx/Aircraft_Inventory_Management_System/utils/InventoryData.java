
package com.fx.Aircraft_Inventory_Management_System.utils;



import com.fx.Aircraft_Inventory_Management_System.model.Part;
import com.fx.Aircraft_Inventory_Management_System.model.Product;

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
