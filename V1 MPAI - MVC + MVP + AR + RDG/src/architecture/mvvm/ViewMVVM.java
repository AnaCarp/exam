package architecture.mvvm;

import java.util.Scanner;
import model.state.Solicitare;
import model.Client;
import java.util.List;

public class ViewMVVM {

    private ViewModel viewModel;

    public ViewMVVM(ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void start() {
        boolean running = true;
        Scanner scanner = new Scanner(System.in);

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

        scanner.close();
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
        viewModel.addRequest(solicitare);
        showMessage("Solicitarea a fost adăugată.");
    }

    private void handleViewRequests() {
        List<Solicitare> requests = viewModel.getAllRequests();
        showRequests(requests);
    }

    private void handleProcessRequest() {
        int requestId = getRequestIdForUpdate();
        Solicitare request = viewModel.findRequestById(requestId);
        if (request != null) {
            viewModel.processRequest(request);
            showMessage("Solicitarea a fost procesată și actualizată.");
        } else {
            showError("Solicitarea nu există.");
        }
    }

    private void handleViewClients() {
        List<Client> clients = viewModel.getAllClients();
        showClients(clients);
    }

    private void handleSearchRequestById() {
        int id = getRequestIdForSearch();
        Solicitare request = viewModel.findRequestById(id);
        if (request != null) {
            showRequests(List.of(request));
        } else {
            showError("Solicitarea nu a fost găsită.");
        }
    }

    private void handleSearchRequestByClientName() {
        String clientName = getClientNameForSearch();
        List<Solicitare> requests = viewModel.findRequestsByClientName(clientName);
        if (requests.isEmpty()) {
            showError("Nu au fost găsite solicitări pentru clientul " + clientName + ".");
        } else {
            showRequests(requests);
        }
    }

    private Solicitare getNewRequestData() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n===== Adaugă Solicitare =====");
        System.out.print("ID Client: ");
        int clientId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Descriere: ");
        String description = scanner.nextLine();
        System.out.print("Adresă: ");
        String address = scanner.nextLine();

        return new Solicitare(clientId, description, address);
    }

    private int getRequestIdForUpdate() {
        System.out.print("\nIntrodu ID-ul solicitării pentru procesare: ");
        return new Scanner(System.in).nextInt();
    }

    private int getRequestIdForSearch() {
        System.out.print("\nIntrodu ID-ul solicitării pentru căutare: ");
        return new Scanner(System.in).nextInt();
    }

    private String getClientNameForSearch() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nIntrodu numele clientului pentru căutare: ");
        return scanner.nextLine();
    }

    public void showRequests(List<Solicitare> requests) {
        System.out.println("\n===== Lista Solicitărilor =====");
        if (requests.isEmpty()) {
            System.out.println("Nu există solicitări.");
        } else {
            for (Solicitare request : requests) {
                System.out.println(request);
            }
        }
    }

    public void showClients(List<Client> clients) {
        System.out.println("\n===== Lista Clienților =====");
        if (clients.isEmpty()) {
            System.out.println("Nu există clienți.");
        } else {
            for (Client client : clients) {
                System.out.println(client);
            }
        }
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String error) {
        System.out.println("Eroare: " + error);
    }
}
