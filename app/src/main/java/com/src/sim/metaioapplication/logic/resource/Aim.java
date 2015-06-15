package com.src.sim.metaioapplication.logic.resource;

public class Aim {

	private Tracker tracker;
	private LocationObject lObject;
	private Direction direction;

	public Aim(Tracker tracker, LocationObject lObject) {
		this.tracker = tracker;
		this.direction = tracker.getDirectionToLocationObject(lObject);
		this.lObject = lObject;
	}

	public Tracker getTracker() {
		return tracker;
	}

	public void setTracker(Tracker tracker) {
		this.tracker = tracker;
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
}
