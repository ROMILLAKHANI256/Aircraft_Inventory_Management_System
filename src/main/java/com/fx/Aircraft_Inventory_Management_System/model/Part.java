
package com.fx.Aircraft_Inventory_Management_System.model;

import java.io.Serializable;

public abstract class Part implements Serializable {
    private int id, stock, min, max;
    private String name;
    private double price;

    public Part(int id, String name, double price, int stock, int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Min cannot be greater than Max.");
        }
        if (stock < min || stock > max) {
            throw new IllegalArgumentException("Stock must be between Min and Max.");
        }

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public int getMin() { return min; }
    public int getMax() { return max; }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.name = name;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.price = price;
    }

    public void setStock(int stock) {
        if (stock < min || stock > max) {
            throw new IllegalArgumentException("Stock must be between Min and Max.");
        }
        this.stock = stock;
    }

    public void setMin(int min) {
        if (min > max) {
            throw new IllegalArgumentException("Min cannot be greater than Max.");
        }
        this.min = min;
    }

    public void setMax(int max) {
        if (max < min) {
            throw new IllegalArgumentException("Max cannot be less than Min.");
        }
        this.max = max;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID cannot be negative.");
        }
        this.id = id;
    }
}
