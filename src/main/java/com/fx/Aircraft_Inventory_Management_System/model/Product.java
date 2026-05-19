
package com.fx.Aircraft_Inventory_Management_System.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Product implements Serializable {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private List<Part> associatedParts;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.associatedParts = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public int getMin() { return min; }
    public int getMax() { return max; }
    public List<Part> getAssociatedParts() { return associatedParts; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }
    public void setMin(int min) { this.min = min; }
    public void setMax(int max) { this.max = max; }

    public void addAssociatedPart(Part part) {
        if (!associatedParts.contains(part)) {
            associatedParts.add(part);
        }
    }

    public boolean removeAssociatedPart(Part part) {
        return associatedParts.remove(part);
    }

    public Part lookupAssociatedPart(int partId) {
        for (Part part : associatedParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }
}
