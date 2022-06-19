package edu.fra.uas.net.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class presents a group of clients
 *
 * @author M.Dawoud
 */
public class Group {
    private String name;
    private List<User> users = new ArrayList<>();

    /**
     * default constructor
     *
     * @param name The name of Group
     */
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

}
