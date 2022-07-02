package edu.fra.uas.net.utill;

import edu.fra.uas.net.model.Group;
import edu.fra.uas.net.model.Message;
import edu.fra.uas.net.model.User;

/**
 * Observer to link GUI with ChatServer
 */
public class Observer {

    /**
     * to add client in server
     *
     * @param user {@link User}
     */
    public void addClient(User user) {
    }

    /**
     * to delete client from server
     *
     * @param username name of client
     */
    public void deleteClient(String username) {
    }

    /**
     * to add group in server
     *
     * @param group {@link Group}
     */
    public void addGroup(Group group) {
    }

    /**
     * to delete group in server
     *
     * @param group {@link Group}
     */
    public void deleteGroup(String group) {
    }

    /**
     * to get message as {@link String} from backend
     *
     * @param msg a message
     */
    public void updateClientMessage(String msg) {
    }

    /**
     * to get message as {@link Message} to GUI
     *
     * @param message message
     */
    public void updateClientMessage(Message message) {
    }

    /**
     * to get a list of username to GUI
     *
     * @param usernames names of clients
     */
    public void updateClientUsernames(String[] usernames) {
    }
}
