package com.src.sim.metaioapplication.logic;

import com.src.sim.metaioapplication.logic.resource.Aim;
import com.src.sim.metaioapplication.logic.resource.Direction;
import com.src.sim.metaioapplication.logic.resource.LocationObject;
import com.src.sim.metaioapplication.logic.resource.Tracker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrackerAlgo {

	private Map<Integer, Tracker> trackerMap;
	private Aim aim;
	private List<List<Tracker>> ways;
	private int distance = -1;

	public TrackerAlgo(Map<Integer, Tracker> trackerMap) {
		this.trackerMap = trackerMap;
	}

	public Map<Aim, List<List<Tracker>>> findWays(Tracker start, LocationObject lObject) {
		List<Tracker> aimTracker = findAimTracker(trackerMap.values(), lObject);
		Map<Aim, List<List<Tracker>>> waysToAim = new HashMap<Aim, List<List<Tracker>>>();
		for (Tracker tracker : aimTracker) {
			this.aim = new Aim(tracker, lObject);
			findWay(start);
			if (!ways.isEmpty()) {
				waysToAim.put(aim, removeToLongWays(ways));
			}
		}

		List<Aim> removeAims = new ArrayList<Aim>();
		for (Aim aim : waysToAim.keySet()) {
			List<List<Tracker>> ways = waysToAim.get(aim);
			if (getMinDistance(ways) > distance) {
				removeAims.add(aim);
			} else if (ways.size() > 1) {
				for (List<Tracker> way : ways) {
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

	private static List<Tracker> findAimTracker(Collection<Tracker> trackerCollaction, LocationObject lObject) {
		List<Tracker> aimTrackers = new ArrayList<Tracker>();
        int distanceToLObject = -1;
		for (Tracker tracker : trackerCollaction) {
			if (tracker.getDirectionToLocationObject(lObject) != null) {
                int distance = tracker.getDirectionToLocationObject(lObject).getDistanceToLocationObject(lObject);
                if(distanceToLObject == -1 || distance <= distanceToLObject) {
                    aimTrackers.add(tracker);
                    distanceToLObject = distance;
                }
			}
		}
		return aimTrackers;
	}

	private List<List<Tracker>> findWay(final Tracker start) {
		ways = new ArrayList<List<Tracker>>();
		start(new ArrayList<Tracker>() {
			private static final long serialVersionUID = 1L;
			{
				add(start);
			}
		}, new HashMap<Tracker, List<Tracker>>());
		return ways;
	}

	private void start(List<Tracker> way, Map<Tracker, List<Tracker>> wayPointsOld) {
		Map<Tracker, List<Tracker>> wayPoints = new HashMap<Tracker, List<Tracker>>(wayPointsOld);
		Tracker lastPoint = way.get(way.size() - 1);

		int distance = getMinDistance(ways);
		if (distance != 0 && getDistance(way) > distance) {
			return;
		} else if (way.contains(aim.getTracker())) {
			addWay(way);
			return;
		} else if (!wayPoints.containsKey(lastPoint)) {
			wayPoints.put(lastPoint, new ArrayList<Tracker>());
		}

		List<Tracker> usedTrackers = wayPoints.get(lastPoint);
		List<Integer> lastPointTrackers = lastPoint.getTrackers();
		for (int i = 0; i < lastPointTrackers.size(); i++) {
			Tracker tracker;
			if (lastPointTrackers.contains(aim.getTracker())) {
				tracker = aim.getTracker();
				i = lastPointTrackers.size();
			} else {
				tracker = trackerMap.get(lastPointTrackers.get(i));
			}

			if (!usedTrackers.contains(tracker)) {
				usedTrackers.add(tracker);

				if (!way.contains(tracker)) {
					List<Tracker> newWay = new ArrayList<Tracker>(way);
					newWay.add(tracker);
					start(newWay, wayPoints);
				}
			}
		}
	}

	private void addWay(List<Tracker> newWay) {
		int newDistance = getDistance(newWay);
		distance = newDistance == 0 ? newDistance : distance < 0 ? newDistance : distance > newDistance ? newDistance
				: distance;
		if (newDistance <= distance)
			ways.add(newWay);
	}

	private static List<List<Tracker>> removeToLongWays(List<List<Tracker>> ways) {
		List<List<Tracker>> newWays = new ArrayList<List<Tracker>>();
		int distance = getMinDistance(ways);
		for (List<Tracker> way : ways) {
			int wayDistance = getDistance(way);
			if (wayDistance <= distance) {
				newWays.add(way);
			}
		}
		return new ArrayList<List<Tracker>>(newWays);
	}

	private static int getMinDistance(List<List<Tracker>> ways) {
		int distance = 0;
		for (List<Tracker> way : ways) {
			int wayDistance = getDistance(way);
			distance = distance <= 0 ? wayDistance : distance > wayDistance ? wayDistance : distance;
		}
		return distance;
	}

	public static int getDistance(List<Tracker> trackers) {
		Tracker oldTracker = null;
		int distance = 0;
		for (Tracker tracker : trackers) {
			if (oldTracker != null) {
				distance += oldTracker.getDistanceToTracker(tracker);
			}
			oldTracker = tracker;
		}
		return distance;
	}

	public static int getWholeDistanceToLocationObject(List<Tracker> trackers, LocationObject lObject) {
		int distance = getDistance(trackers);
		Direction direction = trackers.get(trackers.size() - 1).getDirectionToLocationObject(lObject);
		if (direction != null) {
			distance += direction.getDistanceToLocationObject(lObject);
		}
		return distance;
	}

	// TODO Delete
	public static void printWay(List<Tracker> trackers, Aim aim) {
		String way = "";
		Tracker oldTracker = null;
		for (Tracker tracker : trackers) {
			if (oldTracker != null) {
				way += " (" + oldTracker.getDirectionIconToTracker(tracker)
						+ oldTracker.getDirectionToTracker(tracker).printLocationObjectList() + "), \n"
						+ tracker.getId();
			} else {
				way += tracker.getId();
			}
			oldTracker = tracker;
		}
		way += " (" + aim.getDirection().getRotation().name() + " " + aim.getDirection().printLocationObjectList() + ")";
		System.out.println(way);
	}
}
