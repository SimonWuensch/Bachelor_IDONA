package com.src.sim.metaioapplication.logic.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.owlike.genson.Genson;
import com.owlike.genson.annotation.JsonIgnore;
import com.src.sim.metaioapplication.logic.resource.LocationObject.Kind;
import com.src.sim.metaioapplication.logic.resource.LocationObject.ToiletKind;
import com.src.sim.metaioapplication.logic.resource.Direction.ArrowRotation;

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

    public void initTrackersSHL() throws Exception{

        //H - House
        addFloor(25, 38, 27);
        addFloor(25, 39, 40);

        //I - House
        addFloor(25, 37, 32, 31, 26);
        addFloor(25, 36, 33, 30, 27);
        addFloor(25, 35, 34, 29, 28);


        //Floor - 0
        addDirectionToTracker(1, ArrowRotation.LEFT,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //LEFT - LocationObject, Distance
                    {
                        put(initLocationObjectMultiple(Kind.STAIRWELL), 7);
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //RIGHT - LocationObject, Distance
                    {
                    }
                });

        addDirectionToTracker(1, 2, 30, ArrowRotation.RIGHT, ArrowRotation.RIGHT,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //LEFT - LocationObject, Distance
                    {
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //RIGHT - LocationObject, Distance
                    {
                        put(initLocationObjectToilet(ToiletKind.FEMAIL), 5);
                        put(initLocationObjectToilet(ToiletKind.HANDICAPPED), 10);
                        put(initLocationObjectToilet(ToiletKind.MALE), 15);
                        put(initLocationObjectRoom("H.1.1 - Technic Room"), 20);
                        put(initLocationObjectRoom("H.1.1"), 28);
                    }
                });

        addDirectionToTracker(2, 26, 30, ArrowRotation.LEFT_UP, ArrowRotation.RIGHT_DOWN,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //LEFT - LocationObject, Distance
                    {
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //RIGHT - LocationObject, Distance
                    {
                    }
                });

        addDirectionToTracker(1, 38, 8, ArrowRotation.LEFT, ArrowRotation.LEFT,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //LEFT - LocationObject, Distance
                    {
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //RIGHT - LocationObject, Distance
                    {
                    }
                });


        addDirectionToTracker(2, 3, 30, ArrowRotation.LEFT, ArrowRotation.BACKWARDS,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //LEFT - LocationObject, Distance
                    {
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //RIGHT - LocationObject, Distance
                    {
                    }
                });

        addDirectionToTracker(3, ArrowRotation.RIGHT, //
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //LEFT - LocationObject, Distance
                    {
                        put(initLocationObjectRoom("H.0.5"), 2);
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //RIGHT - LocationObject, Distance
                    {
                    }
                });

        addDirectionToTracker(3, 4, 45, ArrowRotation.LEFT, ArrowRotation.RIGHT,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //LEFT - LocationObject, Distance
                    {
                        put(initLocationObjectRoom("H.0.1"), 14);
                        put(initLocationObjectRoom("H.0.45"), 30);
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //RIGHT - LocationObject, Distance
                    {
                        put(initLocationObjectRoom("H.0.3 - Right ?"), 2);
                        put(initLocationObjectRoom("H.0.3"), 10);
                        put(initLocationObjectRoom("H.0.3 - Left ?"), 14);
                        put(initLocationObjectRoom("H.0.2"), 16);
                        put(initLocationObjectRoom("I.0.44"), 43);
                    }
                });

        addDirectionToTracker(4, 5, 15, ArrowRotation.BACKWARDS, ArrowRotation.LEFT,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //LEFT - LocationObject, Distance
                    {
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //RIGHT - LocationObject, Distance
                    {
                    }
                });

        addDirectionToTracker(4, 37, 10, ArrowRotation.FORWARDS, ArrowRotation.RIGHT,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //LEFT - LocationObject, Distance
                    {
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //RIGHT - LocationObject, Distance
                    {
                    }
                });

        addDirectionToTracker(4, 6, 47, ArrowRotation.LEFT, ArrowRotation.RIGHT,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //LEFT - LocationObject, Distance
                    {
                        put(initLocationObjectRoom("I.0.1"), 20);
                        put(initLocationObjectRoom("I.0.2"), 29);
                        put(initLocationObjectRoom("I.0.3"), 40);
                        put(initLocationObjectRoom("I.0.4"), 43);
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //RIGHT - LocationObject, Distance
                    {
                        put(initLocationObjectMultiple(Kind.STAIRWELL), 2);
                        put(initLocationObjectMultiple(Kind.EVELATOR), 5);
                        put(initLocationObjectRoom(Kind.STAIRWELL + " Technic Room"), 9);
                        put(initLocationObjectRoom("I.0.40"), 15);
                        put(initLocationObjectRoom("I.0.39"), 22);
                        put(initLocationObjectRoom("I.0.38"), 27);
                        put(initLocationObjectRoom("I.0.37"), 39);
                    }
                });


        addDirectionToTracker(6, 8, 50, ArrowRotation.LEFT, ArrowRotation.BACKWARDS,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //LEFT - LocationObject, Distance
                    {
                        put(initLocationObjectRoom("I.0.36"), 5);
                        put(initLocationObjectRoom("I.0.35"), 10);
                        put(initLocationObjectRoom("I.0.34"), 16);
                        put(initLocationObjectRoom("I.0.33"), 23);
                        put(initLocationObjectToilet(ToiletKind.MALE), 30);
                        put(initLocationObjectRoom("E0 - TM - Left 1 ?"), 41);
                        put(initLocationObjectRoom("E0 - TM - Left 2 ?"), 46);
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //RIGHT - LocationObject, Distance
                    {
                        put(initLocationObjectRoom("I.0.6"), 9);
                        put(initLocationObjectRoom("I.0.7"), 26);
                    }
                });

        addDirectionToTracker(8, 36, 15, ArrowRotation.RIGHT, ArrowRotation.LEFT,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //LEFT - LocationObject, Distance
                    {
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //RIGHT - LocationObject, Distance
                    {
                    }
                });

        addDirectionToTracker(8, ArrowRotation.RIGHT,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //LEFT - LocationObject, Distance
                    {
                        put(initLocationObjectMultiple(Kind.STAIRWELL), 10);
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //RIGHT - LocationObject, Distance
                    {
                    }
                });

        addDirectionToTracker(8, ArrowRotation.FORWARDS,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //LEFT - LocationObject, Distance
                    {
                        put(initLocationObjectRoom("I.0.25"), 5);
                        put(initLocationObjectRoom("I.0.24"), 10);
                        put(initLocationObjectRoom("I.0.23"), 15);
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //RIGHT - LocationObject, Distance
                    {
                        put(initLocationObjectRoom("I.0.27"), 3);
                        put(initLocationObjectRoom("I.0.26"), 10);
                    }
                });

        addDirectionToTracker(8, 11, 25, ArrowRotation.UP, ArrowRotation.DOWN,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //LEFT - LocationObject, Distance
                    {
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //RIGHT - LocationObject, Distance
                    {
                    }
                });

        addDirectionToTracker(8, 9, 50, ArrowRotation.LEFT, ArrowRotation.RIGHT,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //LEFT - LocationObject, Distance
                    {
                        put(initLocationObjectRoom("I.0.8"), 15);
                        put(initLocationObjectRoom("I.0.9"), 20);
                        put(initLocationObjectRoom("I.0.10"), 25);
                        put(initLocationObjectRoom("I.0.11"), 30);
                        put(initLocationObjectRoom("I.0.12"), 35);
                        put(initLocationObjectRoom("I.0.13"), 40);
                        put(initLocationObjectRoom("I.0.14"), 45);
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //RIGHT - LocationObject, Distance
                    {
                        put(initLocationObjectRoom("I.0.22"), 9);
                        put(initLocationObjectRoom("I.0.21"), 15);
                        put(initLocationObjectRoom("I.0.20"), 20);
                        put(initLocationObjectRoom("I.0.18"), 26);
                    }
                });

        addDirectionToTracker(9, ArrowRotation.LEFT,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //LEFT - LocationObject, Distance
                    {
                        put(initLocationObjectRoom("I.0.16"), 12);
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //RIGHT - LocationObject, Distance
                    {
                        put(initLocationObjectRoom("I.0.17"), 15);
                    }
                });

        addDirectionToTracker(9, 35, 8, ArrowRotation.BACKWARDS, ArrowRotation.LEFT,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //LEFT - LocationObject, Distance
                    {
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //RIGHT - LocationObject, Distance
                    {
                    }
                });

        //Florr - 1

        addDirectionToTracker(42, ArrowRotation.BACKWARDS,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //LEFT - LocationObject, Distance
                    {
                        put(initLocationObjectMultiple(Kind.EXIT), 25);
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //RIGHT - LocationObject, Distance
                    {
                    }
                });

        addDirectionToTracker(14, 42, 10, ArrowRotation.RIGHT, ArrowRotation.FORWARDS,//
                new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //LEFT - LocationObject, Distance
                    {
                        put(initLocationObjectRoom(Kind.STAIRWELL + " Technic Room"), 3);
                        put(initLocationObjectMultiple(Kind.EVELATOR), 6);
                    }
                }, new HashMap<LocationObject, Integer>() {
                    private static final long serialVersionUID = 1L;
                    //RIGHT - LocationObject, Distance
                    {
                        put(initLocationObjectRoom("I.1.2"), 1);
                        put(initLocationObjectRoom("I.1.1"), 3);
                    }
                });

        // addDirectionToTracker(14, 32, 15, GeometryRotation., GeometryRotation.,//
        // new HashMap<LocationObject, Integer>() {
        // private static final long serialVersionUID = 1L;
        // //LEFT - LocationObject, Distance
        // {
        // put(initLocationObjectRoom("I.1."), );
        // put(initLocationObjectRoom("I.1."), );
        // put(initLocationObjectRoom("I.1."), );
        // put(initLocationObjectRoom("I.1."), );
        // }
        // }, new HashMap<LocationObject, Integer>() {
        // private static final long serialVersionUID = 1L;
        // //RIGHT - LocationObject, Distance
        // {
        // put(initLocationObjectRoom("I.1."), );
        // put(initLocationObjectRoom("I.1."), );
        // put(initLocationObjectRoom("I.1."), );
        // put(initLocationObjectRoom("I.1."), );
        // }
        // });
        //
        // addDirectionToTracker(, , , GeometryRotation., GeometryRotation.,//
        // new HashMap<LocationObject, Integer>() {
        // private static final long serialVersionUID = 1L;
        // //LEFT - LocationObject, Distance
        // {
        // put(initLocationObjectRoom("I.1."), );
        // put(initLocationObjectRoom("I.1."), );
        // put(initLocationObjectRoom("I.1."), );
        // put(initLocationObjectRoom("I.1."), );
        // }
        // }, new HashMap<LocationObject, Integer>() {
        // private static final long serialVersionUID = 1L;
        // //RIGHT - LocationObject, Distance
        // {
        // put(initLocationObjectRoom("I.1."), );
        // put(initLocationObjectRoom("I.1."), );
        // put(initLocationObjectRoom("I.1."), );
        // put(initLocationObjectRoom("I.1."), );
        // }
        // });
        //
        // addDirectionToTracker(, , , GeometryRotation., GeometryRotation.,//
        // new HashMap<LocationObject, Integer>() {
        // private static final long serialVersionUID = 1L;
        // //LEFT - LocationObject, Distance
        // {
        // put(initLocationObjectRoom("I.1."), );
        // put(initLocationObjectRoom("I.1."), );
        // put(initLocationObjectRoom("I.1."), );
        // put(initLocationObjectRoom("I.1."), );
        // }
        // }, new HashMap<LocationObject, Integer>() {
        // private static final long serialVersionUID = 1L;
        // //RIGHT - LocationObject, Distance
        // {
        // put(initLocationObjectRoom("I.1."), );
        // put(initLocationObjectRoom("I.1."), );
        // put(initLocationObjectRoom("I.1."), );
        // put(initLocationObjectRoom("I.1."), );
        // }
        // });
        //
        // addDirectionToTracker(, , , GeometryRotation., GeometryRotation.,//
        // new HashMap<LocationObject, Integer>() {
        // private static final long serialVersionUID = 1L;
        // //LEFT - LocationObject, Distance
        // {
        // put(initLocationObjectRoom("I.1."), );
        // put(initLocationObjectRoom("I.1."), );
        // put(initLocationObjectRoom("I.1."), );
        // put(initLocationObjectRoom("I.1."), );
        // }
        // }, new HashMap<LocationObject, Integer>() {
        // private static final long serialVersionUID = 1L;
        // //RIGHT - LocationObject, Distance
        // {
        // put(initLocationObjectRoom("I.1."), );
        // put(initLocationObjectRoom("I.1."), );
        // put(initLocationObjectRoom("I.1."), );
        // put(initLocationObjectRoom("I.1."), );
        // }
        // });



    }

    public void initTrackers() throws Exception {
        addDirectionToTracker(1, 2, 6, ArrowRotation.LEFT, ArrowRotation.LEFT,//
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

        addDirectionToTracker(1, 6, 8, ArrowRotation.RIGHT, ArrowRotation.LEFT,//
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

        addDirectionToTracker(1, 9, 4, ArrowRotation.BACKWARDS, ArrowRotation.BACKWARDS,
                new HashMap<LocationObject, Integer>(), new HashMap<LocationObject, Integer>());

        addDirectionToTracker(2, ArrowRotation.BACKWARDS, //
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

        addDirectionToTracker(2, 3, 2, ArrowRotation.RIGHT, ArrowRotation.RIGHT, new HashMap<LocationObject, Integer>(),
                new HashMap<LocationObject, Integer>());

        addDirectionToTracker(3, 5, 8, ArrowRotation.LEFT, ArrowRotation.RIGHT,//
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

        addDirectionToTracker(3, 10, 5, ArrowRotation.BACKWARDS, ArrowRotation.BACKWARDS,//
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

        addDirectionToTracker(3, 11, 4, ArrowRotation.BACKWARDS, ArrowRotation.FORWARDS,//
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

        addDirectionToTracker(4, 9, 2, ArrowRotation.RIGHT, ArrowRotation.RIGHT, new HashMap<LocationObject, Integer>(),
                new HashMap<LocationObject, Integer>());

        addDirectionToTracker(4, 10, 5, ArrowRotation.LEFT, ArrowRotation.LEFT,//
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

        addDirectionToTracker(4, 7, 5, ArrowRotation.BACKWARDS, ArrowRotation.BACKWARDS,//
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

        addDirectionToTracker(5, 11, 3, ArrowRotation.BACKWARDS, ArrowRotation.LEFT,//
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

        addDirectionToTracker(5, 8, 9, ArrowRotation.LEFT, ArrowRotation.RIGHT,//
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

        addDirectionToTracker(6, 9, 4, ArrowRotation.BACKWARDS, ArrowRotation.LEFT,//
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

        addDirectionToTracker(6, 7, 11, ArrowRotation.RIGHT, ArrowRotation.LEFT, new HashMap<LocationObject, Integer>() {
            private static final long serialVersionUID = 1L;
            {
                put(initLocationObjectMultiple(Kind.EMERGENCYEXIT), 9);
            }
        }, new HashMap<LocationObject, Integer>() {
            private static final long serialVersionUID = 1L;

            {
            }
        });

        addDirectionToTracker(8, 7, 6, ArrowRotation.LEFT, ArrowRotation.RIGHT,//
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

        addDirectionToTracker(8, 10, 5, ArrowRotation.BACKWARDS, ArrowRotation.FORWARDS,//
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

        addDirectionToTracker(11, 8, 6, ArrowRotation.BACKWARDS, ArrowRotation.BACKWARDS,//
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

        addDirectionToTracker(11, 10, 2, ArrowRotation.RIGHT, ArrowRotation.RIGHT, new HashMap<LocationObject, Integer>(),
                new HashMap<LocationObject, Integer>());
    }

    private Direction addDirectionToTracker(int startTrackerID, ArrowRotation startToEnd,
                                            Map<LocationObject, Integer> leftObjectMap, Map<LocationObject, Integer> rightObjectMap) {
        return addDirectionToTracker(startTrackerID, -1, 0, startToEnd, ArrowRotation.RIGHT, leftObjectMap, rightObjectMap);
    }

    private Direction addFloor(int startTrackerID, int endTrackerID, int distance) {
        return addDirectionToTracker(startTrackerID, endTrackerID, distance, ArrowRotation.UP, ArrowRotation.DOWN,//
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
    }

    private void addFloor(int distance, int... trackerIDs) {
        if (trackerIDs.length <= 1) {
            return;
        }

        int first = trackerIDs[0];
        for (int i = 1; i < trackerIDs.length; i++) {
            addFloor(first, trackerIDs[i], distance);
            first = i;
        }
    }

    private Direction addDirectionToTracker(int startTrackerID, int endTrackerID, int distance, ArrowRotation startToEnd,
                                            ArrowRotation endToStart, Map<LocationObject, Integer> leftObjectMap, Map<LocationObject, Integer> rightObjectMap) {

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
            return LocationObject.newMultipleLocationObject(kind);
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
