package leikrad.dev.homework1.data.city;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString @EqualsAndHashCode
public class CityDTO {
    private Long cityId;
    private String cityName;

    public static CityDTO fromCityEntity(City city) {
        return new CityDTO(city.getCityId(), city.getCityName());
    }

    public City toCityEntity() {
        return new City(cityId, cityName);
    }
}
