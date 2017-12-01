package jissen.e.jissennohito;

import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * REST Api
 */

public interface ApiService {

    @POST("rotation")
    io.reactivex.Observable<Void> postRotationData(@Body RequestRotationData rotationData);

}
