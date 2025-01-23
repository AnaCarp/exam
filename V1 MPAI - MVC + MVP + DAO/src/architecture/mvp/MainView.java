package architecture.mvp;

import model.state.Solicitare;

public interface MainView {
    int displayMainMenu();

    int getClientIdForNewRequest();

    String getDescriptionForNewRequest();

    String getAddressForNewRequest();

    int getRequestIdForUpdate();

    int getRequestIdForSearch();

    String getClientNameForSearch();


     void displaySolicitare(int clientId, String description, String address, String state);

    void displayClient(int clientId, String name, String phone);

    void showMessage(String message);

    void showError(String error);
}
