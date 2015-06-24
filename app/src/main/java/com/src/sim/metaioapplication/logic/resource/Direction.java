package com.src.sim.metaioapplication.logic.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.metaio.sdk.jni.Rotation;
import com.src.sim.metaioapplication.logic.resource.LocationObject.Kind;

public class Direction {

    public enum ArrowRotation {
        DEFAULT(-10.0f, -10.0f, -10.0f),
        LEFT(0.0f, 0.0f, 3.1599975f),
        UP(0.0f, 0.0f, 1.5999988f),
        BACKWARDS(-1.5799989f, -1.419999f, 0.0f),
        FORWARDS(-1.6199988f, 1.5499989f, 0.0f),
        RIGHT_UP(0.0f, 0.0f ,0.51999986f),
        LEFT_UP(0.0f, 0.0f ,2.639998f),
        RIGHT(0.0f, 0.0f ,0.0f),
        DOWN(0.0f, 0.0f ,-1.5999988f),
        RIGHT_DOWN(0.0f, 0.0f ,-0.43999988f),
        LEFT_DOWN(0.0f, 0.0f ,-2.719998f);

        private float[] rotation = new float[3];

        private ArrowRotation(float x, float y, float z){
            rotation = new float[] {x, y, z};
        }

        public float[] getRotation() {
            return rotation;
        }

        public void setRotation(float[] rotation) {
            this.rotation = rotation;
        }

        public Rotation getGeometryRotation(){
            return new Rotation(rotation[0], rotation[1], rotation[2]);
        }
    }

    public static String ARROWNORMAL = "normal";
    public static String ARROWCURVE = "curve";

    public static String LEFTHAND = "lefthand";
    public static String RIGHTHAND = "righthand";

    private ArrowRotation rotation;
    private String arrow;
    private int distance;
    private int trackerID;
    private Map<String, Map<LocationObject, Integer>> locationObjects;
    Map<Kind, List<LocationObject>> locationObjectMap;

    public Direction(Tracker tracker, int distance, ArrowRotation rotation) {
        this.rotation = rotation;
        this.distance = distance;
        this.trackerID = tracker.getId();
        initLocationObjectMap();
    }

    public Direction(ArrowRotation rotation) {
        this.rotation = rotation;
        initLocationObjectMap();
    }

    public Direction() {
    }

    public String getArrow() {
        return arrow == null ? ARROWNORMAL : arrow;
    }

    public void setArrow(String arrow) {
        this.arrow = arrow;
    }

    public ArrowRotation getRotation() {
        return rotation;
    }

    public void setRotation(ArrowRotation rotation) {
        this.rotation = rotation;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getTrackerID() {
        return trackerID;
    }

    public void setTrackerID(int trackerID) {
        this.trackerID = trackerID;
    }

    public Map<String, Map<LocationObject, Integer>> getLocationObjects() {
        return locationObjects;
    }

    public void setLocationObjects(Map<String, Map<LocationObject, Integer>> locationObjects) {
        this.locationObjects = locationObjects;
    }

    public Map<Kind, List<LocationObject>> getLocationObjectMap() {
        return locationObjectMap;
    }

    public void setLocationObjectMap(Map<Kind, List<LocationObject>> locationObjectMap) {
        this.locationObjectMap = locationObjectMap;
    }

    private void initLocationObjectMap() {
        locationObjects = new HashMap<String, Map<LocationObject, Integer>>();
        locationObjectMap = new HashMap<Kind, List<LocationObject>>();
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
        String handSide = getHandside(lObject);
        return "The destination is located on the " + handSide + " side " + position + getNumberCall(position) + " door.";
    }

    private String getNumberCall(int number){
        if(number == 1){
            return "st ";
        }else if(number == 2){
            return "nd ";
        }else if(number == 3){
            return "rd ";
        }else return "th";
    }

    public void setTracker(Tracker startTracker, Tracker endTracker, int distance, ArrowRotation rotation) {
        if(endTracker != null){
            this.trackerID = endTracker.getId();
            this.distance = distance;

            Direction endToStartDirection = new Direction(startTracker, distance, rotation);

            for (LocationObject lObject : locationObjects.get(LEFTHAND).keySet()) {
                endToStartDirection.addLocationObject(lObject, distance - locationObjects.get(LEFTHAND).get(lObject),
                        RIGHTHAND);
            }
            for (LocationObject lObject : locationObjects.get(RIGHTHAND).keySet())
                endToStartDirection.addLocationObject(lObject, distance - locationObjects.get(RIGHTHAND).get(lObject),
                        LEFTHAND);

            endTracker.addDirection(endToStartDirection);
        }
    }

    public boolean hasTracker() {
        return trackerID > 0;
    }

    public void addLocationObjectToLeft(LocationObject lObject, int distance) {
        addLocationObject(lObject, distance, LEFTHAND);
    }

    public void addLocationObjectToRight(LocationObject lObject, int distance) {
        addLocationObject(lObject, distance, RIGHTHAND);
    }

    private void addLocationObject(final LocationObject lObject, int distance, String handside) {
        locationObjects.get(handside).put(lObject, distance);
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

    // TODO delete
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

