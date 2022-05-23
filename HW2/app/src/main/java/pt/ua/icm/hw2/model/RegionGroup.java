package pt.ua.icm.hw2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegionGroup {

    @Expose
    @SerializedName("owner")
    private String owner;

    @Expose
    @SerializedName("country")
    private String country;

    @Expose
    @SerializedName("data")
    private List<Region> regions;

    public RegionGroup(String owner, String country, List<Region> regions) {
        this.owner = owner;
        this.country = country;
        this.regions = regions;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> cities) {
        this.regions = regions;
    }
}
