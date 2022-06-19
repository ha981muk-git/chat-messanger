package edu.fra.uas.net.model;

/**
 * The UserClass presents the client
 * 
 * @author kalnaasan
 *
 */
public class User {

	private String username;
	private String host;
	private int port;
	private boolean toDelete = false;

	/**
	 * default constructor
	 * @param username username of client
	 * @param host bind address
	 * @param port bind port
	 */
	public User(String username, String host, int port) {
		super();
		this.username = username;
		this.host = host;
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isToDelete() {
		return toDelete;
	}

	public void setToDelete(boolean toDelete) {
		this.toDelete = toDelete;
	}
	

}
