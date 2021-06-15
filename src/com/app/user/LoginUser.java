package com.app.user;


public class LoginUser {
	private String email;
	private String password;

	public LoginUser(String email, String password) {
		this.email = email;
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)// if the two objects hav same ref, ie the objs are same
			return true;
		if (!(obj instanceof LoginUser))// instanceof checking
			return false;
		LoginUser other = (LoginUser) obj;// downcasting
		if (other.email == null) {
			return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LoginUser [email=" + email + "]";
	}

	public boolean validateLogin(LoginUser other) {
		if (other.password == null)
			return false;
		return password.equals(other.password);
	}
}
