package architecture.mvp;


import java.util.Scanner;

public class ViewMVP implements MainView {
    private final Scanner scanner = new Scanner(System.in);

    @Override
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
        return scanner.nextInt();
    }

    @Override
    public int getClientIdForNewRequest() {
        System.out.print("\nIntrodu ID-ul clientului: ");
        return scanner.nextInt();
    }

    @Override
    public String getDescriptionForNewRequest() {
        scanner.nextLine(); // Consumă newline
        System.out.print("Introdu descrierea solicitării: ");
        return scanner.nextLine();
    }

    @Override
    public String getAddressForNewRequest() {
        System.out.print("Introdu adresa solicitării: ");
        return scanner.nextLine();
    }

    @Override
    public int getRequestIdForUpdate() {
        System.out.print("\nIntrodu ID-ul solicitării pentru procesare: ");
        return scanner.nextInt();
    }

    @Override
    public int getRequestIdForSearch() {
        System.out.print("\nIntrodu ID-ul solicitării pentru căutare: ");
        return scanner.nextInt();
    }

    @Override
    public String getClientNameForSearch() {
        scanner.nextLine(); // Consumă newline
        System.out.print("\nIntrodu numele clientului: ");
        return scanner.nextLine();
    }

    @Override
    public void displaySolicitare(int clientId, String description, String address, String state) {
        System.out.println("\n===== Solicitare =====");
        System.out.println("ID Client: " + clientId);
        System.out.println("Descriere: " + description);
        System.out.println("Adresă: " + address);
        System.out.println("Stare: " + state);
    }

    @Override
    public void displayClient(int clientId, String name, String phone) {
        System.out.println("\n===== Client =====");
        System.out.println("ID: " + clientId);
        System.out.println("Nume: " + name);
        System.out.println("Telefon: " + phone);
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void showError(String error) {
        System.out.println("Eroare: " + error);
    }
}
