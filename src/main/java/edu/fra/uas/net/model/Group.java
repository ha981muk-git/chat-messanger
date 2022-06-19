package edu.fra.uas.net.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author M.Dawoud
 *
 */
public class Group {
	private String name;
	private List<User> users = new ArrayList<>();
	private boolean toDelete = false;


	public Group(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public boolean isToDelete() {
		return toDelete;
	}

	public void setToDelete(boolean toDelete) {
		this.toDelete = toDelete;
	}
	

}
