package model.state;

public class AcceptataState implements RequestState {
    @Override
    public void process(Solicitare request) {
        System.out.println("Solicitarea a fost acceptată. Echipajul este în drum.");
        request.setState(new EchipajPlecatState());
        request.save();
    }

    @Override
    public void notifyClient(Solicitare request) {
        request.notifyObservers("Solicitarea ta a fost acceptată. Echipajul este în drum.");
    }
}
