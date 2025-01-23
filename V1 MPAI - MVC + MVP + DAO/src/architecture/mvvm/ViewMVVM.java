package architecture.mvvm;

import model.Client;
import model.state.Solicitare;

import java.util.List;
import java.util.Scanner;

public class ViewMVVM {

    private final ViewModel viewModel;

    public ViewMVVM(ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void start() {
        boolean running = true;
        while (running) {
            int choice = displayMainMenu();
            switch (choice) {
                case 1 -> handleAddRequest();
                case 2 -> handleViewRequests();
                case 3 -> handleProcessRequest();
                case 4 -> handleViewClients();
                case 5 -> handleSearchRequestById();
                case 6 -> handleSearchRequestByClientName();
                case 0 -> running = false;
                default -> showError("Opțiune invalidă!");
            }
        }
    }

    private int displayMainMenu() {
        System.out.println("\n===== Meniu Principal =====");
        System.out.println("1. Adaugă solicitare");
        System.out.println("2. Vizualizează solicitări");
        System.out.println("3. Treci solicitare în următoarea stare");
        System.out.println("4. Vizualizează clienți");
        System.out.println("5. Caută solicitare după ID");
        System.out.println("6. Caută solicitare după numele clientului");
        System.out.println("0. Ieșire");
        System.out.print("Alege o opțiune: ");
        return new Scanner(System.in).nextInt();
    }

    private void handleAddRequest() {
        Solicitare solicitare = getNewRequestData();
        try {
            viewModel.addRequest(solicitare);
            showMessage("Solicitarea a fost adăugată.");
        } catch (Exception e) {
            showError("Eroare la adăugarea solicitării: " + e.getMessage());
        }
    }

    private void handleViewRequests() {
        try {
            List<Solicitare> requests = viewModel.getAllRequests();
            if (requests.isEmpty()) {
                showMessage("Nu există solicitări.");
            } else {
                showRequests(requests);
            }
        } catch (Exception e) {
            showError("Eroare la afișarea solicitărilor: " + e.getMessage());
        }
    }

    private void handleProcessRequest() {
        int requestId = getRequestIdForUpdate();
        try {
            Solicitare request = viewModel.findRequestById(requestId);
            if (request == null) {
                showError("Solicitarea nu există.");
                return;
            }
            viewModel.processRequest(request);
            showMessage("Solicitarea a fost procesată și actualizată.");
        } catch (Exception e) {
            showError("Eroare la procesarea solicitării: " + e.getMessage());
        }
    }

    private void handleViewClients() {
        try {
            List<Client> clients = viewModel.getAllClients();
            if (clients.isEmpty()) {
                showMessage("Nu există clienți.");
            } else {
                showClients(clients);
            }
        } catch (Exception e) {
            showError("Eroare la afișarea clienților: " + e.getMessage());
        }
    }

    private void handleSearchRequestById() {
        int id = getRequestIdForSearch();
        try {
            Solicitare request = viewModel.findRequestById(id);
            if (request == null) {
                showError("Solicitarea nu a fost găsită.");
            } else {
                showRequests(List.of(request));
            }
        } catch (Exception e) {
            showError("Eroare la căutarea solicitării: " + e.getMessage());
        }
    }

    private void handleSearchRequestByClientName() {
        String clientName = getClientNameForSearch();
        try {
            List<Solicitare> requests = viewModel.findRequestsByClientName(clientName);
            if (requests.isEmpty()) {
                showError("Nu au fost găsite solicitări pentru clientul " + clientName + ".");
            } else {
                showRequests(requests);
            }
        } catch (Exception e) {
            showError("Eroare la căutarea solicitărilor: " + e.getMessage());
        }
    }

    public Solicitare getNewRequestData() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n===== Adaugă Solicitare =====");
        System.out.print("ID Client: ");
        int clientId = scanner.nextInt();
        scanner.nextLine(); // Consumă newline
        System.out.print("Descriere: ");
        String description = scanner.nextLine();
        System.out.print("Adresă: ");
        String address = scanner.nextLine();
        return new Solicitare(clientId, description, address);
    }

    public int getRequestIdForUpdate() {
        System.out.print("\nIntrodu ID-ul solicitării pentru procesare: ");
        return new Scanner(System.in).nextInt();
    }

    public int getRequestIdForSearch() {
        System.out.print("\nIntrodu ID-ul solicitării pentru căutare: ");
        return new Scanner(System.in).nextInt();
    }

    public String getClientNameForSearch() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nIntrodu numele clientului pentru căutare: ");
        return scanner.nextLine();
    }

    public void showRequests(List<Solicitare> requests) {
        System.out.println("\n===== Solicitări =====");
        for (Solicitare request : requests) {
            System.out.println(request);
        }
    }

    public void showClients(List<Client> clients) {
        System.out.println("\n===== Clienți =====");
        for (Client client : clients) {
            System.out.println(client);
        }
    }

    public void showMessage(String message) {
        System.out.println("\n" + message);
    }

    public void showError(String message) {
        System.err.println("\nEroare: " + message);
    }
}
