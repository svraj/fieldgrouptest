package com.rnd.tms.data.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rnd.tms.data.enums.UserType;


/**
 * @author ABHI
 *
 */

@Entity
@Table(name = "user_detail")
public class UserDetail extends BaseEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2226642772089759301L;
	
	private String username;
	private String password;
	
	@Enumerated(EnumType.STRING)
	private UserType userType;
	private Integer referenceId;
	private Integer activeStatus;
	private String emailId;
	private Integer registrationId;
	
	@ManyToMany(fetch = FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinTable(name = "user_role",joinColumns={@JoinColumn(name="user_id",nullable=false,updatable=false)},
	inverseJoinColumns={@JoinColumn(name="role_id",nullable=false,updatable=false)})
	private Set<RoleDetail> roleDetails;
	
	private Integer companyId;
	
	public UserDetail()
	{
		
	}

	
	@Column(name="email_id")
	public String getEmailId() { 
		return emailId; 
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	/**
	 * @return the username
	 */
	@Column(name="user_name")
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the userType
	 */
	@Column(name="user_type")
	
	public UserType getUserType() {
		return userType;
	}

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	/**
	 * @return the referenceId
	 */
	public Integer getReferenceId() {
		return referenceId;
	}

	/**
	 * @param referenceId the referenceId to set
	 */
	public void setReferenceId(Integer referenceId) {
		this.referenceId = referenceId;
	}

	public Integer getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(Integer activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(Integer registrationId) {
		this.registrationId = registrationId;
	}
	
	
	public Set<RoleDetail> getRoleDetails() {
		return roleDetails;
	}

	public void setRoleDetails(Set<RoleDetail> roleDetails) {
		this.roleDetails = roleDetails;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}


	

}
