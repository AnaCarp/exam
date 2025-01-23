package architecture.mvvm;

import dao.ClientDAO;
import dao.SolicitareDAO;
import model.Client;
import model.state.Solicitare;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewModel {
    private final ClientDAO clientDAO;
    private final SolicitareDAO solicitareDAO;

    public ViewModel() {
        this.clientDAO = new ClientDAO();
        this.solicitareDAO = new SolicitareDAO();
    }

    // Metoda pentru a obține toți clienții
    public List<Client> getAllClients() {
        try {
            return clientDAO.getAll();
        } catch (Exception e) {
            throw new RuntimeException("Eroare la obținerea clienților", e);
        }
    }

    // Metoda pentru a adăuga o solicitare
    public void addRequest(Solicitare solicitare) {
        try {
            Client client = clientDAO.getById(solicitare.getClientId());
            if (client == null) {
                throw new IllegalArgumentException("Clientul nu există.");
            }
            solicitareDAO.insert(solicitare);
        } catch (IOException e) {
            throw new RuntimeException("Eroare la adăugarea solicitării", e);
        }
    }

    // Metoda pentru a obține toate solicitările
    public List<Solicitare> getAllRequests() {
        try {
            return solicitareDAO.getAll();
        } catch (Exception e) {
            throw new RuntimeException("Eroare la obținerea solicitărilor", e);
        }
    }

    // Metoda pentru a căuta solicitare după ID
    public Solicitare findRequestById(int id) {
        try {
            return solicitareDAO.getById(id);
        } catch (Exception e) {
            throw new RuntimeException("Eroare la căutarea solicitării", e);
        }
    }

    // Metoda pentru a căuta solicitări după numele clientului
    public List<Solicitare> findRequestsByClientName(String clientName) {
        List<Client> clients = clientDAO.getAll();
        List<Solicitare> results = new ArrayList<>();
        for (Client client : clients) {
            if (client.getName().equalsIgnoreCase(clientName)) {
                List<Solicitare> allRequests = solicitareDAO.getAll();
                for (Solicitare request : allRequests) {
                    if (request.getClientId() == client.getId()) {
                        results.add(request);
                    }
                }
            }
        }
        return results;
    }

    // Metoda pentru a procesa o solicitare
    public void processRequest(Solicitare request) {
        try {
            request.process();  // Presupunând că solicitarea trece prin stările corespunzătoare
            solicitareDAO.update(request);
        } catch (IOException e) {
            throw new RuntimeException("Eroare la procesarea solicitării", e);
        }
    }
}
