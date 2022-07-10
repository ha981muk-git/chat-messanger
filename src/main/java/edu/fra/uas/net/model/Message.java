package edu.fra.uas.net.model;

/**
 * This class presents sent message
 *
 * @author kalnaasan
 */
public class Message {

    private String sender;
    private String receiver;

    private String nameGroup;

    private int type;
    private byte[] content;
    /**
     * default constructor
     *
     * @param sender   from client
     * @param receiver to client
     * @param type     type of message
     * @param content  content of message
     */
    public Message(String sender, String receiver, int type, byte[] content) {
        super();
        this.sender = sender;
        this.receiver = receiver;
        this.nameGroup = "null";
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

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
