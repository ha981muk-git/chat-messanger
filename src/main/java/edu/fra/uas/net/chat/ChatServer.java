package edu.fra.uas.net.chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.fra.uas.net.AbstractServer;
import edu.fra.uas.net.model.Message;
import edu.fra.uas.net.model.User;
import edu.fra.uas.net.utill.Log;
import edu.fra.uas.net.utill.Parser;

/**
 * This class presents a Socket server
 *
 * @author kalnaasan
 */
public class ChatServer extends AbstractServer {

    private final Log log = new Log();
    private List<User> users = new ArrayList<>();

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
                    User deletedUser = Parser.convertBytesToUser(receivedData);
                    users.remove(deletedUser);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        log.info("REGISTER: " + srcAddress + ":" + srcPort + " // " + client.getUsername());
        String sender = "server";
        String receiver = client.getUsername();
        String type = "REGISTERED";
        String msg = "You are connected to server!";
        Message message = new Message(sender, receiver, type, msg.getBytes());
        this.sendResponse(Parser.createByteArray(message), client.getHost(), client.getPort());
    }

    /**
     * to deregister a client
     *
     * @param receivedData byte[]
     * @param srcAddress   ip address of sender
     * @param srcPort      port of sender
     */
    private void deregisterClient(byte[] receivedData, String srcAddress, int srcPort) {
        String sender = Parser.getSenderFromBytes(receivedData);
        User user = this.getUser(sender);
        log.info("REGISTER: " + srcAddress + ":" + srcPort + " // " + sender);
        users.remove(user);

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
}
