package edu.fra.uas.net.chat;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import edu.fra.uas.net.Client;
import edu.fra.uas.net.model.Message;
import edu.fra.uas.net.model.User;
import edu.fra.uas.net.utill.Log;
import edu.fra.uas.net.utill.Parser;

/**
 * To start a client
 *
 * @author kalnaasan
 */
public class ChatClient extends Client {

    private User user;
    private static final Log LOG = new Log();

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
            Message message = Parser.convertBytesToMessage(this.received());
            LOG.info(new String(message.getContent()));
        } catch (IOException e) {
            e.printStackTrace();
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
     *
     * @return byte[]
     */
    public Message deregister() throws IOException {
        this.sendRequest(Parser.createByteArray(Parser.DEREGISTER, user.getUsername()));
        return Parser.convertBytesToMessage(this.received());
    }

}
