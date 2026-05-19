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
package com.fx.apd_workshop6_7.databases;

import com.fx.apd_workshop6_7.model.Inventory;
import com.fx.apd_workshop6_7.model.Part;
import com.fx.apd_workshop6_7.model.Product;
import com.fx.apd_workshop6_7.model.InHouse;
import  com.fx.apd_workshop6_7.model.Outsourced;
import com.fx.apd_workshop6_7.utils.dbUtils;

import java.sql.*;

public class InventoryDAO {


    public static void recreateDatabaseTables(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            System.out.println("Dropping tables if exist...");
            stmt.execute("DROP TABLE IF EXISTS product_parts;");
            stmt.execute("DROP TABLE IF EXISTS products;");
            stmt.execute("DROP TABLE IF EXISTS parts;");

            System.out.println("Creating tables...");
            String createPartsTable = "CREATE TABLE parts (" +
                    "id INTEGER PRIMARY KEY," +
                    "name TEXT," +
                    "price REAL," +
                    "stock INTEGER," +
                    "min INTEGER," +
                    "max INTEGER," +
                    "machineId INTEGER," +        // For InHouse parts
                    "companyName TEXT," +         // For Outsourced parts
                    "partType TEXT" +             // "InHouse" or "Outsourced"
                    ");";

            String createProductsTable = "CREATE TABLE products (" +
                    "id INTEGER PRIMARY KEY," +
                    "name TEXT," +
                    "price REAL," +
                    "stock INTEGER," +
                    "min INTEGER," +
                    "max INTEGER" +
                    ");";

            String createAssociationTable = "CREATE TABLE product_parts (" +
                    "product_id INTEGER," +
                    "part_id INTEGER," +
                    "PRIMARY KEY (product_id, part_id)," +
                    "FOREIGN KEY (product_id) REFERENCES products(id)," +
                    "FOREIGN KEY (part_id) REFERENCES parts(id)" +
                    ");";

            stmt.execute(createPartsTable);
            stmt.execute(createProductsTable);
            stmt.execute(createAssociationTable);
            System.out.println("Tables created successfully.");
        }
    }

    public static void saveInventoryToDatabase() {
        try (Connection conn = Database.getConnection()) {
            conn.setAutoCommit(false);

            // Recreate tables fresh on each save
            recreateDatabaseTables(conn);

            // Insert parts
            String insertPart = "INSERT INTO parts (id, name, price, stock, min, max, machineId, companyName, partType) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
            try (PreparedStatement ps = conn.prepareStatement(insertPart)) {
                for (Part part : Inventory.getAllParts()) {
                    ps.setInt(1, part.getId());
                    ps.setString(2, part.getName());
                    ps.setDouble(3, part.getPrice());
                    ps.setInt(4, part.getStock());
                    ps.setInt(5, part.getMin());
                    ps.setInt(6, part.getMax());

                    if (part instanceof InHouse) {
                        ps.setInt(7, ((InHouse) part).getMachineId());
                        ps.setNull(8, Types.VARCHAR);
                        ps.setString(9, "InHouse");
                    } else if (part instanceof Outsourced) {
                        ps.setNull(7, Types.INTEGER);
                        ps.setString(8, ((Outsourced) part).getCompanyName());
                        ps.setString(9, "Outsourced");
                    } else {
                        // Default or unknown part type
                        ps.setNull(7, Types.INTEGER);
                        ps.setNull(8, Types.VARCHAR);
                        ps.setString(9, "Unknown");
                    }

                    ps.addBatch();
                }
                ps.executeBatch();
            }

            // Insert products
            String insertProduct = "INSERT INTO products (id, name, price, stock, min, max) VALUES (?, ?, ?, ?, ?, ?);";
            try (PreparedStatement ps = conn.prepareStatement(insertProduct)) {
                for (Product product : Inventory.getAllProducts()) {
                    ps.setInt(1, product.getId());
                    ps.setString(2, product.getName());
                    ps.setDouble(3, product.getPrice());
                    ps.setInt(4, product.getStock());
                    ps.setInt(5, product.getMin());
                    ps.setInt(6, product.getMax());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            // Insert product-part associations
            String insertAssociation = "INSERT INTO product_parts (product_id, part_id) VALUES (?, ?);";
            try (PreparedStatement ps = conn.prepareStatement(insertAssociation)) {
                for (Product product : Inventory.getAllProducts()) {
                    for (Part part : product.getAssociatedParts()) {
                        ps.setInt(1, product.getId());
                        ps.setInt(2, part.getId());
                        ps.addBatch();
                    }
                }
                ps.executeBatch();
            }

            conn.commit();

            System.out.println("Inventory saved to database successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error saving inventory to database: " + e.getMessage());
        }
    }

    public static void loadInventoryFromDatabase() throws SQLException {
        try (Connection conn = Database.getConnection()) {
            Inventory.getAllParts().clear();
            Inventory.getAllProducts().clear();

            // Load parts
            String selectParts = "SELECT * FROM parts;";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(selectParts)) {

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    double price = rs.getDouble("price");
                    int stock = rs.getInt("stock");
                    int min = rs.getInt("min");
                    int max = rs.getInt("max");
                    String partType = rs.getString("partType");

                    if ("InHouse".equals(partType)) {
                        int machineId = rs.getInt("machineId");
                        Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
                    } else if ("Outsourced".equals(partType)) {
                        String companyName = rs.getString("companyName");
                        Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));

                    }
                }

                // Load products
                String selectProducts = "SELECT * FROM products;";
                try (Statement stmt2 = conn.createStatement();
                     ResultSet rs2 = stmt.executeQuery(selectProducts)) {

                    while (rs2.next()) {
                        int id = rs2.getInt("id");
                        String name = rs2.getString("name");
                        double price = rs2.getDouble("price");
                        int stock = rs2.getInt("stock");
                        int min = rs2.getInt("min");
                        int max = rs2.getInt("max");

                        Product product = new Product(id, name, price, stock, min, max);

                        // Load associated parts
                        String selectAssociations = "SELECT part_id FROM product_parts WHERE product_id = ?;";
                        try (PreparedStatement psAssoc = conn.prepareStatement(selectAssociations)) {
                            psAssoc.setInt(1, id);
                            try (ResultSet rsAssoc = psAssoc.executeQuery()) {
                                while (rsAssoc.next()) {
                                    int partId = rsAssoc.getInt("part_id");
                                    Part part = Inventory.lookupPart(partId);
                                    if (part != null) {
                                        product.addAssociatedPart(part);
                                    }
                                }
                            }
                        }

                        Inventory.addProduct(product);
                    }
                }

                System.out.println("Inventory loaded from database successfully.");
                Inventory.refreshIDCounters();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error loading inventory from database: " + e.getMessage());
            }
        }

    }
    public static void initializeDatabaseFromFile(String sqlFilePath) {
        try (Connection conn = Database.getConnection()) {
            conn.setAutoCommit(false);
            dbUtils.runSqlScript(conn, sqlFilePath);
            conn.commit();
            System.out.println("Database initialized successfully from SQL file.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



