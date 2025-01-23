package model.state;


public class InAnalizaState implements RequestState {
    @Override
    public void process(Solicitare request) {
        System.out.println("Solicitarea este în analiză.");
        request.setState(new AcceptataState());
        System.out.println(request.getState());
        request.update();
    }

    @Override
    public void notifyClient(Solicitare request) {
        request.notifyObservers("Solicitarea ta este în analiză.");
    }
}
