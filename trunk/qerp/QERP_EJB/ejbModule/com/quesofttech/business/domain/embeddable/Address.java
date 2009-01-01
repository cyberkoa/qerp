package com.quesofttech.business.domain.embeddable;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
@SuppressWarnings("serial")
public class Address implements Serializable {

	
	@Column(length = 10)
	private String buildingLot;
	
	@Column(length = 100)
	private String street;
		
	@Column(length = 10)
	private String postalCode;
	
	@Column(length = 30)
	private String city;
	
	@Column(length = 30)
	private String area;

	@Column(length = 30)
	private String country;
	
	@Column(length = 30)
	private String state;

	/**
	 * @return the buildingLot
	 */
	
	public String getBuildingLot() {
		return buildingLot;
	}

	/**
	 * @param buildingLot the buildingLot to set
	 */
	public void setBuildingLot(String buildingLot) {
		this.buildingLot = buildingLot;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	


	
	
}
