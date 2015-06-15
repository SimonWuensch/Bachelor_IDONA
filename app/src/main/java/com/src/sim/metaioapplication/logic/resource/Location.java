package com.src.sim.metaioapplication.logic.resource;

import com.owlike.genson.Genson;
import com.owlike.genson.annotation.JsonIgnore;
import com.src.sim.metaioapplication.logic.History;

public class Location {

	@JsonIgnore private long id;
	private String name;
	private String street;
	private String number;
	private String zip;
	private History history;

	public Location() {
	}

	public Location(String name, String street, String number, String zip) {
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

	public String toJsonLocationOnly() {
		Genson genson = new Genson();
		LocationOnly only = LocationOnly.JsonToLocationOnly(genson.serialize(this));
        return only.toJson();
	}

	public static Location JsonToLocation(String jsonString) {
		Genson genson = new Genson();
		return genson.deserialize(jsonString, Location.class);
	}

	@JsonIgnore
	public long getId() {
		return id;
	}

	@JsonIgnore
	public void setId(long id) {
		this.id = id;
        history.setId(id);
	}

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
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
