package nhanquach.com.homework06;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by mac on 2/17/17.
 */

public class Location {
    String name, address, website;
    LatLng Coor;

    public Location(){

    }

    public Location(String name, String address, String website, LatLng coor) {
        this.name = name;
        this.address = address;
        this.website = website;
        Coor = coor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public LatLng getCoor() {
        return Coor;
    }

    public void setCoor(LatLng coor) {
        Coor = coor;
    }
}
