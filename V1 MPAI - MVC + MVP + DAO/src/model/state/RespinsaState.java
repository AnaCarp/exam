package model.state;
public class RespinsaState implements RequestState {
    @Override
    public void process(Solicitare request) {
        System.out.println("Solicitarea a fost respinsă.");
    }

    @Override
    public void notifyClient(Solicitare request) {
        request.notifyObservers("Solicitarea ta a fost respinsă. Te așteptăm la clinică.");
    }

}
