package com.src.sim.metaioapplication.logic;

import com.src.sim.metaioapplication.logic.resource.Aim;
import com.src.sim.metaioapplication.logic.resource.Direction;
import com.src.sim.metaioapplication.logic.resource.LocationObject;
import com.src.sim.metaioapplication.logic.resource.Marker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WayAlgorithm {

	private Map<Integer, Marker> markerMap;
	private Aim aim;
	private List<List<Marker>> ways;
	private int distance = -1;

	public WayAlgorithm(Map<Integer, Marker> markerMap) {
		this.markerMap = markerMap;
	}

	public Map<Aim, List<List<Marker>>> findWays(Marker start, LocationObject lObject) {
		List<Marker> aimMarker = findAimMarker(markerMap.values(), lObject);
		Map<Aim, List<List<Marker>>> waysToAim = new HashMap<Aim, List<List<Marker>>>();
		for (Marker marker : aimMarker) {
			this.aim = new Aim(marker, lObject);
			findWay(start);
			if (!ways.isEmpty()) {
				waysToAim.put(aim, removeToLongWays(ways));
			}
		}

		List<Aim> removeAims = new ArrayList<Aim>();
		for (Aim aim : waysToAim.keySet()) {
			List<List<Marker>> ways = waysToAim.get(aim);
			if (getMinDistance(ways) > distance) {
				removeAims.add(aim);
			} else if (ways.size() > 1) {
				for (List<Marker> way : ways) {
					if (getDistance(way) > distance) {
						ways.remove(way);
					}
				}
			}
		}
		for (Aim aim : removeAims) {
			waysToAim.remove(aim);
		}
		return waysToAim;
	}

	private static List<Marker> findAimMarker(Collection<Marker> markerCollaction, LocationObject lObject) {
		List<Marker> aimMarkers = new ArrayList<Marker>();
        int distanceToLObject = -1;
		for (Marker marker : markerCollaction) {
			if (marker.getDirectionToLocationObject(lObject) != null) {
                int distance = marker.getDirectionToLocationObject(lObject).getDistanceToLocationObject(lObject);
                if(distanceToLObject == -1 || distance <= distanceToLObject) {
                    aimMarkers.add(marker);
                    distanceToLObject = distance;
                }
			}
		}
		return aimMarkers;
	}

	private List<List<Marker>> findWay(final Marker start) {
		ways = new ArrayList<List<Marker>>();
		start(new ArrayList<Marker>() {
			private static final long serialVersionUID = 1L;
			{
				add(start);
			}
		}, new HashMap<Marker, List<Marker>>());
		return ways;
	}

	private void start(List<Marker> way, Map<Marker, List<Marker>> wayPointsOld) {
		Map<Marker, List<Marker>> wayPoints = new HashMap<Marker, List<Marker>>(wayPointsOld);
		Marker lastPoint = way.get(way.size() - 1);

		int distance = getMinDistance(ways);
		if (distance != 0 && getDistance(way) > distance) {
			return;
		} else if (way.contains(aim.getMarker())) {
			addWay(way);
			return;
		} else if (!wayPoints.containsKey(lastPoint)) {
			wayPoints.put(lastPoint, new ArrayList<Marker>());
		}

		List<Marker> usedMarkers = wayPoints.get(lastPoint);
		List<Integer> lastPointMarkers = lastPoint.getMarkerss();
		for (int i = 0; i < lastPointMarkers.size(); i++) {
			Marker marker;
			if (lastPointMarkers.contains(aim.getMarker())) {
				marker = aim.getMarker();
				i = lastPointMarkers.size();
			} else {
				marker = markerMap.get(lastPointMarkers.get(i));
			}

			if (!usedMarkers.contains(marker)) {
				usedMarkers.add(marker);

				if (!way.contains(marker)) {
					List<Marker> newWay = new ArrayList<Marker>(way);
					newWay.add(marker);
					start(newWay, wayPoints);
				}
			}
		}
	}

	private void addWay(List<Marker> newWay) {
		int newDistance = getDistance(newWay);
		distance = newDistance == 0 ? newDistance : distance < 0 ? newDistance : distance > newDistance ? newDistance
				: distance;
		if (newDistance <= distance)
			ways.add(newWay);
	}

	private static List<List<Marker>> removeToLongWays(List<List<Marker>> ways) {
		List<List<Marker>> newWays = new ArrayList<List<Marker>>();
		int distance = getMinDistance(ways);
		for (List<Marker> way : ways) {
			int wayDistance = getDistance(way);
			if (wayDistance <= distance) {
				newWays.add(way);
			}
		}
		return new ArrayList<List<Marker>>(newWays);
	}

	private static int getMinDistance(List<List<Marker>> ways) {
		int distance = 0;
		for (List<Marker> way : ways) {
			int wayDistance = getDistance(way);
			distance = distance <= 0 ? wayDistance : distance > wayDistance ? wayDistance : distance;
		}
		return distance;
	}

	public static int getDistance(List<Marker> markers) {
		Marker oldMarker = null;
		int distance = 0;
		for (Marker marker : markers) {
			if (oldMarker != null) {
				distance += oldMarker.getDistanceToMarker(marker);
			}
			oldMarker = marker;
		}
		return distance;
	}

	public static int getWholeDistanceToLocationObject(List<Marker> markers, LocationObject lObject) {
		int distance = getDistance(markers);
		Direction direction = markers.get(markers.size() - 1).getDirectionToLocationObject(lObject);
		if (direction != null) {
			distance += direction.getDistanceToLocationObject(lObject);
		}
		return distance;
	}

	// TODO Delete
	public static void printWay(List<Marker> markers, Aim aim) {
		String way = "";
		Marker oldMarker = null;
		for (Marker marker : markers) {
			if (oldMarker != null) {
				way += " (" + oldMarker.getDirectionIconToMarker(marker)
						+ oldMarker.getDirectionToMarker(marker).printLocationObjectList() + "), \n"
						+ marker.getId();
			} else {
				way += marker.getId();
			}
			oldMarker = marker;
		}
		way += " (" + aim.getDirection().getArrow().name() + " " + aim.getDirection().printLocationObjectList() + ")";
		System.out.println(way);
	}
}
