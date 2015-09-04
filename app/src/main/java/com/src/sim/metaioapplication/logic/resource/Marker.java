package com.src.sim.metaioapplication.logic.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.src.sim.metaioapplication.logic.resource.LocationObject.Kind;

public class Marker {

	private int id;
	private List<Direction> directions = new ArrayList<Direction>();

	public Marker(int id) {
		this.id = id;
	}

	public Marker() {
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

	public List<Integer> getMarkerss() {
		return new ArrayList<Integer>() {
			private static final long serialVersionUID = 1L;
			{
				for (Direction direction : directions) {
					if (direction.hasMarker()) {
						add(direction.getMarkerID());
					}
				}
			}
		};
	}

	public int getDistanceToMarker(Marker marker) {
		return getDirectionToMarker(marker).getDistance();
	}

	public String getDirectionIconToMarker(Marker marker) {
		return getDirectionToMarker(marker).getArrow().name();
	}

	public Direction getDirectionToMarker(Marker marker) {
		for (Direction direction : directions) {
			if (direction.hasMarker()) {
				if (direction.getMarkerID() == marker.getId()) {
					return direction;
				}
			}
		}
		throw new NullPointerException("No correct Marker found in this direction!");
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
