package edu.fra.uas.net.chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.fra.uas.net.AbstractServer;
import edu.fra.uas.net.model.Group;
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
    private final List<User> users = new ArrayList<>();
    private final List<Group> groups = new ArrayList<>();
    private final Map<String, List<byte[]>> messages = new HashMap<>();
    private final Observable observable = new Observable();

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
                    this.sendListOfUsernames(receivedData);
                    break;
                case Parser.POLLER:
                    this.sendMessages(receivedData, srcAddress, srcPort);
                    break;
                case Parser.MESSAGE:
                    this.addMessageToDatabase(receivedData);
                    break;
                case Parser.GROUP_CREATE:
                    this.createGroup(receivedData);
                    break;
                case Parser.GROUP_JOIN:
                    this.joinGroup(receivedData);
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * to add byte[] into HashMap{Sender => List:byte[] }
     *
     * @param data byte[]
     */
    private void addMessageToDatabase(byte[] data) {
        String receiver = Parser.getReceiverFromBytes(data);
        if (!messages.containsKey(receiver)) {
            messages.put(receiver, null);
        }

        List<byte[]> receiverMessages = messages.get(receiver);

        if (receiverMessages == null) {
            receiverMessages = new ArrayList<>();
        }

        receiverMessages.add(data);
        messages.put(receiver, receiverMessages);
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
     */
    private void registerClient(byte[] receivedData, String srcAddress, int srcPort) {
        User client = Parser.convertBytesToUser(receivedData);
        users.add(client);
        observable.fireUpdateAddClient(client);
        log.info("REGISTER: " + srcAddress + ":" + srcPort + " // " + client.getUsername());
        String sender = "server";
        String receiver = client.getUsername();
        String msg = "You are connected to server!";
        this.addMessageToDatabase(
                Parser.createByteArray(new Message(sender, receiver, Parser.MESSAGE_TYPE_REGISTER, msg.getBytes())));
    }

    /**
     * to deregister a client
     *
     * @param receivedData byte[]
     * @param srcAddress   ip address of sender
     * @param srcPort      port of sender
     */
    private void deregisterClient(byte[] receivedData, String srcAddress, int srcPort) {
        String clientUsername = Parser.getSenderFromBytes(receivedData);
        log.info("DEREGISTER: " + srcAddress + ":" + srcPort + " // " + clientUsername);
        users.remove(this.getUser(clientUsername));
        observable.fireUpdateDeleteClient(clientUsername);
        String sender = "server";
        String msg = "You are disconnected to server!";
        this.addMessageToDatabase(Parser.createByteArray(
                new Message(sender, clientUsername, Parser.MESSAGE_TYPE_DEREGISTER, msg.getBytes())));
    }

    /**
     * to send a list of usernames to target client
     *
     * @param receivedData byte[]
     */
    private void sendListOfUsernames(byte[] receivedData) {
        String sender = "server";
        String receiver = Parser.getSenderFromBytes(receivedData);

        int length = users.size() - 1;
        if (!groups.isEmpty()) {
            length += groups.size();
        }
        int counter = 0;
        if (length != 0) {
            String[] usernames = new String[length];
            for (User user : users) {
                if (!user.getUsername().equals(receiver)) {
                    usernames[counter] = user.getUsername();
                    counter++;
                }
            }
            for (Group group : groups) {
                usernames[counter] = group.getName();
                counter++;
            }

            byte[] content = Arrays.toString(usernames).getBytes();
            this.addMessageToDatabase(
                    Parser.createByteArray(new Message(sender, receiver, Parser.MESSAGE_TYPE_SEARCH, content)));
        }
    }

    /**
     * to send messages to target client
     *
     * @param data       byte[]
     * @param srcAddress ip address of sender
     * @param srcPort    port of sender
     * @throws IOException Signals that an I/O exception to some sort has occurred.
     *                     This class is the general class of exceptions produced by
     *                     failed or interrupted I/O operations.
     */
    private void sendMessages(byte[] data, String srcAddress, int srcPort) throws IOException {
        // get target-client
        String targetClient = Parser.getSenderFromBytes(data);
        if (messages.containsKey(targetClient)) { // if client has messages
            List<byte[]> targetMessages = messages.get(targetClient); // get all messages of target-client
            if (targetMessages != null && !targetMessages.isEmpty()) {
                this.sendResponse(targetMessages.get(0), srcAddress, srcPort); // send the oldest message
                targetMessages.remove(0); // remove message
            }
        }
    }

    private void createGroup(byte[] data) {
        String sender = Parser.getSenderFromBytes(data);
        String nameGroup = Parser.getGroupNameFromBytes(data);
        Group group = new Group(nameGroup);
        group.addUser(this.getUser(sender));
        groups.add(group);
        observable.fireUpdateAddGroup(group);
    }
    
    private void joinGroup(byte[] data) {
        String sender = Parser.getSenderFromBytes(data);
        String nameGroup = Parser.getGroupNameFromBytes(data);
        Group group = this.getGroup(nameGroup);
        if (!group.getUsers().contains(this.getUser(sender))) {
            group.addUser(this.getUser(sender));
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

    /**
     * to get group by name
     *
     * @param name name of group
     * @return {@link Group}
     */
    private Group getGroup(String name) {
        for (Group group : groups) {
            if (name.equals(group.getName())) {
                return group;
            }
        }
        return null;
    }

    /**
     * to remove Client from the list
     *
     * @param index client's index
     */
    public void deleteUser(int index) {
        if (index < users.size()) {
            users.remove(index);
        }
    }
}
