package jissen.e.jissennohito;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Util class
 */

public class ApiUtils {

    public static Retrofit build(){
        return new Retrofit.Builder()
                .baseUrl("http://miwpayou0808.info:4545/"/*"http://192.168.1.1/"*/)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

}
