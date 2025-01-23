package architecture.mvc;

import dao.ClientDAO;
import dao.SolicitareDAO;
import model.Client;
import model.state.Solicitare;

import java.io.IOException;
import java.util.List;

public class Controller {
    private final ViewMVC view;
    private final ClientDAO clientDAO;
    private final SolicitareDAO solicitareDAO;

    public Controller(ViewMVC view) {
        this.view = view;
        this.clientDAO = new ClientDAO();
        this.solicitareDAO = new SolicitareDAO();
        initializeDatabase();
    }

    private void initializeDatabase() {
        try {
            if (clientDAO.getById(1) == null) {
                Client client1 = new Client(0, "Maria Popescu", "0723000000");
                clientDAO.insert(client1);
            }
            if (clientDAO.getById(2) == null) {
                Client client2 = new Client(0, "Ion Ionescu", "0734000000");
                clientDAO.insert(client2);
            }
        } catch (IOException e) {
            view.showError("Eroare la inițializarea bazei de date: " + e.getMessage());
        }
    }

    public void start() {
        boolean running = true;
        while (running) {
            int choice = view.displayMainMenu();
            switch (choice) {
                case 1 -> handleAddRequest();
                case 2 -> handleViewRequests();
                case 3 -> handleProcessRequest();
                case 4 -> handleViewClients();
                case 5 -> handleSearchRequestById();
                case 6 -> handleSearchRequestByClientName();
                case 0 -> running = false;
                default -> view.showError("Opțiune invalidă!");
            }
        }
    }

    private void handleAddRequest() {
        Solicitare solicitare = view.getNewRequestData();
        try {
            Client client = clientDAO.getById(solicitare.getClientId());
            if (client == null) {
                view.showError("Clientul cu ID-ul specificat nu există.");
                return;
            }
            solicitare.addObserver(client);
            solicitareDAO.insert(solicitare);
            view.showMessage("Solicitarea a fost adăugată.");
        } catch (IOException e) {
            view.showError("Eroare la adăugarea solicitării: " + e.getMessage());
        }
    }

    private void handleViewRequests() {
        try {
            List<Solicitare> requests = solicitareDAO.getAll();
            if (requests.isEmpty()) {
                view.showMessage("Nu există solicitări.");
            } else {
                view.showRequests(requests);
            }
        } catch (Exception e) {
            view.showError("Eroare la afișarea solicitărilor: " + e.getMessage());
        }
    }

    private void handleProcessRequest() {
        int requestId = view.getRequestIdForUpdate();
        try {
            Solicitare request = solicitareDAO.getById(requestId);
            if (request == null) {
                view.showError("Solicitarea nu există.");
                return;
            }
            request.process();
            solicitareDAO.update(request);
            view.showMessage("Solicitarea a fost procesată și actualizată.");
        } catch (IOException e) {
            view.showError("Eroare la procesarea solicitării: " + e.getMessage());
        }
    }

    private void handleViewClients() {
        try {
            List<Client> clients = clientDAO.getAll();
            if (clients.isEmpty()) {
                view.showMessage("Nu există clienți.");
            } else {
                view.showClients(clients);
            }
        } catch (Exception e) {
            view.showError("Eroare la afișarea clienților: " + e.getMessage());
        }
    }

    private void handleSearchRequestById() {
        int id = view.getRequestIdForSearch();
        try {
            Solicitare solicitare = solicitareDAO.getById(id);
            if (solicitare == null) {
                view.showError("Solicitarea nu a fost găsită.");
            } else {
                view.showRequests(List.of(solicitare));
            }
        } catch (Exception e) {
            view.showError("Eroare la căutarea solicitării: " + e.getMessage());
        }
    }

    private void handleSearchRequestByClientName() {
        String clientName = view.getClientNameForSearch();
        try {
            List<Client> clients = clientDAO.getAll();
            boolean found = false;
            for (Client client : clients) {
                if (client.getName().equalsIgnoreCase(clientName)) {
                    List<Solicitare> matchingRequests = solicitareDAO.getAll();
                    matchingRequests.removeIf(solicitare -> solicitare.getClientId() != client.getId());
                    if (!matchingRequests.isEmpty()) {
                        view.showRequests(matchingRequests);
                        found = true;
                    }
                }
            }
            if (!found) {
                view.showError("Nu au fost găsite solicitări pentru clientul " + clientName + ".");
            }
        } catch (Exception e) {
            view.showError("Eroare la căutarea solicitărilor: " + e.getMessage());
        }
    }
}
