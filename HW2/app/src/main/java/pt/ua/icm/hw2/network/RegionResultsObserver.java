package pt.ua.icm.hw2.network;

import java.util.HashMap;

import pt.ua.icm.hw2.model.Region;

public interface RegionResultsObserver {
    public void receiveRegionsList(HashMap<String, Region> regionsCollection);
    public void onFailure(Throwable cause);
}
