package jissen.e.jissennohito;

import com.google.gson.annotations.Expose;

/**
 * Api request method
 */

public class RequestRotationData {

    @Expose
    RotationData rotationData;

    public void setRotationData(RotationData rotationData) {
        this.rotationData = rotationData;
    }

}
