package javaSocket;

import java.util.HashMap;

public class UserLogin {
	HashMap<String, String> _credentials = new HashMap<String, String>();

	public UserLogin()
	{
		//Initialize user login database
		_credentials.put("a", "1");
		_credentials.put("b", "2");
		_credentials.put("c", "3");
		_credentials.put("d", "4");
		_credentials.put("e", "5");
		_credentials.put("f", "6");
		_credentials.put("g", "7");
		_credentials.put("h", "8");
		_credentials.put("i", "9");
		_credentials.put("j", "10");
	}

	public boolean checkLogin(String username, String password) {
		if(_credentials.containsKey(username)) {
			if(_credentials.get(username).equals(password)) {
				return true;
			}
		}
		return false;
	}
}