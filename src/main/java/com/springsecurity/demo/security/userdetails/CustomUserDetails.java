package com.springsecurity.demo.security.userdetails;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.springsecurity.demo.constants.AuthenticationType;

/**
 * Customized UserDetails with project specific fields included
 * @author Brooke
 * @since JDK1.8
 * @created on 2018/06/16
 */
public class CustomUserDetails extends org.springframework.security.core.userdetails.User {

	private AuthenticationType authenticationType;

	private Integer accountId;

	private String firstName;

	private String lastName;

	private String email;

	private String cellphoneNo;

	private Byte status;

	public AuthenticationType getAuthenticationType() {
		return authenticationType;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getCellphoneNo() {
		return cellphoneNo;
	}

	public Byte getStatus() {
		return status;
	}

	public CustomUserDetails(AuthenticationType authenticationType, Integer accountId, String firstName,
			String lastName, String email, String cellphoneNo, Byte status, String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.authenticationType = authenticationType;
		this.accountId = accountId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.cellphoneNo = cellphoneNo;
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CustomUserDetails [authenticationType=");
		builder.append(authenticationType);
		builder.append(", accountId=");
		builder.append(accountId);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", email=");
		builder.append(email);
		builder.append(", cellphoneNo=");
		builder.append(cellphoneNo);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}

}
