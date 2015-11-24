/**
 * 
 */
package com.rnd.tms.data.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author ABHI
 *
 */
@Entity
@Table(name="permission_detail")
public class PermissionDetail extends BaseEntity {

	private String name;
	private String permissionText;
	
	@JoinColumn(name="view_id")
	@ManyToOne(fetch=FetchType.EAGER)
	private ViewDetail viewDetail;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "permissionDetails")
	private Set<RoleDetail> roleDetails;
	private Integer companyId;
	public PermissionDetail(){
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public ViewDetail getViewDetail() {
		return viewDetail;
	}

	public void setViewDetail(ViewDetail viewDetail) {
		this.viewDetail = viewDetail;
	}


	public Set<RoleDetail> getRoleDetails() {
		return roleDetails;
	}

	public void setRoleDetails(Set<RoleDetail> roleDetails) {
		this.roleDetails = roleDetails;
	}

	public String getPermissionText() {
		return permissionText;
	}

	public void setPermissionText(String permissionText) {
		this.permissionText = permissionText;
	}
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
}
