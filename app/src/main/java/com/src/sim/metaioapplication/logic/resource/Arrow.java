package com.src.sim.metaioapplication.logic.resource;

import com.metaio.sdk.jni.Rotation;

public enum Arrow {
    DEFAULT(Direction.ARROWNORMAL, -10.0f, -10.0f, -10.0f),

    LEFT(Direction.ARROWNORMAL, 0.0f, 0.0f, 3.1599975f),
    RIGHT(Direction.ARROWNORMAL, 0.0f, 0.0f ,0.0f),
    UP(Direction.ARROWNORMAL, 0.0f, 0.0f, 1.5999988f),
    DOWN(Direction.ARROWNORMAL, 0.0f, 0.0f ,-1.5999988f),

    FORWARDS(Direction.ARROWNORMAL, 0.0f, 1.459999f, 1.5999988f),
    BACKWARDS(Direction.ARROWNORMAL, 0.0f, -1.7599987f, 7.860085f),

    RIGHT_UP(Direction.ARROWNORMAL, 0.0f, 0.0f ,0.51999986f),
    LEFT_UP(Direction.ARROWNORMAL, 0.0f, 0.0f ,2.639998f),
    RIGHT_DOWN(Direction.ARROWNORMAL, 0.0f, 0.0f ,-0.43999988f),
    LEFT_DOWN(Direction.ARROWNORMAL, 0.0f, 0.0f ,-2.719998f),

    RIGHT_FORWARDS(Direction.ARROWCURVE, 0.0f, 1.1299993f, 1.6099988f),
    RIGHT_BACKWARDS(Direction.ARROWCURVE, 0.0f, -1.5299989f, 7.8400846f),
    LEFT_FORWARDS(Direction.ARROWCURVE, 0.0f, 1.8199986f, -1.5799987f),
    LEFT_BACKWARDS(Direction.ARROWCURVE, 0.0f, -1.3799989f, -1.5799987f),

    FORWARDS_RIGHT(Direction.ARROWCURVE, 1.8199986f, 0.03f, 6.290049f),
    BACKWARDS_RIGHT(Direction.ARROWCURVE, 2.0199986f, 3.1099975f, 9.43012f),
    FORWARDS_LEFT(Direction.ARROWCURVE, 1.1999992f, 0.0f, 3.1499975f),
    BACKWARDS_LEFT(Direction.ARROWCURVE, 1.0699993f, 3.1099975f, 6.290049f);

    private String arrow;
    private float[] rotation = new float[3];

    private Arrow(String arrow, float x, float y, float z){
        this.arrow = arrow;
        this.rotation = new float[] {x, y, z};
    }

    public float[] getRotation() {
        return rotation;
    }

    public void setRotation(float[] rotation) {
        this.rotation = rotation;
    }

    public String getArrow() {
        return arrow;
    }

    public void setArrow(String arrow) {
        this.arrow = arrow;
    }

    public Rotation getGeometryRotation(){
        return new Rotation(rotation[0], rotation[1], rotation[2]);
    }
}
