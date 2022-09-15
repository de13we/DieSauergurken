package de.fhdw.chitter;

public interface Publisher {

    void subscribe(Receiver receiver, String resort);
    void unsubscribe(Receiver receiver, String resort);
    void notifyObserver(Newsmessage msg);
}
