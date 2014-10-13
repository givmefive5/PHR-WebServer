package phr.models;

import java.util.Date;

public class User {

	private int id;
	private String username;
	private String password;
	private String name;
	private Date dateOfBirth;

	public User() {
		super();
	}

	public User(int id) {
		super();
		this.id = id;
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public User(String username, String password, String name, Date dateOfBirth) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
	}

	public User(int id, String username, String password, String name,
			Date dateOfBirth) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

}
