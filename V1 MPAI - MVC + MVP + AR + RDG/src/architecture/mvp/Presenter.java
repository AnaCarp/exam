package architecture.mvp;

import model.Client;
import model.state.Solicitare;


import java.util.ArrayList;
import java.util.List;

public class Presenter {
    private final ViewMVP view;

    public Presenter(ViewMVP view) {
        this.view = view;
        initializeDatabase();
    }

    private void initializeDatabase() {
        // Inițializează baza de date cu câțiva clienți dacă nu există
        if (Client.find(1) == null) {
            Client client1 = new Client(0, "Maria Popescu", "0723000000");
            client1.save();
        }
        if (Client.find(2) == null) {
            Client client2 = new Client(0, "Ion Ionescu", "0734000000");
            client2.save();
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
        int clientId = view.getClientIdForNewRequest();
        String description = view.getDescriptionForNewRequest();
        String address = view.getAddressForNewRequest();

        Client client = Client.find(clientId);
        if (client == null) {
            view.showError("Clientul cu ID-ul specificat nu există.");
            return;
        }

        Solicitare solicitare = new Solicitare(clientId, description, address);
        solicitare.addObserver(client);
        solicitare.save();
        view.showMessage("Solicitarea a fost adăugată.");
    }

    private void handleViewRequests() {
        List<Solicitare> requests = new ArrayList<>();
        for (int i = 1; ; i++) {
            Solicitare solicitare = Solicitare.find(i);
            if (solicitare == null) break;

            view.displaySolicitare(solicitare.getClientId(), solicitare.getDescription(), solicitare.getAddress(), solicitare.getState().getClass().getSimpleName());
        }
    }

    private void handleProcessRequest() {
        int requestId = view.getRequestIdForUpdate();
        Solicitare solicitare = Solicitare.find(requestId);
        if (solicitare == null) {
            view.showError("Solicitarea nu există.");
            return;
        }

        solicitare.process();
        solicitare.save();
        view.showMessage("Solicitarea a fost procesată.");
    }

    private void handleViewClients() {
        for (int i = 1; ; i++) {
            Client client = Client.find(i);
            if (client == null) break;

            view.displayClient(client.getId(), client.getName(), client.getPhone());
        }
    }

    private void handleSearchRequestById() {
        int requestId = view.getRequestIdForSearch();
        Solicitare solicitare = Solicitare.find(requestId);
        if (solicitare == null) {
            view.showError("Solicitarea nu a fost găsită.");
        } else {
            view.displaySolicitare(solicitare.getClientId(), solicitare.getDescription(), solicitare.getAddress(),solicitare.getState().getClass().getSimpleName());
        }
    }

    private void handleSearchRequestByClientName() {
        String clientName = view.getClientNameForSearch();
        List<Solicitare> matchingRequests = new ArrayList<>();

        for (int i = 1; ; i++) {
            Client client = Client.find(i);
            if (client == null) break;

            if (client.getName().equalsIgnoreCase(clientName)) {
                for (int j = 1; ; j++) {
                    Solicitare solicitare = Solicitare.find(j);
                    if (solicitare == null) break;

                    if (solicitare.getClientId() == client.getId()) {
                        matchingRequests.add(solicitare);
                    }
                }
            }
        }

        if (matchingRequests.isEmpty()) {
            view.showError("Nu au fost găsite solicitări pentru clientul " + clientName + ".");
        } else {
            for (Solicitare solicitare : matchingRequests) {
                view.displaySolicitare(solicitare.getClientId(), solicitare.getDescription(), solicitare.getAddress(),solicitare.getState().getClass().getSimpleName());
            }
        }
    }
}
