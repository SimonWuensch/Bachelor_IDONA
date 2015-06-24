package com.src.sim.metaioapplication.logic.resource;

import com.owlike.genson.Genson;

public class LocationObject {

    public enum Kind {
        ROOM(false), TOILET(false), MEDIKIT(true), FIREDRENCHER(true), EMERGENCYEXIT(true), EXIT(true), STAIRWELL(true), EVELATOR(true);

        private boolean multipleObject;

        private Kind(boolean isMultipleObject) {
            this.multipleObject = isMultipleObject;
        }

        public boolean isMultipleObject() {
            return multipleObject;
        }
    }

    public enum ToiletKind {
        MALE, FEMAIL, HANDICAPPED
    }

	private Kind kind;
	private String description;

	public LocationObject(Kind kind, String description) {
		this.kind = kind;
		this.description = description;
	}

	public LocationObject() {

	}

    public String toJson() {
        Genson genson = new Genson();
        String jsonString = genson.serialize(this);
        return jsonString;
    }

    public static LocationObject JsonToLocationObject(String jsonString) {
        Genson genson = new Genson();
        return genson.deserialize(jsonString, LocationObject.class);
    }

	public Kind getKind() {
		return kind;
	}

	public void setKind(Kind kind) {
		this.kind = kind;
	}

	 public String getDescription() {
	 return description;
	 }

	public void setDescription(String description) {
		this.description = description;
	}

	public static LocationObject newRoom(String roomName) {
		return new LocationObject(Kind.ROOM, roomName);
	}

	public static LocationObject newToilet(ToiletKind kind) {
		return new LocationObject(Kind.TOILET, kind.toString());
	}

	public static LocationObject newMultipleLocationObject(Kind kind) {
		return new LocationObject(kind, kind.toString());
	}

	public String toString() {
		return new StringBuilder().append("[")//
				.append(kind).append(" - ")//
				.append(description).append("]").toString();
	}
}
