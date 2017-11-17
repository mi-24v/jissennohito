package jissen.e.jissennohito;

/**
 * Model class(singleton)
 */

enum RotationData {
    INSTANCE;

    public float[] orientations;

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

    private float[] acceleration;
    private float[] geomagnetic;

    public static RotationData getInstance() {
        return INSTANCE;
    }

    RotationData() {
        this.orientations = new float[3];
    }

}
