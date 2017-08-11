package xyz.aungpyaephyo.padc.rxjava.network;

import io.reactivex.Observable;
import retrofit2.http.GET;
import xyz.aungpyaephyo.padc.rxjava.network.responses.RestaurantListResponse;
import xyz.aungpyaephyo.padc.rxjava.utils.RxJavaSamplesConstants;

/**
 * Created by aung on 7/13/17.
 */

public interface RestaurantsApi {

    @GET(RxJavaSamplesConstants.API_GET_RESTAURANTS_V2)
    Observable<RestaurantListResponse> getRestaurantList();

}
