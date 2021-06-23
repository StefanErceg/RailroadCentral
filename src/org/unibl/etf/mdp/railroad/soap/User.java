package org.unibl.etf.mdp.railroad.soap;

import java.util.ArrayList;

import org.unibl.etf.mdp.railroad.data.Users;

public class User {
	
	public boolean createUser(org.unibl.etf.mdp.railroad.model.User user) {
		if (user == null) return false;
		return Users.createUser(user);
	}
	
	public boolean deactivate(String username) {
		return Users.deactivate(username);
	}

	public boolean usernameExists(String username) {
		if (username == null) return false;
		return Users.usernameExists(username);
	}
	
	public org.unibl.etf.mdp.railroad.model.User[] getUsers() {
		ArrayList<org.unibl.etf.mdp.railroad.model.User> users = Users.getUsers();
		return Users.getUsers().toArray(new org.unibl.etf.mdp.railroad.model.User[users.size()]);
	}

}
 