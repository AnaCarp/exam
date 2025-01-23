package architecture.mvvm;

import model.Client;
import model.state.Solicitare;
import java.util.ArrayList;
import java.util.List;

public class ViewModel {

    public List<Solicitare> getAllRequests() {
        List<Solicitare> requests = new ArrayList<>();
        for (int i = 1; ; i++) {
            Solicitare solicitare = Solicitare.find(i);
            if (solicitare == null) break;
            requests.add(solicitare);
        }
        return requests;
    }

    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        for (int i = 1; ; i++) {
            Client client = Client.find(i);
            if (client == null) break;
            clients.add(client);
        }
        return clients;
    }

    public Solicitare findRequestById(int id) {
        return Solicitare.find(id);
    }

    public List<Solicitare> findRequestsByClientName(String clientName) {
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
        return matchingRequests;
    }

    public void addRequest(Solicitare solicitare) {
        solicitare.save();
    }

    public void processRequest(Solicitare request) {
        request.process(); // Process the current state
        request.update();  // Save the updated state
    }
}
