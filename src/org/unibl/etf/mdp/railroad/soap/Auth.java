package org.unibl.etf.mdp.railroad.soap;

import org.unibl.etf.mdp.railroad.data.Users;
import org.unibl.etf.mdp.railroad.model.User;

public class Auth {
	
	public User login(String username, String password) {
		if (username == null || password == null) return null;
		return Users.login(username, password);
	}
	
	public boolean usernameExists(String username) {
		if (username == null) return false;
		return Users.usernameExists(username);
	}
	
	
}
