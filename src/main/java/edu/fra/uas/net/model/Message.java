package edu.fra.uas.net.model;

/**
 * This class presents sent message
 *
 * @author kalnaasan
 */
public class Message {

    private static final long serialVersionUID = -2413682340604592350L;
    private String sender;
    private String receiver;
    private String type;
    private byte[] content;

    /**
     * default constructor
     *
     * @param sender   from client
     * @param receiver to client
     * @param type     type of message
     * @param content  content of message
     */
    public Message(String sender, String receiver, String type, byte[] content) {
        super();
        this.sender = sender;
        this.receiver = receiver;
        this.type = type;
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
