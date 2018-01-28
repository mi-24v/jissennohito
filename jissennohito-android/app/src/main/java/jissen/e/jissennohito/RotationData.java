package jissen.e.jissennohito;

import com.google.gson.annotations.Expose;

/**
 * Model class
 */

class RotationData {

    @Expose public float[] orientations;
    @Expose(serialize = false, deserialize = false) private float[] acceleration;
    @Expose(serialize = false, deserialize = false) private float[] geomagnetic;
    @Expose private float rotation;
    private float offset;

    public float[] getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float[] acceleration) {
        this.acceleration = acceleration;
    }

    public float[] getGeomagnetic() {
        return geomagnetic;
    }

    public void setGeomagnetic(float[] geomagnetic) {
        this.geomagnetic = geomagnetic;
    }


    public float[] getOrientations() {
        return orientations;
    }

    public void setOrientations(float[] orientations) {
        this.orientations = orientations;
    }

    RotationData() {
        this.offset = 0;
        this.orientations = new float[3];
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation){
        if(rotation < 0){
            this.rotation = rotation + 360 - this.offset;//FIXME 計算式あってる???
        }else{
            this.rotation = rotation - this.offset;//FIXME 同上
        }
    }

    public void setRotationWithOffset(float rotation) {
        offset = rotation;
        this.rotation = rotation - offset;
    }
}
