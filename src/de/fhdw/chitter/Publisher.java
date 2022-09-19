package de.fhdw.chitter;

import de.fhdw.chitter.exceptions.ResortDoesNotExistException;

public interface Publisher {

    void subscribe(Receiver receiver, String resort) throws ResortDoesNotExistException;
    void unsubscribe(Receiver receiver, String resort) throws ResortDoesNotExistException;
    void notifyObserver(Newsmessage msg);
}
