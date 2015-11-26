package com.rnd.tms.data.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rnd.tms.data.enums.RecordSource;
import com.rnd.tms.data.enums.RecordStatus;


@SuppressWarnings("serial")
@MappedSuperclass
@JsonIgnoreProperties({"recordCreatedDate","recordModifiedDate","recordCreatedBy","recordModifiedBy","recordCurrentStatus"})
public class BaseEntity implements Serializable{
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseEntity other = (BaseEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Id
	@GeneratedValue
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date recordCreatedDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date recordModifiedDate;
	
	@OneToOne(fetch=FetchType.LAZY)
	private UserDetail recordCreatedBy;
	
	@OneToOne(fetch=FetchType.LAZY)
	private UserDetail recordModifiedBy;
	
	@Enumerated(EnumType.ORDINAL)
	private RecordStatus recordCurrentStatus=RecordStatus.ACTIVE;
	
	@Enumerated(EnumType.ORDINAL)
	private RecordSource recordSource=RecordSource.MANUAL;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public Date getRecordCreatedDate() {
		return recordCreatedDate;
	}
	public void setRecordCreatedDate(Date recordCreatedDate) {
		this.recordCreatedDate = recordCreatedDate;
	}
	
	public Date getRecordModifiedDate() {
		return recordModifiedDate;
	}
	public void setRecordModifiedDate(Date recordModifiedDate) {
		this.recordModifiedDate = recordModifiedDate;
	}
	
	public UserDetail getRecordCreatedBy() {
		return recordCreatedBy;
	}
	public void setRecordCreatedBy(UserDetail recordCreatedBy) {
		this.recordCreatedBy = recordCreatedBy;
	}
	
	public UserDetail getRecordModifiedBy() {
		return recordModifiedBy;
	}
	public void setRecordModifiedBy(UserDetail recordModifiedBy) {
		this.recordModifiedBy = recordModifiedBy;
	}
	
	public RecordStatus getRecordCurrentStatus() {
		return recordCurrentStatus;
	}
	public void setRecordCurrentStatus(RecordStatus recordCurrentStatus) {
		this.recordCurrentStatus = recordCurrentStatus;
	}
	
	
}
