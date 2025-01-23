package model;

import model.observer.Observer;

import java.sql.*;

public class Client implements Observer {
    private int id;
    private String name;
    private String phone;

    public Client(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public static Client find(int id) {
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

    public void save() {
        try (Connection conn = DatabaseManager.connect()) {
            if (id == 0) { // Insert
                String sql = "INSERT INTO Clients (name, phone) VALUES (?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setString(1, name);
                    stmt.setString(2, phone);
                    stmt.executeUpdate();
                    ResultSet rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        this.id = rs.getInt(1);
                    }
                }
            } else { // Update
                String sql = "UPDATE Clients SET name = ?, phone = ? WHERE id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, name);
                    stmt.setString(2, phone);
                    stmt.setInt(3, id);
                    stmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        if (id != 0) {
            try (Connection conn = DatabaseManager.connect();
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM Clients WHERE id = ?")) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
                id = 0; // Mark as deleted
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(String message) {
        System.out.println("Notificare pentru " + name + ": " + message);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
