package com.src.sim.metaioapplication.logic.resource;

public class Aim {

	private Marker marker;
	private LocationObject lObject;
	private Direction direction;

	public Aim(Marker marker, LocationObject lObject) {
		this.marker = marker;
		this.direction = marker.getDirectionToLocationObject(lObject);
		this.lObject = lObject;
	}

    public Aim(){}

	public Marker getMarker() {
		return marker;
	}

	public void setMarker(Marker marker) {
		this.marker = marker;
	}

	public LocationObject getlObject() {
		return lObject;
	}

	public int getDistance() {
		return direction.getDistanceToLocationObject(lObject);
	}

	public void setlObject(LocationObject lObject) {
		this.lObject = lObject;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

    public boolean hasMarker(){
        return marker != null;
    }
}
