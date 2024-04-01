package leikrad.dev.homework1.data.city;

public class CityDTO {
    private Long cityId;
    private String cityName;

    public CityDTO() {
    }

    public CityDTO(Long cityId, String cityName) {
        this.cityId = cityId;
        this.cityName = cityName;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public static CityDTO fromCityEntity(City city) {
        return new CityDTO(city.getCityId(), city.getCityName());
    }

    public City toCityEntity() {
        return new City(getCityId(), getCityName());
    }
    
}
