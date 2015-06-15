package com.src.sim.metaioapplication.logic.resource;

import com.owlike.genson.Genson;
import com.owlike.genson.annotation.JsonIgnore;
import com.src.sim.metaioapplication.logic.History;

public class LocationOnly {

	@JsonIgnore private long id;
	private String name;
	private String street;
	private String number;
	private String zip;
	@JsonIgnore
	private History initiator;

	public LocationOnly() {

	}

	public LocationOnly(String name, String street, String number, String zip) {
		super();
		this.name = name;
		this.street = street;
		this.number = number;
		this.zip = zip;
	}

	public String toJson() {
		Genson genson = new Genson();
		return genson.serialize(this);
	}

	public static LocationOnly JsonToLocationOnly(String jsonString) {
		Genson genson = new Genson();
		return genson.deserialize(jsonString, LocationOnly.class);
	}

	@JsonIgnore
	public long getId() {
		return id;
	}

	@JsonIgnore
	public void setId(long id) {
		this.id = id;
	}

	@JsonIgnore
	public History getInitiator() {
		return initiator;
	}
	
	@JsonIgnore
	public void setInitiator(History initiator) {
		this.initiator = initiator;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

}
