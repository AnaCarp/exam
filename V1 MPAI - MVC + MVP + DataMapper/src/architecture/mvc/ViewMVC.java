package architecture.mvc;


import model.Client;
import model.state.Solicitare;

import java.util.List;

public class ViewMVC {

    public int displayMainMenu() {
        System.out.println("\n===== Meniu Principal =====");
        System.out.println("1. Adaugă solicitare");
        System.out.println("2. Vizualizează solicitări");
        System.out.println("3. Treci solicitare în următoarea stare");
        System.out.println("4. Vizualizează clienți");
        System.out.println("5. Caută solicitare după ID");
        System.out.println("6. Caută solicitare după numele clientului");
        System.out.println("0. Ieșire");
        System.out.print("Alege o opțiune: ");
        return new java.util.Scanner(System.in).nextInt();
    }

    public Solicitare getNewRequestData() {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
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
        return new java.util.Scanner(System.in).nextInt();
    }

    public int getRequestIdForSearch() {
        System.out.print("\nIntrodu ID-ul solicitării pentru căutare: ");
        return new java.util.Scanner(System.in).nextInt();
    }

    public String getClientNameForSearch() {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
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
