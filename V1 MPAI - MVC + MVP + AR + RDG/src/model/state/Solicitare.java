package model.state;

import model.DatabaseManager;
import model.observer.Observer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Solicitare {
    private int id;
    private int clientId;
    private String description;
    private String address;
    private RequestState state;
    private List<Observer> observers = new ArrayList<>();

    public Solicitare(int clientId, String description, String address) {
        this.clientId = clientId;
        this.description = description;
        this.address = address;
        this.state = new InAnalizaState(); // Setare implicită doar pentru solicitările noi
    }

    public static Solicitare find(int id) {
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
                solicitare.id = rs.getInt("id");

                // Setează starea pe baza valorii din baza de date
                String status = rs.getString("status");
                solicitare.state = parseState(status);

                return solicitare;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void save() {
        try (Connection conn = DatabaseManager.connect()) {
            if (id == 0) { // Insert
                String sql = "INSERT INTO Requests (clientId, description, address, status) VALUES (?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setInt(1, clientId);
                    stmt.setString(2, description);
                    stmt.setString(3, address);
                    System.out.println();
                    stmt.setString(4, state.getClass().getSimpleName());
                    stmt.executeUpdate();
                    ResultSet rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        this.id = rs.getInt(1);
                    }
                }
            } else { // Update
                String sql = "UPDATE Requests SET clientId = ?, description = ?, address = ?, status = ? WHERE id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, clientId);
                    stmt.setString(2, description);
                    stmt.setString(3, address);
                    stmt.setString(4, state.getClass().getSimpleName());
                    stmt.setInt(5, id);
                    stmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        try (Connection conn = DatabaseManager.connect()) {

                String sql = "UPDATE Requests SET clientId = ?, description = ?, address = ?, status = ? WHERE id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, clientId);
                    stmt.setString(2, description);
                    stmt.setString(3, address);
                    stmt.setString(4, state.getClass().getSimpleName());
                    stmt.setInt(5, id);
                    stmt.executeUpdate();
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete() {
        if (id != 0) {
            try (Connection conn = DatabaseManager.connect();
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM Requests WHERE id = ?")) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
                id = 0; // Mark as deleted
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setState(RequestState state) {
        this.state = state;
    }

    public void process() {
        state.process(this);
    }

    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public int getId() {
        return id;
    }

    public int getClientId() {
        return clientId;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public RequestState getState() {
        return state;
    }

    @Override
    public String toString() {
        return "Solicitare{id=" + id + ", clientId=" + clientId +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", state=" + state.getClass().getSimpleName() + '}';
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

}
