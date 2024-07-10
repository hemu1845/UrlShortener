package com.example.Capstone_10.Entity;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class UserEntity {
    @Id
    private String mail;
    
	private String username;
    private String password;
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "UserEntity [mail=" + mail + ", username=" + username + ", password=" + password + "]";
	}
    
}

