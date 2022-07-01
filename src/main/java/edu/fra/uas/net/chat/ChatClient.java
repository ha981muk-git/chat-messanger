package edu.fra.uas.net.chat;

import java.io.IOException;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import edu.fra.uas.net.Client;
import edu.fra.uas.net.model.Message;
import edu.fra.uas.net.model.User;
import edu.fra.uas.net.utill.Log;
import edu.fra.uas.net.utill.Observable;
import edu.fra.uas.net.utill.Observer;
import edu.fra.uas.net.utill.Parser;

/**
 * To start a client
 *
 * @author kalnaasan
 */
public class ChatClient extends Client {

    private User user;
    private static final Log LOG = new Log();
    private List<Message> messages = new ArrayList<>();
    private String[] clients;
    private MessagePuller messagePuller;
    private Observable observable = new Observable();

    /**
     * default constructor
     *
     * @param serverAddress IP-Address of server
     * @param serverPort    Port  of server
     * @param bindAddress   IP-Address of client
     * @param port          Port of client
     * @param username      name of client
     * @throws SocketException      Thrown to indicate that there is an error creating or accessing a Socket.
     * @throws UnknownHostException Thrown to indicate that the IP address of a host could not be determined
     */
    public ChatClient(String serverAddress, int serverPort, String bindAddress, int port, String username)
            throws SocketException, UnknownHostException {
        super(serverAddress, serverPort, bindAddress, port);

        user = new User(username, bindAddress, port);

        try {
            this.sendRequest(Parser.createByteArray(username, bindAddress, port));
        } catch (IOException e) {
            e.printStackTrace();
        }
        messagePuller = new MessagePuller();
        this.startListeningToMessage();
    }

    /**
     * A request is sent to server to send the list of clients back to client
     *
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     *                     This class is the general class of exceptions produced
     *                     by failed or interrupted I/O operations.
     */
    public void readClients() throws IOException {
        this.sendRequest(Parser.createByteArray(Parser.SEARCH, user.getUsername()));
    }

    private class MessagePuller extends Thread {
        private boolean active = true;
        private static final int TIME_SLEEP = 1000;
        private final byte[] puller = Parser.createByteArray(Parser.POLLER, user.getUsername());

        @Override
        public void run() {
            while (this.active) {
                // send Message to ask the server for newly received messages
                try {
                    sendRequest(this.puller);
                    Thread.sleep(MessagePuller.TIME_SLEEP);
                    byte[] data = receivedResponse();
                    Message message = Parser.convertBytesToMessage(data);
                    addMessage(message);
                } catch (IOException | InterruptedException e) {
//                    e.printStackTrace();
//                    this.active = false;
//                    LOG.info("receiving ...");
                }
            }
        }

    }

    /**
     * to start client to receive messages
     */
    private void startListeningToMessage() {
        this.messagePuller.start();
    }

    /**
     * to stop client to receive messages
     */
    public void stopListeningToMessage() {
        messagePuller.stop();
    }

    private void addMessage(Message message) {
        messages.add(message);
        switch (message.getType()) {
            case Parser.MESSAGE_TYPE_REGISTER:
                LOG.info(new String(message.getContent()));
                observable.clientFireUpdateMessage(new String(message.getContent()));
                break;
            case Parser.MESSAGE_TYPE_DEREGISTER:
                LOG.info(new String(message.getContent()));
                observable.clientFireUpdateMessage(new String(message.getContent()));
                this.stopListeningToMessage();
                this.stopAndClose();
                break;
            case Parser.MESSAGE_TYPE_SEARCH:
                String tmpClients = new String(message.getContent());
                tmpClients = tmpClients.substring(1, tmpClients.length() - 1);
                clients = tmpClients.split(", ");
                observable.clientFireUpdateUsernames(clients);
                break;
            case Parser.MESSAGE_TYPE_MESSAGE:
                observable.clientFireUpdateMessage(message);
                break;
            case Parser.MESSAGE_TYPE_FILE:
                break;
            default:
                break;
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * to receive response from server
     *
     * @return byte[]
     */
    private byte[] received() {
        try {
            return this.receivedResponse();
        } catch (IOException e) {
            this.received();
        }
        return new byte[0];
    }

    /**
     * to deregister a client
     */
    public void deregister() throws IOException {
        this.sendRequest(Parser.createByteArray(Parser.DEREGISTER, user.getUsername()));
    }

    public String[] getClients() {
        return clients;
    }

    /**
     * to attach Observer
     *
     * @param observer {@link Observer}
     */
    public void attach(Observer observer) {
        observable.attach(observer);
    }

    /**
     * to detach {@link Observer}
     *
     * @param observer a Observer
     */
    public void detach(Observer observer) {
        observable.detach(observer);
    }

    /**
     * to send a message to Server
     *
     * @param message {@link Message}
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     *                     This class is the general class of exceptions produced
     *                     by failed or interrupted I/O operations.
     */
    public void sendMessage(Message message) throws IOException {
        this.sendRequest(Parser.createByteArray(message));
    }
}
