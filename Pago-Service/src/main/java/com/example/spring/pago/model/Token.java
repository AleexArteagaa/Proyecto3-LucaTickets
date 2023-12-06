package com.example.spring.pago.model;

public class Token {
	
	private String user;
	
	private String password;
	
	private String token;
	
	

	public Token() {
		super();
	}
	
	

	public Token(String user, String password, String token) {
		super();
		this.user = user;
		this.password = password;
		this.token = token;
	}



	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}



	@Override
	public String toString() {
		return "Token [user=" + user + ", password=" + password + ", token=" + token + "]";
	}
	
	
	

}
