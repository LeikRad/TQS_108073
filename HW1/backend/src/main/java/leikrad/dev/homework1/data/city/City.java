package leikrad.dev.homework1.data.city;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "cities")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @RequiredArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cityId;

    @NonNull
    @Column(name = "city_name")
    private String cityName;
}
