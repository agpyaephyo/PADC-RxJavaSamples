package xyz.aungpyaephyo.padc.rxjava.network.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import xyz.aungpyaephyo.padc.rxjava.data.vo.RestaurantVO;

/**
 * Created by aung on 7/13/17.
 */

public class RestaurantListResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("timestamp")
    private String timestamp;

    @SerializedName("restaurants")
    private List<RestaurantVO> restaurantList;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public List<RestaurantVO> getRestaurantList() {
        return restaurantList;
    }
}
