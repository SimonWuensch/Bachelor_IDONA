package com.src.sim.metaioapplication.logic.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.owlike.genson.Genson;
import com.owlike.genson.annotation.JsonIgnore;
import com.src.sim.metaioapplication.logic.resource.LocationObject.Kind;
import com.src.sim.metaioapplication.logic.resource.LocationObject.ToiletKind;

public class History {

    @JsonIgnore
    private long id;
	private Map<Integer, Tracker> trackerMap;
	private Map<Kind, List<LocationObject>> lObjectMap;

	public History() {
		trackerMap = new HashMap<Integer, Tracker>();
		lObjectMap = new HashMap<Kind, List<LocationObject>>();
	}

    public String toJson() {
        Genson genson = new Genson();
        return genson.serialize(this);
    }

    public static History JsonToHistory(String jsonString) {
        Genson genson = new Genson();
        return genson.deserialize(jsonString, History.class);
    }

    @JsonIgnore
    public long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(long id) {
        this.id = id;
    }

    public Map<Integer, Tracker> getTrackerMap() {
		return trackerMap;
	}

	public void setTrackerMap(Map<Integer, Tracker> trackerMap) {
		this.trackerMap = trackerMap;
	}

	public Map<Kind, List<LocationObject>> getlObjectMap() {
		return lObjectMap;
	}

	public void setlObjectMap(Map<Kind, List<LocationObject>> lObjectMap) {
		this.lObjectMap = lObjectMap;
	}

	public void initTrackers() throws Exception {
		addDirectionToTracker(1, 2, 6, Direction.LEFT, Direction.LEFT,//
				new HashMap<LocationObject, Integer>() {
					private static final long serialVersionUID = 1L;

					{
						put(initLocationObjectRoom("C"), 1);
						put(initLocationObjectRoom("E"), 5);
					}
				}, new HashMap<LocationObject, Integer>() {
					private static final long serialVersionUID = 1L;

					{
						put(initLocationObjectRoom("Q"), 2);
						put(initLocationObjectMultiple(Kind.FIREDRENCHER), 4);
					}
				});

		addDirectionToTracker(1, 6, 8, Direction.RIGHT, Direction.LEFT,//
				new HashMap<LocationObject, Integer>() {
					private static final long serialVersionUID = 1L;

					{
						put(initLocationObjectToilet(ToiletKind.HANDICAPPED), 2);
					}
				}, new HashMap<LocationObject, Integer>() {
					private static final long serialVersionUID = 1L;

					{
						put(initLocationObjectRoom("A"), 3);
						put(initLocationObjectMultiple(Kind.MEDIKIT), 5);
					}
				});

		addDirectionToTracker(1, 9, 4, Direction.BACKWARDS, Direction.BACKWARDS,
				new HashMap<LocationObject, Integer>(), new HashMap<LocationObject, Integer>());

		addDirectionToTracker(2, 12, 2, Direction.BACKWARDS, Direction.BACKWARDS,//
				new HashMap<LocationObject, Integer>() {
					private static final long serialVersionUID = 1L;

					{
						put(initLocationObjectRoom("S"), 1);
					}
				}, new HashMap<LocationObject, Integer>() {
					private static final long serialVersionUID = 1L;

					{
						put(initLocationObjectRoom("R"), 1);
					}
				});

		addDirectionToTracker(2, 3, 2, Direction.RIGHT, Direction.RIGHT, new HashMap<LocationObject, Integer>(),
				new HashMap<LocationObject, Integer>());

		addDirectionToTracker(3, 5, 8, Direction.LEFT, Direction.RIGHT,//
				new HashMap<LocationObject, Integer>() {
					private static final long serialVersionUID = 1L;

					{
						put(initLocationObjectRoom("H"), 5);
					}
				}, new HashMap<LocationObject, Integer>() {
					private static final long serialVersionUID = 1L;

					{
						put(initLocationObjectRoom("T"), 3);
					}
				});

		addDirectionToTracker(3, 10, 5, Direction.BACKWARDS, Direction.BACKWARDS,//
				new HashMap<LocationObject, Integer>() {
					private static final long serialVersionUID = 1L;

					{
					}
				}, new HashMap<LocationObject, Integer>() {
					private static final long serialVersionUID = 1L;

					{
						put(initLocationObjectRoom("G"), 2);
					}
				});

		addDirectionToTracker(3, 11, 4, Direction.BACKWARDS, Direction.FORWARDS,//
				new HashMap<LocationObject, Integer>() {
					private static final long serialVersionUID = 1L;

					{
					}
				}, new HashMap<LocationObject, Integer>() {
					private static final long serialVersionUID = 1L;

					{
						put(initLocationObjectRoom("G"), 2);
					}
				});

		addDirectionToTracker(4, 9, 2, Direction.RIGHT, Direction.RIGHT, new HashMap<LocationObject, Integer>(),
				new HashMap<LocationObject, Integer>());

		addDirectionToTracker(4, 10, 5, Direction.LEFT, Direction.LEFT,//
				new HashMap<LocationObject, Integer>() {
					private static final long serialVersionUID = 1L;

					{
						put(initLocationObjectRoom("M"), 2);
						put(initLocationObjectMultiple(Kind.MEDIKIT), 4);
					}
				}, new HashMap<LocationObject, Integer>() {
					private static final long serialVersionUID = 1L;

					{
						put(initLocationObjectRoom("D"), 2);
						put(initLocationObjectRoom("F"), 5);
					}
				});

		addDirectionToTracker(4, 7, 5, Direction.BACKWARDS, Direction.BACKWARDS,//
				new HashMap<LocationObject, Integer>() {
					private static final long serialVersionUID = 1L;

					{
						put(initLocationObjectRoom("N"), 3);
					}
				}, new HashMap<LocationObject, Integer>() {
					private static final long serialVersionUID = 1L;

					{
					}
				});

		addDirectionToTracker(5, 11, 3, Direction.BACKWARDS, Direction.LEFT,//
				new HashMap<LocationObject, Integer>() {
					private static final long serialVersionUID = 1L;

					{
					}
				}, new HashMap<LocationObject, Integer>() {
					private static final long serialVersionUID = 1L;

					{
						put(initLocationObjectToilet(ToiletKind.MALE), 3);
					}
				});

		addDirectionToTracker(5, 8, 9, Direction.LEFT, Direction.RIGHT,//
				new HashMap<LocationObject, Integer>() {
					private static final long serialVersionUID = 1L;

					{
						put(initLocationObjectRoom("J"), 8);
					}
				}, new HashMap<LocationObject, Integer>() {
					private static final long serialVersionUID = 1L;

					{
						put(initLocationObjectMultiple(Kind.EMERGENCYEXIT), 3);
					}
				});

		addDirectionToTracker(6, 9, 4, Direction.BACKWARDS, Direction.LEFT,//
				new HashMap<LocationObject, Integer>() {
					private static final long serialVersionUID = 1L;

					{
						put(initLocationObjectToilet(ToiletKind.FEMAIL), 1);
					}
				}, new HashMap<LocationObject, Integer>() {
					private static final long serialVersionUID = 1L;

					{
						put(initLocationObjectRoom("B"), 2);
					}
				});

		addDirectionToTracker(6, 7, 11, Direction.RIGHT, Direction.LEFT, new HashMap<LocationObject, Integer>() {
			private static final long serialVersionUID = 1L;
			{
				put(initLocationObjectMultiple(Kind.EMERGENCYEXIT), 9);
			}
		}, new HashMap<LocationObject, Integer>() {
			private static final long serialVersionUID = 1L;

			{
			}
		});

		addDirectionToTracker(8, 7, 6, Direction.LEFT, Direction.RIGHT,//
				new HashMap<LocationObject, Integer>() {
					private static final long serialVersionUID = 1L;

					{
						put(initLocationObjectRoom("L"), 4);
						put(initLocationObjectMultiple(Kind.FIREDRENCHER), 3);
					}
				}, new HashMap<LocationObject, Integer>() {
					private static final long serialVersionUID = 1L;

					{
					}
				});

		addDirectionToTracker(8, 10, 5, Direction.BACKWARDS, Direction.FORWARDS,//
				new HashMap<LocationObject, Integer>() {
					private static final long serialVersionUID = 1L;

					{
					}
				}, new HashMap<LocationObject, Integer>() {
					private static final long serialVersionUID = 1L;

					{
						put(initLocationObjectRoom("K"), 2);
					}
				});

		addDirectionToTracker(11, 8, 6, Direction.BACKWARDS, Direction.BACKWARDS,//
				new HashMap<LocationObject, Integer>() {
					private static final long serialVersionUID = 1L;

					{
						put(initLocationObjectRoom("K"), 2);
					}
				}, new HashMap<LocationObject, Integer>() {
					private static final long serialVersionUID = 1L;

					{
					}
				});

		addDirectionToTracker(11, 10, 2, Direction.RIGHT, Direction.RIGHT, new HashMap<LocationObject, Integer>(),
				new HashMap<LocationObject, Integer>());
	}

	private Direction addDirectionToTracker(int startTrackerID, int endTrackerID, int distance, String startToEnd,
			String endToStart, Map<LocationObject, Integer> leftObjectMap, Map<LocationObject, Integer> rightObjectMap) {

		Tracker startTracker = initTracker(startTrackerID);
		Direction direction = new Direction(startToEnd);

		for (LocationObject object : leftObjectMap.keySet()) {
			direction.addLocationObjectToLeft(object, leftObjectMap.get(object));
		}
		for (LocationObject object : rightObjectMap.keySet()) {
			direction.addLocationObjectToRight(object, rightObjectMap.get(object));
		}

		startTracker.addDirection(direction);
		direction.setTracker(startTracker, initTracker(endTrackerID), distance, endToStart);
		return direction;
	}

	private Tracker initTracker(int trackerID) {
		Tracker tracker;
		if (!trackerMap.containsKey(trackerID)) {
			tracker = new Tracker(trackerID);
			trackerMap.put(trackerID, tracker);
		} else {
			tracker = trackerMap.get(trackerID);
		}
		return tracker;
	}

	private LocationObject initLocationObjectRoom(String description) {
		return initLocationObject(Kind.ROOM, description);
	}

	private LocationObject initLocationObjectToilet(ToiletKind toiletKind) {
		return initLocationObject(Kind.TOILET, toiletKind.toString());
	}

	private LocationObject initLocationObjectMultiple(Kind kind) throws Exception {
		if (kind.isMultipleObject())
			return initLocationObject(kind, kind.toString());
		else
			throw new Exception("location object cannot be initialized. It is not a multiple kind.");
	}

	private LocationObject initLocationObject(Kind kind, String description) {
		final LocationObject lObject = new LocationObject(kind, description);
		if (!lObjectMap.containsKey(kind)) {
			lObjectMap.put(kind, new ArrayList<LocationObject>() {
				private static final long serialVersionUID = 1L;
				{
					add(lObject);
				}
			});
			return lObject;
		}
		if (!kind.isMultipleObject()) {
			for (LocationObject lObjectValue : lObjectMap.get(kind)) {
				if (lObjectValue.getDescription().equals(description)) {
					return lObjectValue;
				}
			}
			lObjectMap.get(kind).add(lObject);
		}
		return lObject;
	}
}
