package sbz.projekat.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class User {
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String username, String first_name, String last_name, String password,
			Uloga role, Date registration_date, UserAccount userAccount) {
		super();
		this.username = username;
		this.first_name = first_name;
		this.last_name = last_name;
		this.password = password;
		this.role = role;
		this.registration_date = registration_date;
		this.userAccount = userAccount;
	}

	public enum Uloga { KUPAC, CUSTOMER, MANAGER, EMPLOYEE };

	
	@Id
	private String username;
	private String first_name;
	private String last_name;
	private String password;
	private Uloga role;
	private Date registration_date;
  @DBRef
	private UserAccount userAccount;
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Uloga getRole() {
		return role;
	}

	public void setRole(Uloga role) {
		this.role = role;
	}

	public Date getRegistration_date() {
		return registration_date;
	}

	public void setRegistration_date(Date registration_date) {
		this.registration_date = registration_date;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

}
