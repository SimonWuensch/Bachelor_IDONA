package com.src.sim.metaioapplication.logic.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.src.sim.metaioapplication.logic.resource.LocationObject.Kind;

public class Tracker {

	private int id;
	private List<Direction> directions = new ArrayList<Direction>();

	public Tracker(int id) {
		this.id = id;
	}

	public Tracker() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Direction> getDirections() {
		return directions;
	}

	public void setDirections(List<Direction> directions) {
		this.directions = directions;
	}

	public List<Integer> getTrackers() {
		return new ArrayList<Integer>() {
			private static final long serialVersionUID = 1L;
			{
				for (Direction direction : directions) {
					if (direction.hasTracker()) {
						add(direction.getTrackerID());
					}
				}
			}
		};
	}

	public int getDistanceToTracker(Tracker tracker) {
		return getDirectionToTracker(tracker).getDistance();
	}

	public String getDirectionIconToTracker(Tracker tracker) {
		return getDirectionToTracker(tracker).getArrow().name();
	}

	public Direction getDirectionToTracker(Tracker tracker) {
		for (Direction direction : directions) {
			if (direction.hasTracker()) {
				if (direction.getTrackerID() == tracker.getId()) {
					return direction;
				}
			}
		}
		throw new NullPointerException("No correct Tracker found in this direction!");
	}

	public Direction addDirection(Direction direction) {
		directions.add(direction);
		return direction;
	}

	public Direction getDirectionToLocationObject(LocationObject lObject) {
		for (Direction direction : directions) {
			Kind kind = lObject.getKind();
			Map<Kind, List<LocationObject>> locationObjectMap = direction.getLocationObjectMap();
			if (locationObjectMap.containsKey(kind)) {
				for (LocationObject lObjectValue : locationObjectMap.get(kind)) {
					if (lObjectValue.getDescription().equals(lObject.getDescription()))
						return direction;
				}
			}
		}
		return null;
	}
}
