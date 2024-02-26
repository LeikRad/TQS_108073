/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geocoding;

import java.util.Objects;

/**
 * @author ico
 */
public class Address {

    private String road;
    private String city;
    private String zipCode;
    private String houseNumber;

    public Address(String road, String city, String zipCode, String houseNumber) {
        this.road = road;
        this.city = city;
        this.zipCode = zipCode;
        this.houseNumber = houseNumber;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZio() {
        return zipCode;
    }

    public void setZio(String zio) {
        this.zipCode = zio;
    }

    /**
     * Get the value of houseNumber
     *
     * @return the value of houseNumber
     */
    public String getHouseNumber() {
        return houseNumber;
    }

    /**
     * Set the value of houseNumber
     *
     * @param houseNumber new value of houseNumber
     */
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    @Override
    public String toString() {
        return "Address{" + "road=" + road + ", city=" + city + ", zip=" + zipCode + ", houseNumber=" + houseNumber + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (!road.equals(address.road)) return false;
        if (!Objects.equals(city, address.city)) return false;
        if (!Objects.equals(zipCode, address.zipCode)) return false;
        return Objects.equals(houseNumber, address.houseNumber);
    }

    @Override
    public int hashCode() {
        int result = road.hashCode();
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0);
        result = 31 * result + (houseNumber != null ? houseNumber.hashCode() : 0);
        return result;
    }
}
