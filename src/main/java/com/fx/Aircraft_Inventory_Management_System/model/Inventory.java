

package com.fx.Aircraft_Inventory_Management_System.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;

public class Inventory  implements Serializable {
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static int productIDCounter = 1;
    private static int partIDCounter = 1;

//     if you want to initialize the data you can do it from here too
//    static {
//        try {
//            Part part1 = new InHouse(partIDCounter++, "Jet Engine", 1200000.00, 2, 1, 5, 1001);
//            Part part2 = new InHouse(partIDCounter++, "Landing Gear", 450000.00, 4, 1, 6, 1002);
//            Part part3 = new InHouse(partIDCounter++, "Avionics System", 250000.00, 5, 1, 10, 1003);
//            Part part4 = new InHouse(partIDCounter++, "Cabin Seat", 1200.00, 100, 20, 200, 1004);
//            Part part5 = new InHouse(partIDCounter++, "Cockpit Display", 35000.00, 6, 1, 10, 1005);
//            Part part6 = new InHouse(partIDCounter++, "Wing Flap", 18000.00, 10, 2, 15, 1006);
//            Part part7 = new InHouse(partIDCounter++, "Oxygen Mask", 800.00, 50, 10, 100, 1007);
//            Part part8 = new InHouse(partIDCounter++, "Fuel Pump", 15000.00, 7, 2, 10, 1008);
//            Part part9 = new InHouse(partIDCounter++, "Hydraulic System", 40000.00, 3, 1, 5, 1009);
//            Part part10 = new InHouse(partIDCounter++, "Radar System", 60000.00, 2, 1, 3, 1010);
//
//            allParts.addAll(part1, part2, part3, part4, part5, part6, part7, part8, part9, part10);
//
//            Product product1 = new Product(productIDCounter++, "Cargo Plane A1", 10000000.00, 2, 1, 4);
//            product1.addAssociatedPart(part1);
//            product1.addAssociatedPart(part2);
//
//            Product product2 = new Product(productIDCounter++, "Passenger Jet B2", 25000000.00, 3, 1, 5);
//            product2.addAssociatedPart(part3);
//            product2.addAssociatedPart(part4);
//            product2.addAssociatedPart(part5);
//
//            Product product3 = new Product(productIDCounter++, "Fighter Jet C3", 40000000.00, 1, 1, 2);
//            product3.addAssociatedPart(part1);
//            product3.addAssociatedPart(part9);
//
//            Product product4 = new Product(productIDCounter++, "Private Jet D4", 15000000.00, 2, 1, 3);
//            product4.addAssociatedPart(part4);
//            product4.addAssociatedPart(part5);
//
//            Product product5 = new Product(productIDCounter++, "Helicopter E5", 12000000.00, 4, 1, 6);
//            product5.addAssociatedPart(part6);
//            product5.addAssociatedPart(part8);
//
//            Product product6 = new Product(productIDCounter++, "Glider F6", 300000.00, 5, 1, 10);
//            product6.addAssociatedPart(part4);
//
//            Product product7 = new Product(productIDCounter++, "Drone G7", 150000.00, 10, 2, 20);
//            product7.addAssociatedPart(part7);
//            product7.addAssociatedPart(part10);
//
//            Product product8 = new Product(productIDCounter++, "Training Plane H8", 2500000.00, 6, 1, 8);
//            product8.addAssociatedPart(part3);
//            product8.addAssociatedPart(part4);
//
//            Product product9 = new Product(productIDCounter++, "Surveillance Jet I9", 22000000.00, 1, 1, 3);
//            product9.addAssociatedPart(part10);
//            product9.addAssociatedPart(part1);
//
//            Product product10 = new Product(productIDCounter++, "Bomber Jet J10", 55000000.00, 1, 1, 2);
//            product10.addAssociatedPart(part9);
//            product10.addAssociatedPart(part2);
//
//            allProducts.addAll(product1, product2, product3, product4, product5,
//                    product6, product7, product8, product9, product10);
//
//        } catch (IllegalArgumentException e) {
//            System.out.println("Error initializing inventory data: " + e.getMessage());
//        }
//    }
//
//
//
    public static void refreshIDCounters() {
        for (Part part : allParts) {
            if (part.getId() >= partIDCounter) {
                partIDCounter = part.getId() + 1;
            }
        }
        for (Product product : allProducts) {
            if (product.getId() >= productIDCounter) {
                productIDCounter = product.getId() + 1;
            }
        }
    }

    public static int peekNextPartID() {
        return partIDCounter;
    }

    public static int peekNextProductID() {
        return productIDCounter;
    }

    public static int consumeNextPartID() {
        return partIDCounter++;
    }

    public static int consumeNextProductID() {
        return productIDCounter++;
    }


    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    public static void addPart(Part part) {
        allParts.add(part);
    }

    public static boolean deletePart(Part part) {
        return allParts.remove(part);
    }

    public static boolean deleteProduct(Product product) {
        return allProducts.remove(product);
    }

    public static Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    public static Product lookupProduct(int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    public static void updatePart(int index, Part newPart) {
        if (index >= 0 && index < allParts.size()) {
            allParts.set(index, newPart);
        }
    }
}