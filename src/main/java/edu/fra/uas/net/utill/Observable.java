package edu.fra.uas.net.utill;

import edu.fra.uas.net.model.Group;
import edu.fra.uas.net.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Observable to link GUI with ChatServer
 */
public class Observable {
    private List<Observer> observers = new ArrayList<>();

    /**
     * to attach Observer
     *
     * @param observer {@link Observer}
     */
    public void attach(Observer observer) {
        observers.add(observer);
    }

    /**
     * to detach {@link Observer}
     *
     * @param observer a Observer
     */
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    /**
     * to send update to {@link User}
     *
     * @param user a client
     */
    public void fireUpdateAddClient(User user) {
        for (Observer observer : observers) {
            observer.addClient(user);
        }
    }

    /**
     * to send update to {@link String}
     *
     * @param username name of client
     */
    public void fireUpdateDeleteClient(String username) {
        for (Observer observer : observers) {
            observer.deleteClient(username);
        }
    }

    /**
     * to send update to {@link Group}
     *
     * @param group a group of client
     */
    public void fireUpdateAddGroup(Group group) {
        for (Observer observer : observers) {
            observer.addGroup(group);
        }
    }

    /**
     * to send update to {@link String}
     *
     * @param group name of group
     */
    public void fireUpdateDeleteGroup(String group) {
        for (Observer observer : observers) {
            observer.deleteGroup(group);
        }
    }

    public void clientFireUpdateMessage(String msg){
        for (Observer observer: observers){
            observer.updateClientMessage(msg);
        }
    }
    public void clientFireUpdateUsernames(String[] usernames){
        for (Observer observer: observers){
            observer.updateClientUsernames(usernames);
        }
    }


}
