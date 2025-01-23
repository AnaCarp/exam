package dao;

import model.Client;
import model.DatabaseManager;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO  implements IDao<Client>{

    @Override
    public void insert(Client client) throws IOException {
        try (Connection conn = DatabaseManager.connect()) {
            String sql = "INSERT INTO Clients (name, phone) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, client.getName());
                stmt.setString(2, client.getPhone());
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    client.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException("Error inserting client into the database", e);
        }
    }

    @Override
    public void update(Client client) throws IOException {
        try (Connection conn = DatabaseManager.connect()) {
            String sql = "UPDATE Clients SET name = ?, phone = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, client.getName());
                stmt.setString(2, client.getPhone());
                stmt.setInt(3, client.getId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException("Error updating client in the database", e);
        }
    }

    @Override
    public void delete(Client client) throws IOException {
        try (Connection conn = DatabaseManager.connect()) {
            String sql = "DELETE FROM Clients WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, client.getId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException("Error deleting client from the database", e);
        }
    }

    @Override
    public Client getById(int id) {
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Clients WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Client(rs.getInt("id"), rs.getString("name"), rs.getString("phone"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Client> getAll() {
        List<Client> clients = new ArrayList<>();
        try (Connection conn = DatabaseManager.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Clients")) {
            while (rs.next()) {
                clients.add(new Client(rs.getInt("id"), rs.getString("name"), rs.getString("phone")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }
}
