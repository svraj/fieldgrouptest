/**
 * 
 */
package com.rnd.tms.data.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author ABHI
 *
 */
@Entity
@Table(name="view_detail")
public class ViewDetail extends BaseEntity {

	private String name;
	private String url;
	private String viewClass;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="main_view_id")
	private ViewDetail mainView;
	
	@OneToMany(mappedBy="mainView")
	private Set<ViewDetail> subViews = new HashSet<ViewDetail>(0);
	
	private Integer viewOrderNumber;
	private String logoPath;
	
	public ViewDetail(){
		
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
	public Set<ViewDetail> getSubViews() {
		return subViews;
	}

	public void setSubViews(Set<ViewDetail> subViews) {
		this.subViews = subViews;
	}

	
	public ViewDetail getMainView() {
		return mainView;
	}

	public void setMainView(ViewDetail mainView) {
		this.mainView = mainView;
	}

	public String getViewClass() {
		return viewClass;
	}

	public void setViewClass(String viewClass) {
		this.viewClass = viewClass;
	}
	public Integer getViewOrderNumber() {
		return viewOrderNumber;
	}

	public void setViewOrderNumber(Integer viewOrderNumber) {
		this.viewOrderNumber = viewOrderNumber;
	}
	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	
	
}
