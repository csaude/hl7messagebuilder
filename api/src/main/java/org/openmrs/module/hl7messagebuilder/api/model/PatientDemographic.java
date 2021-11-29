package org.openmrs.module.hl7messagebuilder.api.model;

import java.io.Serializable;

/**
 * @author machabane
 */
@SuppressWarnings("serial")
public class PatientDemographic implements Serializable {
	
	private String pid;
	
	private String gender;
	
	private String birthDate;
	
	private String givenName;
	
	private String middleName;
	
	private String familyName;
	
	private String address;
	
	private String stateProvince;
	
	private String country;
	
	private String countryDistrict;
	
	private String telefone1;
	
	private String telefone2;
	
	private String maritalStatus;
	
	private String lastConsultation;
	
	private Integer personId;
	
	public String getPid() {
		return pid;
	}
	
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	
	public String getGivenName() {
		return givenName;
	}
	
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	
	public String getMiddleName() {
		return middleName;
	}
	
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	public String getFamilyName() {
		return familyName;
	}
	
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getStateProvince() {
		return stateProvince;
	}
	
	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getCountryDistrict() {
		return countryDistrict;
	}
	
	public void setCountryDistrict(String countryDistrict) {
		this.countryDistrict = countryDistrict;
	}
	
	public String getTelefone1() {
		return telefone1;
	}
	
	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}
	
	public String getTelefone2() {
		return telefone2;
	}
	
	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}
	
	public String getMaritalStatus() {
		return maritalStatus;
	}
	
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	
	public String getLastConsultation() {
		return lastConsultation;
	}
	
	public void setLastConsultation(String lastConsultation) {
		this.lastConsultation = lastConsultation;
	}
	
	public Integer getPersonId() {
		return personId;
	}
	
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}
}
