package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:cabinet.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initializeDatabase() {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            String createClientsTable = """
                CREATE TABLE IF NOT EXISTS Clients (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    phone TEXT NOT NULL
                );
            """;

            String createRequestsTable = """
                CREATE TABLE IF NOT EXISTS Requests (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    clientId INTEGER,
                    description TEXT NOT NULL,
                    address TEXT NOT NULL,
                    status TEXT NOT NULL,
                    estimatedArrivalTime TEXT,
                    FOREIGN KEY (clientId) REFERENCES Clients(id)
                );
            """;

            stmt.execute(createClientsTable);
            stmt.execute(createRequestsTable);

        } catch (SQLException e) {
            System.err.println("Database initialization error: " + e.getMessage());
        }
    }
}
