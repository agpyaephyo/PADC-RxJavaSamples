package xyz.aungpyaephyo.padc.rxjava.data.vo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aung on 7/13/17.
 */

public class RestaurantVO {

    @SerializedName("title")
    private String title;

    @SerializedName("image")
    private String image;

    @SerializedName("addr-short")
    private String shortAddress;

    @SerializedName("total-reating-count")
    private int totalRatingCount;

    @SerializedName("average-rating-value")
    private double averageRatingValue;

    @SerializedName("is-ad")
    private boolean isAd;

    @SerializedName("is-new")
    private boolean isNew;

    @SerializedName("lead-time-in-min")
    private int leadTimeInMin;

    @SerializedName("tags")
    private List<String> tagList;

    @SerializedName("most-popular")
    private String mostPopular;

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getShortAddress() {
        return shortAddress;
    }

    public int getTotalRatingCount() {
        return totalRatingCount;
    }

    public double getAverageRatingValue() {
        return averageRatingValue;
    }

    public boolean isAd() {
        return isAd;
    }

    public boolean isNew() {
        return isNew;
    }

    public int getLeadTimeInMin() {
        return leadTimeInMin;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public String getMostPopular() {
        return mostPopular;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }
}
