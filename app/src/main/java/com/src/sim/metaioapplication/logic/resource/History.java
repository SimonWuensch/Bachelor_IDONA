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

    @JsonIgnore private long id;
    private Map<Integer, Marker> markerMap;
    private Map<Kind, List<LocationObject>> lObjectMap;

    public History() {
        markerMap = new HashMap<Integer, Marker>();
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

    public Map<Integer, Marker> getMarkerMap() {
        return markerMap;
    }

    public void setMarkerMap(Map<Integer, Marker> markerMap) {
        this.markerMap = markerMap;
    }

    public Map<Kind, List<LocationObject>> getlObjectMap() {
        return lObjectMap;
    }

    public void setlObjectMap(Map<Kind, List<LocationObject>> lObjectMap) {
        this.lObjectMap = lObjectMap;
    }

    // ** ADD LOCATION METHODS ****************************************************************** //

    private Direction addDirectionToMarker(int startMarkerID,
                                           Arrow startToEnd,
                                           Map<LocationObject, Integer> leftObjectMap,
                                           Map<LocationObject, Integer> rightObjectMap) {
        return addDirectionToMarker(startMarkerID, -1, 0, startToEnd, Arrow.RIGHT, leftObjectMap, rightObjectMap);
    }

    private Direction addDirectionToMarker(int startMarkerID,
                                           int endMarkerID,
                                           int distance,
                                           Arrow startToEnd,
                                           Arrow endToStart,
                                           Map<LocationObject, Integer> leftObjectMap,
                                           Map<LocationObject, Integer> rightObjectMap) {

        Marker startMarker = initMarker(startMarkerID);
        Direction direction = new Direction(startToEnd);

        for (LocationObject object : leftObjectMap.keySet()) {
            direction.addLocationObjectToLeft(object, leftObjectMap.get(object));
        }
        for (LocationObject object : rightObjectMap.keySet()) {
            direction.addLocationObjectToRight(object, rightObjectMap.get(object));
        }

        startMarker.addDirection(direction);
        direction.setMarker(startMarker, initMarker(endMarkerID), distance, endToStart);
        return direction;
    }

    private Marker initMarker(int markerID) {
        if (markerID == -1) {
            return null;
        }

        Marker marker;
        if (!markerMap.containsKey(markerID)) {
            marker = new Marker(markerID);
            markerMap.put(markerID, marker);
        } else {
            marker = markerMap.get(markerID);
        }
        return marker;
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

    public void initMarkersSHL() throws Exception {
        // Floor - 0
        addDirectionToMarker(1, 2, 27, Arrow.LEFT, Arrow.RIGHT,//
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

        addDirectionToMarker(2, 3, 25, Arrow.BACKWARDS_LEFT, Arrow.BACKWARDS,//
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

        addDirectionToMarker(3, Arrow.RIGHT, //
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

        addDirectionToMarker(3, 4, 29, Arrow.LEFT, Arrow.RIGHT,//
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

        addDirectionToMarker(4, 5, 8, Arrow.BACKWARDS, Arrow.LEFT,//
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

        addDirectionToMarker(4, 6, 40, Arrow.LEFT, Arrow.RIGHT,//
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

        addDirectionToMarker(6, 7, 43, Arrow.LEFT, Arrow.BACKWARDS,//
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

        addDirectionToMarker(7, 8, 30, Arrow.LEFT_UP, Arrow.RIGHT_DOWN,//
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

        addDirectionToMarker(5, 13, 40, Arrow.UP, Arrow.DOWN,//
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

        addDirectionToMarker(13, 12, 12, Arrow.LEFT, Arrow.RIGHT,//
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

        addDirectionToMarker(12, 14, 24, Arrow.LEFT_UP, Arrow.RIGHT_DOWN,//
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

        addDirectionToMarker(14, 23, 24, Arrow.LEFT_UP, Arrow.RIGHT_DOWN,//
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

        addDirectionToMarker(23, 24, 28, Arrow.RIGHT, Arrow.LEFT,//
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

        addDirectionToMarker(24, Arrow.RIGHT,//
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

        addDirectionToMarker(23, 22, 43, Arrow.LEFT, Arrow.BACKWARDS,//
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

        addDirectionToMarker(22, 20, 14, Arrow.LEFT, Arrow.RIGHT,//
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

        addDirectionToMarker(20, 16, 30, Arrow.RIGHT_DOWN, Arrow.LEFT_UP,//
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

        addDirectionToMarker(16, 17, 14, Arrow.LEFT, Arrow.RIGHT,//
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

        addDirectionToMarker(17, 10, 30, Arrow.RIGHT_DOWN, Arrow.LEFT_UP,//
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

        addDirectionToMarker(10, 8, 13, Arrow.LEFT, Arrow.RIGHT,//
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

        addDirectionToMarker(8, Arrow.LEFT,//
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
