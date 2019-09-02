package com.example.restspringbootexample.entitie;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
// @Table(name = "User")
public class User {
	@Id
	@Column
	private String userId;

	@Column
	private String userName;

	public User() {

	}

	public User(String userName, String userId) {
		super();
		this.userName = userName;
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
