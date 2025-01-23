package model.state;

import model.DatabaseManager;
import model.observer.Observer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Solicitare {
    private int id;
    private int clientId;
    private String description;
    private String address;
    private RequestState state;
    private List<Observer> observers = new ArrayList<>();

    public Solicitare(int clientId, String description, String address) {
        this.clientId = clientId;
        this.description = description;
        this.address = address;
        this.state = new InAnalizaState(); // Setare implicită doar pentru solicitările noi
    }

    public void setState(RequestState state) {
        this.state = state;
    }

    public void process() {
        state.process(this);
    }

    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public int getId() {
        return id;
    }

    public int getClientId() {
        return clientId;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public RequestState getState() {
        return state;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Observer> getObservers() {
        return observers;
    }

    public void setObservers(List<Observer> observers) {
        this.observers = observers;
    }

    @Override
    public String toString() {
        return "Solicitare{id=" + id + ", clientId=" + clientId +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", state=" + state.getClass().getSimpleName() + '}';
    }


}
