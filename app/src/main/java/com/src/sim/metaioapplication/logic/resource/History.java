package com.src.sim.metaioapplication.logic.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.owlike.genson.Genson;
import com.owlike.genson.annotation.JsonIgnore;
import com.src.sim.metaioapplication.logic.resource.LocationObject.Kind;
import com.src.sim.metaioapplication.logic.resource.LocationObject.ToiletKind;
import com.src.sim.metaioapplication.logic.resource.Direction.Arrow;

public class History {

    @JsonIgnore private long id;
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

    // ** ADD LOCATION METHODS ****************************************************************** //

    private Direction addDirectionToTracker(int startTrackerID,
                                            Arrow startToEnd,
                                            Map<LocationObject, Integer> leftObjectMap,
                                            Map<LocationObject, Integer> rightObjectMap) {
        return addDirectionToTracker(startTrackerID, -1, 0, startToEnd, Arrow.RIGHT, leftObjectMap, rightObjectMap);
    }

    private Direction addDirectionToTracker(int startTrackerID,
                                            int endTrackerID,
                                            int distance,
                                            Arrow startToEnd,
                                            Arrow endToStart,
                                            Map<LocationObject, Integer> leftObjectMap,
                                            Map<LocationObject, Integer> rightObjectMap) {

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
        if (trackerID == -1) {
            return null;
        }

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
            return initLocationObject(kind, kind.name());
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

    // ** SHL LOCATION ************************************************************************** //

    public void initTrackersSHLTwo() throws Exception {
        // Floor - 0
        addDirectionToTracker(1, 2, 27, Arrow.LEFT, Arrow.RIGHT,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // LEFT - LocationObject, Distance
                    {
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // RIGHT - LocationObject, Distance
                    {
                        put(initLocationObjectToilet(ToiletKind.FEMAIL), 5);
                        put(initLocationObjectToilet(ToiletKind.HANDICAPPED), 10);
                        put(initLocationObjectToilet(ToiletKind.MALE), 15);
                    }
                });

        addDirectionToTracker(2, 3, 25, Arrow.BACKWARDS_LEFT, Arrow.BACKWARDS,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // LEFT - LocationObject, Distance
                    {
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // RIGHT - LocationObject, Distance
                    {
                    }
                });

        addDirectionToTracker(3, Arrow.RIGHT, //
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // LEFT - LocationObject, Distance
                    {
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // RIGHT - LocationObject, Distance
                    {
                    }
                });

        addDirectionToTracker(3, 4, 29, Arrow.LEFT, Arrow.RIGHT,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // LEFT - LocationObject, Distance
                    {
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // RIGHT - LocationObject, Distance
                    {
                    }
                });

        addDirectionToTracker(4, 5, 8, Arrow.BACKWARDS, Arrow.LEFT,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // LEFT - LocationObject, Distance
                    {
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // RIGHT - LocationObject, Distance
                    {
                    }
                });

        addDirectionToTracker(4, 6, 40, Arrow.LEFT, Arrow.RIGHT,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // LEFT - LocationObject, Distance
                    {
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // RIGHT - LocationObject, Distance
                    {
                    }
                });

        addDirectionToTracker(6, 7, 43, Arrow.LEFT, Arrow.BACKWARDS,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // LEFT - LocationObject, Distance
                    {
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // RIGHT - LocationObject, Distance
                    {
                    }
                });

        addDirectionToTracker(7, 8, 30, Arrow.LEFT_UP, Arrow.RIGHT_DOWN,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // LEFT - LocationObject, Distance
                    {
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // RIGHT - LocationObject, Distance
                    {
                    }
                });

        addDirectionToTracker(5, 13, 40, Arrow.UP, Arrow.DOWN,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // LEFT - LocationObject, Distance
                    {
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // RIGHT - LocationObject, Distance
                    {
                    }
                });

        addDirectionToTracker(13, 12, 12, Arrow.LEFT, Arrow.RIGHT,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // LEFT - LocationObject, Distance
                    {
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // RIGHT - LocationObject, Distance
                    {
                    }
                });

        addDirectionToTracker(12, 14, 24, Arrow.LEFT_UP, Arrow.RIGHT_DOWN,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // LEFT - LocationObject, Distance
                    {
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // RIGHT - LocationObject, Distance
                    {
                    }
                });

        addDirectionToTracker(14, 23, 24, Arrow.LEFT_UP, Arrow.RIGHT_DOWN,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // LEFT - LocationObject, Distance
                    {
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // RIGHT - LocationObject, Distance
                    {
                    }
                });

        addDirectionToTracker(23, 24, 28, Arrow.RIGHT, Arrow.LEFT,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // LEFT - LocationObject, Distance
                    {
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // RIGHT - LocationObject, Distance
                    {
                    }
                });

        addDirectionToTracker(24, Arrow.RIGHT,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // LEFT - LocationObject, Distance
                    {
                        put(initLocationObjectRoom("AD15"), 4);
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // RIGHT - LocationObject, Distance
                    {
                        put(initLocationObjectRoom("AD21"), 2);
                        put(initLocationObjectRoom("AD20"), 6);
                        put(initLocationObjectRoom("AD19"), 10);
                    }
                });

        addDirectionToTracker(23, 22, 43, Arrow.LEFT, Arrow.BACKWARDS,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // LEFT - LocationObject, Distance
                    {
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // RIGHT - LocationObject, Distance
                    {
                    }
                });

        addDirectionToTracker(22, 20, 14, Arrow.LEFT, Arrow.RIGHT,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // LEFT - LocationObject, Distance
                    {
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // RIGHT - LocationObject, Distance
                    {
                    }
                });

        addDirectionToTracker(20, 16, 30, Arrow.RIGHT_DOWN, Arrow.LEFT_UP,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // LEFT - LocationObject, Distance
                    {
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // RIGHT - LocationObject, Distance
                    {
                    }
                });

        addDirectionToTracker(16, 17, 14, Arrow.LEFT, Arrow.RIGHT,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // LEFT - LocationObject, Distance
                    {
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // RIGHT - LocationObject, Distance
                    {
                    }
                });

        addDirectionToTracker(17, 10, 30, Arrow.RIGHT_DOWN, Arrow.LEFT_UP,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // LEFT - LocationObject, Distance
                    {
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // RIGHT - LocationObject, Distance
                    {
                    }
                });

        addDirectionToTracker(10, 8, 13, Arrow.LEFT, Arrow.RIGHT,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // LEFT - LocationObject, Distance
                    {
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // RIGHT - LocationObject, Distance
                    {
                    }
                });

        addDirectionToTracker(8, Arrow.LEFT,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // LEFT - LocationObject, Distance
                    {
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    // RIGHT - LocationObject, Distance
                    {
                        put(initLocationObjectRoom("AB8"), 5);
                        put(initLocationObjectRoom("AB7"), 10);
                        put(initLocationObjectRoom("AB5"), 13);
                    }
                });
    }
}
