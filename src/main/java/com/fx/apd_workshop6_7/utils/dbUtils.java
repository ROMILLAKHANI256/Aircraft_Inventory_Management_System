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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class dbUtils {

    /**
     * Executes SQL commands from a file.
     * @param conn Active DB connection
     * @param filePath Path to SQL file
     * @throws SQLException
     * @throws IOException
     */
    public static void runSqlScript(Connection conn, String filePath) throws SQLException, IOException {
        StringBuilder sqlBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Skip comments and empty lines
                line = line.trim();
                if (line.isEmpty() || line.startsWith("--") || line.startsWith("//")) {
                    continue;
                }
                sqlBuilder.append(line).append(" ");
            }
        }

        String[] sqlCommands = sqlBuilder.toString().split(";");

        try (Statement stmt = conn.createStatement()) {
            for (String cmd : sqlCommands) {
                String trimmed = cmd.trim();
                if (!trimmed.isEmpty()) {
                    stmt.execute(trimmed);
                }
            }
        }
    }
}
