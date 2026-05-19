
package com.fx.Aircraft_Inventory_Management_System.databases;
import java.sql.*;
public class Database {

        private static final String DB_URL = "jdbc:sqlite:inventory.db";

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(DB_URL);
        }

}
