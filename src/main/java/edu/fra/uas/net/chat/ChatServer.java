package edu.fra.uas.net.chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.fra.uas.net.AbstractServer;
import edu.fra.uas.net.model.Message;
import edu.fra.uas.net.model.User;
import edu.fra.uas.net.utill.Log;
import edu.fra.uas.net.utill.Observable;
import edu.fra.uas.net.utill.Observer;
import edu.fra.uas.net.utill.Parser;

/**
 * This class presents a Socket server
 *
 * @author kalnaasan
 */
public class ChatServer extends AbstractServer {

    private final Log log = new Log();
    private List<User> users = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();
    private Observable observable = new Observable();

    /**
     * default constructor
     *
     * @param bindAddress ip address of server
     * @param port        port of server
     */
    public ChatServer(String bindAddress, int port) throws IOException {
        super(bindAddress, port);
    }

    /**
     * Receives a request. When this method returns, the {@code receivedData}
     * parameter is filled with the data received. The received request also
     * contains the sender's IP address {@code srcAddress}, and the port number
     * {@code srcPort} on the sender's machine.
     * <p>
     * This method is called when ever a request is received. If the received
     * request message is longer than 1500 bytes the message is truncated.
     * <p>
     * If there is a security manager, a packet cannot be received if the security
     * manager's {@code checkAccept} method does not allow it.
     *
     * @param receivedData the {@code byte[]} into which to place the incoming data.
     * @param srcAddress   contains the sender's IP address
     * @param srcPort      contains the sender's port number
     */
    @Override
    public void onReceive(byte[] receivedData, String srcAddress, int srcPort) {
        try {
            int opcode = Parser.detectType(receivedData);
            switch (opcode) {
                case Parser.REGISTER:
                    this.registerClient(receivedData, srcAddress, srcPort);
                    break;
                case Parser.DEREGISTER:
                    this.deregisterClient(receivedData, srcAddress, srcPort);
                    break;
                case Parser.SEARCH:
                    this.sendListOfUsernames(receivedData, srcAddress, srcPort);
                    break;
                case Parser.POLLER:
                    this.sendMessages(receivedData, srcAddress, srcPort);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
     * to register a client
     *
     * @param receivedData byte[]
     * @param srcAddress   ip address of sender
     * @param srcPort      port of sender
     * @throws IOException Signals that an I/O exception to some sort has occurred.
     *                     This class is the general class of exceptions produced by
     *                     failed or interrupted I/O operations.
     */
    private void registerClient(byte[] receivedData, String srcAddress, int srcPort) throws IOException {
        User client = Parser.convertBytesToUser(receivedData);
        users.add(client);
        observable.fireUpdateAddClient(client);
        log.info("REGISTER: " + srcAddress + ":" + srcPort + " // " + client.getUsername());
        String sender = "server";
        String receiver = client.getUsername();
        String msg = "You are connected to server!";
        Message message = new Message(sender, receiver, Parser.MESSAGE_TYPE_REGISTER, msg.getBytes());
        messages.add(message);
}

    /**
     * to deregister a client
     *
     * @param receivedData byte[]
     * @param srcAddress   ip address of sender
     * @param srcPort      port of sender
     * @throws IOException Signals that an I/O exception to some sort has occurred.
     *                     This class is the general class of exceptions produced by
     *                     failed or interrupted I/O operations.
     */
    private void deregisterClient(byte[] receivedData, String srcAddress, int srcPort) throws IOException {
        String clientUsername = Parser.getSenderFromBytes(receivedData);
        log.info("DEREGISTER: " + srcAddress + ":" + srcPort + " // " + clientUsername);
        users.remove(this.getUser(clientUsername));
        observable.fireUpdateDeleteClient(clientUsername);
        String sender = "server";
        String msg = "You are disconnected to server!";
        Message message = new Message(sender, clientUsername, Parser.MESSAGE_TYPE_DEREGISTER, msg.getBytes());
        messages.add(message);
    }

    /**
     * to send a list of usernames to target client
     *
     * @param receivedData byte[]
     * @param srcAddress   ip address of sender
     * @param srcPort      port of sender
     * @throws IOException Signals that an I/O exception to some sort has occurred.
     *                     This class is the general class of exceptions produced by
     *                     failed or interrupted I/O operations.
     */
    private void sendListOfUsernames(byte[] receivedData, String srcAddress, int srcPort) throws IOException {
        String[] usernames = new String[users.size()];
        for (int i = 0; i < users.size(); i++) {
            usernames[i] = users.get(i).getUsername();
        }
        String sender = "server";
        String receiver = Parser.getSenderFromBytes(receivedData);
        byte[] content = Arrays.toString(usernames).getBytes();
        Message message = new Message(sender, receiver, Parser.MESSAGE_TYPE_SEARCH, content);
        messages.add(message);
}

    /**
     * to send messages to target client
     *
     * @param receivedData byte[]
     * @param srcAddress   ip address of sender
     * @param srcPort      port of sender
     * @throws IOException Signals that an I/O exception to some sort has occurred.
     *                     This class is the general class of exceptions produced by
     *                     failed or interrupted I/O operations.
     */
    private void sendMessages(byte[] data, String srcAddress, int srcPort) throws IOException, InterruptedException {
        String targetClient = Parser.getSenderFromBytes(data);
        for (int i = 0; i < messages.size();i++) {
            Message message = messages.get(i);
            if (message.getReceiver().equals(targetClient)) {
                this.sendResponse(Parser.createByteArray(message), srcAddress, srcPort);
                messages.remove(message);
                break;
            }
        }
    }

    /**
     * to get client by his username
     *
     * @param sender a source-client
     * @return User {@link User}
     */
    private User getUser(String sender) {
        for (User user : users) {
            if (sender.equals(user.getUsername())) {
                return user;
            }
        }
        return null;
    }

    public void deleteUser(int index){
        if(index < users.size()){
            users.remove(index);
        }
    }
}
