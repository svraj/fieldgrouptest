/**
 * 
 */
package com.rnd.tms.data.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author ABHI
 *
 */
@Entity
@Table(name="role_detail")
public class RoleDetail extends BaseEntity {

	private String name;
	private String description;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "role_permission", joinColumns = {@JoinColumn(name="role_id",nullable=false,updatable=false)},
	inverseJoinColumns={@JoinColumn(name="permission_id",nullable=false,updatable=false)})
	private Set<PermissionDetail> permissionDetails = new HashSet<PermissionDetail>(0);
	
	@ManyToMany(fetch=FetchType.LAZY,mappedBy="roleDetails")
	private Set<UserDetail> userDetails = new HashSet<UserDetail>(0);
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "role_view", joinColumns = {@JoinColumn(name="role_id",nullable=false,updatable=false)},
	inverseJoinColumns={@JoinColumn(name="view_id",nullable=false,updatable=false)})
	private Set<ViewDetail> viewDetails = new HashSet<ViewDetail>(0);

	private Integer companyId;
	

	public RoleDetail(){
		
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public Set<PermissionDetail> getPermissionDetails() {
		return permissionDetails;
	}

	public void setPermissionDetails(Set<PermissionDetail> permissionDetails) {
		this.permissionDetails = permissionDetails;
	}
	
	public Set<UserDetail> getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(Set<UserDetail> userDetails) {
		this.userDetails = userDetails;
	}
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
	public Set<ViewDetail> getViewDetails() {
		return viewDetails;
	}

	public void setViewDetails(Set<ViewDetail> viewDetails) {
		this.viewDetails = viewDetails;
	}
	
}
