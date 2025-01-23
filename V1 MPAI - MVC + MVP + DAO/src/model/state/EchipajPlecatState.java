package model.state;



public class EchipajPlecatState implements RequestState {
    @Override
    public void process(Solicitare request) {
        System.out.println("Echipaj plecat catre locație.");
    }

    @Override
    public void notifyClient(Solicitare request) {
        request.notifyObservers("Echipaj plecat catre locație.");
    }
}
