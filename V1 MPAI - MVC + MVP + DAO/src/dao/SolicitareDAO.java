package dao;

import model.DatabaseManager;
import model.state.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SolicitareDAO implements IDao<Solicitare> {

    @Override
    public void insert(Solicitare solicitare) throws IOException {
        try (Connection conn = DatabaseManager.connect()) {
            String sql = "INSERT INTO Requests (clientId, description, address, status) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, solicitare.getClientId());
                stmt.setString(2, solicitare.getDescription());
                stmt.setString(3, solicitare.getAddress());
                stmt.setString(4, solicitare.getState().getClass().getSimpleName());
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    solicitare.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException("Error inserting solicitare into the database", e);
        }
    }

    @Override
    public void update(Solicitare solicitare) throws IOException {
        try (Connection conn = DatabaseManager.connect()) {
            String sql = "UPDATE Requests SET clientId = ?, description = ?, address = ?, status = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, solicitare.getClientId());
                stmt.setString(2, solicitare.getDescription());
                stmt.setString(3, solicitare.getAddress());
                stmt.setString(4, solicitare.getState().getClass().getSimpleName());
                stmt.setInt(5, solicitare.getId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException("Error updating solicitare in the database", e);
        }
    }

    @Override
    public void delete(Solicitare solicitare) throws IOException {
        try (Connection conn = DatabaseManager.connect()) {
            String sql = "DELETE FROM Requests WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, solicitare.getId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException("Error deleting solicitare from the database", e);
        }
    }

    public Solicitare getById(int id) {
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Requests WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Solicitare solicitare = new Solicitare(
                        rs.getInt("clientId"),
                        rs.getString("description"),
                        rs.getString("address")
                );
                solicitare.setId(rs.getInt("id"));

                // Setează starea pe baza valorii din baza de date
                String status = rs.getString("status");
                solicitare.setState(parseState(status));

                return solicitare;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static RequestState parseState(String status) {
        switch (status) {
            case "InAnalizaState":
                return new InAnalizaState();
            case "AcceptataState":
                return new AcceptataState();
            case "EchipajPlecatState":
                return new EchipajPlecatState();
            case "RespinsaState":
                return new RespinsaState();
            // Adaugă alte stări după caz
            default:
                throw new IllegalArgumentException("Unknown state: " + status);
        }
    }
    @Override
    public List<Solicitare> getAll() {
        List<Solicitare> requests = new ArrayList<>();
        try (Connection conn = DatabaseManager.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Requests")) {
            while (rs.next()) {
                Solicitare solicitare = new Solicitare(
                        rs.getInt("clientId"),
                        rs.getString("description"),
                        rs.getString("address")
                );
                solicitare.setId(rs.getInt("id"));
                requests.add(solicitare);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }
}
