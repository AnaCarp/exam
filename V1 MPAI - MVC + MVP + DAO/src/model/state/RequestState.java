package model.state;


public interface RequestState {
    void process(Solicitare request);
    void notifyClient(Solicitare request);

}
