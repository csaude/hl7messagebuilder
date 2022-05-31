package org.openmrs.module.hl7messagebuilder;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "disa_mapping_sites")
public class DisaSite implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(name = "location_name")
	private String locationName;
	
	@Column(name = "location_id")
	private Integer locationId;
	
	@Column(name = "description")
	private String locationDescription;
	
	public String getLocationName() {
		return locationName;
	}
	
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	public Integer getLocationId() {
		return locationId;
	}
	
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}
	
	public String getLocationDescription() {
		return locationDescription;
	}
	
	public void setLocationDescription(String locationDescription) {
		this.locationDescription = locationDescription;
	}
}
