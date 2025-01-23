package architecture.mvp;

import dataMapper.ClientDataMapper;
import dataMapper.SolicitareDataMapper;
import model.Client;
import model.state.Solicitare;

import java.io.IOException;
import java.util.List;

public class Presenter {
    private final ViewMVP view;
    private final ClientDataMapper clientDAO;
    private final SolicitareDataMapper solicitareDAO;

    public Presenter(ViewMVP view) {
        this.view = view;
        this.clientDAO = new ClientDataMapper();
        this.solicitareDAO = new SolicitareDataMapper();
        initializeDatabase();
    }

    private void initializeDatabase() {
        try {
            if (clientDAO.getById(1) == null) {
                clientDAO.insert(new Client(0, "Maria Popescu", "0723000000"));
            }
            if (clientDAO.getById(2) == null) {
                clientDAO.insert(new Client(0, "Ion Ionescu", "0734000000"));
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
        try {
            int clientId = view.getClientIdForNewRequest();
            String description = view.getDescriptionForNewRequest();
            String address = view.getAddressForNewRequest();

            Client client = clientDAO.getById(clientId);
            if (client == null) {
                view.showError("Clientul cu ID-ul specificat nu există.");
                return;
            }

            Solicitare solicitare = new Solicitare(clientId, description, address);
            solicitare.addObserver(client);
            solicitareDAO.insert(solicitare);
            view.showMessage("Solicitarea a fost adăugată.");
        } catch (IOException e) {
            view.showError("Eroare la adăugarea solicitării: " + e.getMessage());
        }
    }

    private void handleViewRequests() {
        List<Solicitare> requests = solicitareDAO.getAll();
        if (requests.isEmpty()) {
            view.showMessage("Nu există solicitări.");
        } else {
            for (Solicitare solicitare : requests) {
                view.displaySolicitare(solicitare.getClientId(), solicitare.getDescription(), solicitare.getAddress(), solicitare.getState().getClass().getSimpleName());
            }
        }
    }

    private void handleProcessRequest() {
        try {
            int requestId = view.getRequestIdForUpdate();
            Solicitare solicitare = solicitareDAO.getById(requestId);
            if (solicitare == null) {
                view.showError("Solicitarea nu există.");
                return;
            }

            solicitare.process();
            solicitareDAO.update(solicitare);
            view.showMessage("Solicitarea a fost procesată.");
        } catch (IOException e) {
            view.showError("Eroare la procesarea solicitării: " + e.getMessage());
        }
    }

    private void handleViewClients() {
        List<Client> clients = clientDAO.getAll();
        if (clients.isEmpty()) {
            view.showMessage("Nu există clienți.");
        } else {
            for (Client client : clients) {
                view.displayClient(client.getId(), client.getName(), client.getPhone());
            }
        }
    }

    private void handleSearchRequestById() {
        int requestId = view.getRequestIdForSearch();
        Solicitare solicitare = solicitareDAO.getById(requestId);
        if (solicitare == null) {
            view.showError("Solicitarea nu a fost găsită.");
        } else {
            view.displaySolicitare(solicitare.getClientId(), solicitare.getDescription(), solicitare.getAddress(), solicitare.getState().getClass().getSimpleName());
        }
    }

    private void handleSearchRequestByClientName() {
        String clientName = view.getClientNameForSearch();
        List<Client> clients = clientDAO.getAll();
        List<Solicitare> matchingRequests = solicitareDAO.getAll().stream()
                .filter(request -> clients.stream()
                        .anyMatch(client -> client.getId() == request.getClientId() && client.getName().equalsIgnoreCase(clientName)))
                .toList();

        if (matchingRequests.isEmpty()) {
            view.showError("Nu au fost găsite solicitări pentru clientul " + clientName + ".");
        } else {
            for (Solicitare solicitare : matchingRequests) {
                view.displaySolicitare(solicitare.getClientId(), solicitare.getDescription(), solicitare.getAddress(), solicitare.getState().getClass().getSimpleName());
            }
        }
    }
}
