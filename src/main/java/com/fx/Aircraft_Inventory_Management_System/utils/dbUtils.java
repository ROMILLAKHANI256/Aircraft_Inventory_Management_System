
package com.fx.Aircraft_Inventory_Management_System.utils;

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
