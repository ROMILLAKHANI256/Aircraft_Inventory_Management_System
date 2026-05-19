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
import java.sql.*;
public class Database {

        private static final String DB_URL = "jdbc:sqlite:inventory.db";

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(DB_URL);
        }

}
