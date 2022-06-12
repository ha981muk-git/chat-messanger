package edu.fra.uas.net.model;

import java.io.Serializable;

import edu.fra.uas.net.message.Data;

/**
 * 
 * @author kalnaasan
 *
 */
public class Message implements Serializable {

	private static final long serialVersionUID = -2413682340604592350L;
	private String sender;
	private String receiver;
	private String type;
	private Data content;

	public Message(String sender, String receiver, String type, Data content) {
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

	public Data getContent() {
		return content;
	}

	public void setContent(Data content) {
		this.content = content;
	}
}
