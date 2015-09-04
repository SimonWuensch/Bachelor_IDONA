package com.src.sim.metaioapplication.logic.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.src.sim.metaioapplication.logic.resource.LocationObject.Kind;

public class Direction {
    public static String ARROWNORMAL = "normal";
    public static String ARROWCURVE = "curve";
    public static String ARROWTURN = "turn";

    public static String LEFTHAND = "lefthand";
    public static String RIGHTHAND = "righthand";

    private Arrow arrow;
    private int distance;
    private int markerID;
    private Map<String, Map<LocationObject, Integer>> locationObjects;

    public Direction(Marker marker, int distance, Arrow arrow) {
        this.arrow = arrow;
        this.distance = distance;
        this.markerID = marker.getId();
        init();
    }

    public Direction(Arrow arrow) {
        this.arrow = arrow;
        init();
    }

    public Direction() {}

    public Arrow getArrow() {
        return arrow;
    }

    public void setArrow(Arrow arrow) {
        this.arrow = arrow;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getMarkerID() {
        return markerID;
    }

    public void setMarkerID(int markerID) {
        this.markerID = markerID;
    }

    public Map<String, Map<LocationObject, Integer>> getLocationObjects() {
        return locationObjects;
    }

    public void setLocationObjects(Map<String, Map<LocationObject, Integer>> locationObjects) {
        this.locationObjects = locationObjects;
    }

    public Map<Kind, List<LocationObject>> getLocationObjectMap() {
        Map<Kind, List<LocationObject>> locationObjectMap = new HashMap<Kind, List<LocationObject>>();
        for(Map<LocationObject, Integer> locationObjectIntegerMap : locationObjects.values()){
            for(final LocationObject lObject : locationObjectIntegerMap.keySet()){
                if (!locationObjectMap.containsKey(lObject.getKind())) {
                    locationObjectMap.put(lObject.getKind(), new ArrayList<LocationObject>() {
                        private static final long serialVersionUID = 1L;
                        {
                            add(lObject);
                        }
                    });
                } else {
                    locationObjectMap.get(lObject.getKind()).add(lObject);
                }
            }
        }
        return locationObjectMap;
    }

    private void init() {
        locationObjects = new HashMap<String, Map<LocationObject, Integer>>();
        locationObjects.put(LEFTHAND, new HashMap<LocationObject, Integer>());
        locationObjects.put(RIGHTHAND, new HashMap<LocationObject, Integer>());
    }

    public String getHandside(LocationObject object) {
        for (LocationObject lObjectValue : locationObjects.get(LEFTHAND).keySet()) {
            if (lObjectValue.getDescription().equals(object.getDescription())) {
                return LEFTHAND;
            }
        }
        for (LocationObject lObjectValue : locationObjects.get(RIGHTHAND).keySet()) {
            if (lObjectValue.getDescription().equals(object.getDescription())) {
                return RIGHTHAND;
            }
        }
        throw new NullPointerException("Direction contains not the LocationObject [" + object.getDescription() + "]");
    }

    public int getCountPosition(LocationObject lObject) {
        Object[] array = locationObjects.get(getHandside(lObject)).entrySet().toArray();
        Arrays.sort(array, new Comparator<Object>() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<LocationObject, Integer>) o1).getValue().compareTo(
                        ((Map.Entry<LocationObject, Integer>) o2).getValue());
            }
        });
        for (int i = 0; i < array.length; i++) {
            if (((Map.Entry<LocationObject, Integer>) array[i]).getKey().getDescription().equals(lObject.getDescription())) {
                return i + 1;
            }
        }
        return -1;
    }

    public int getCountPositionRoom(LocationObject lObject) {
        Object[] array = locationObjects.get(getHandside(lObject)).entrySet().toArray();
        Arrays.sort(array, new Comparator<Object>() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<LocationObject, Integer>) o1).getValue().compareTo(
                        ((Map.Entry<LocationObject, Integer>) o2).getValue());
            }
        });
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            Kind kind = ((Map.Entry<LocationObject, Integer>) array[i]).getKey().getKind();
            if(kind.equals(Kind.ROOM) || kind.equals(Kind.TOILET) || kind.equals(Kind.STAIRWELL) || kind.equals(Kind.EMERGENCYEXIT) || kind.equals(Kind.EXIT)){
                count++;
            }
            if (((Map.Entry<LocationObject, Integer>) array[i]).getKey().getDescription().equals(lObject.getDescription())) {
                return count;
            }
        }
        return -1;
    }

    public int getDistanceToLocationObject(LocationObject lObject) {
        String handSide = getHandside(lObject);
        for (LocationObject lObjectValue : locationObjects.get(handSide).keySet()) {
            if (lObjectValue.getDescription().equals(lObject.getDescription())) {
                return locationObjects.get(handSide).get(lObjectValue);
            }
        }
        throw new NullPointerException("Direction contains not the LocationObject [" + lObject.getDescription() + "]");
    }

    public String getWayDescriptionToLocationObject(LocationObject lObject){
        int position = getCountPositionRoom(lObject);
        String positionString = getNumberCall(position);
        String handSide = getHandside(lObject).equals(Direction.LEFTHAND) ? "LEFT" : "RIGHT";
        return handSide + " - " + positionString + " door.";
    }

    private String getNumberCall(int number){
        if(number == 1){
            return number + "st ";
        }else if(number == 2){
            return number + "nd ";
        }else if(number == 3){
            return number + "rd ";
        }else return number + "th";
    }

    public void setMarker(Marker startMarker, Marker endMarker, int distance, Arrow rotation) {
        if(endMarker != null){
            this.markerID = endMarker.getId();
            this.distance = distance;

            Direction endToStartDirection = new Direction(startMarker, distance, rotation);

            for (LocationObject lObject : locationObjects.get(LEFTHAND).keySet()) {
                endToStartDirection.addLocationObject(lObject, distance - locationObjects.get(LEFTHAND).get(lObject),
                        RIGHTHAND);
            }
            for (LocationObject lObject : locationObjects.get(RIGHTHAND).keySet())
                endToStartDirection.addLocationObject(lObject, distance - locationObjects.get(RIGHTHAND).get(lObject),
                        LEFTHAND);

            endMarker.addDirection(endToStartDirection);
        }
    }

    public boolean hasMarker() {
        return markerID > 0;
    }

    public void addLocationObjectToLeft(LocationObject lObject, int distance) {
        addLocationObject(lObject, distance, LEFTHAND);
    }

    public void addLocationObjectToRight(LocationObject lObject, int distance) {
        addLocationObject(lObject, distance, RIGHTHAND);
    }

    private void addLocationObject(final LocationObject lObject, int distance, String handside) {
        locationObjects.get(handside).put(lObject, distance);
    }

    public String printLocationObjectList() {
        StringBuilder builder = new StringBuilder();

        Map<LocationObject, Integer> leftObjects = locationObjects.get(LEFTHAND);
        Map<LocationObject, Integer> rightObjects = locationObjects.get(RIGHTHAND);

        if (!leftObjects.isEmpty() && !rightObjects.isEmpty())
            builder.append(", ");

        if (!leftObjects.isEmpty())
            builder.append(" Left: ");

        for (LocationObject leftObject : leftObjects.keySet()) {
            builder.append(leftObject.toString() + " pos: " + getCountPosition(leftObject) + " dis: "
                    + getDistanceToLocationObject(leftObject));

            if (leftObjects.size() - 1 != getCountPosition(leftObject)) {
                builder.append(", ");
            }
        }

        if (!rightObjects.isEmpty())
            builder.append(" Right: ");

        for (LocationObject rightObject : rightObjects.keySet()) {
            builder.append(rightObject.toString() + " pos: " + getCountPosition(rightObject) + " dis: "
                    + getDistanceToLocationObject(rightObject));

            if (rightObjects.size() - 1 != getCountPosition(rightObject)) {
                builder.append(", ");
            }
        }

        return builder.toString();
    }
}

