package architecture.mvc;

import architecture.mvc.ViewMVC;
import model.Client;
import model.state.Solicitare;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private final ViewMVC view;

    public Controller(ViewMVC view) {
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
        Solicitare solicitare = view.getNewRequestData();
        Client client = Client.find(solicitare.getClientId());
        if (client == null) {
            view.showError("Clientul cu ID-ul specificat nu există.");
            return;
        }
        solicitare.addObserver(client);
        solicitare.save();
        view.showMessage("Solicitarea a fost adăugată.");
    }




    private void handleViewRequests() {
        List<Solicitare> requests = new ArrayList<>();
        for (int i = 1; ; i++) {
            Solicitare solicitare = Solicitare.find(i);
            if (solicitare == null) break;
            requests.add(solicitare);
        }
        view.showRequests(requests);
    }

    private void handleProcessRequest() {
        int requestId = view.getRequestIdForUpdate();
        Solicitare request = Solicitare.find(requestId);
        if (request == null) {
            System.out.println("Solicitarea nu există.");
            return;
        }
        request.process(); // Procesează starea curentă
        request.update(); // Salvează starea actualizată
        System.out.println("Solicitarea a fost procesată și actualizată.");
    }

    private void handleViewClients() {
        List<Client> clients = new ArrayList<>();
        for (int i = 1; ; i++) {
            Client client = Client.find(i);
            if (client == null) break;
            clients.add(client);
        }
        view.showClients(clients);
    }

    private void handleSearchRequestById() {
        int id = view.getRequestIdForSearch();
        Solicitare solicitare = Solicitare.find(id);
        if (solicitare == null) {
            view.showError("Solicitarea nu a fost găsită.");
        } else {
            view.showRequests(List.of(solicitare));
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
            view.showRequests(matchingRequests);
        }
    }
}
