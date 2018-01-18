package com.kelevnor.noteworthplaces.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kelevnor on 1/18/18.
 */

public class UserPreferences {


    @SerializedName("hot_and_new")
    @Expose
    private Boolean hotAndNew ;
    @SerializedName("open_now")
    @Expose
    private Boolean openNow;
    @SerializedName("radius")
    @Expose
    private Double pickedRadius;
    @SerializedName("sort_by")
    @Expose
    private String sortBy;

    public Boolean getHotAndNew() {
        return hotAndNew;
    }

    public void setHotAndNew(Boolean hotAndNew) {
        this.hotAndNew = hotAndNew;
    }

    public Boolean getOpenNow() {
        return openNow;
    }

    public void setOpenNow(Boolean openNow) {
        this.openNow = openNow;
    }

    public Double getPickedRadius() {
        return pickedRadius;
    }

    public void setPickedRadius(Double pickedRadius) {
        this.pickedRadius = pickedRadius;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
