package org.unibl.etf.mdp.railroad.soap;

import org.unibl.etf.mdp.railroad.data.Users;

public class User {
	
	public boolean createUser(org.unibl.etf.mdp.railroad.model.User user) {
		if (user == null) return false;
		return Users.createUser(user);
	}
	
	public boolean deactivate(String username) {
		return Users.deactivate(username);
	}

}
