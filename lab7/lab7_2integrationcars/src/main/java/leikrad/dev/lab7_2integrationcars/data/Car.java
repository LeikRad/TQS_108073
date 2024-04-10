package leikrad.dev.lab7_2integrationcars.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long carId;

    @NotNull
    @Size(max = 100)
    private String maker;

    @NotNull
    @Size(max = 100)
    private String model;

    public Car() {
    }

    public Car(String maker, String model) {
        this.maker = maker;
        this.model = model;
    }

    public Car(String maker, String model, Long id) {
        this.maker = maker;
        this.model = model;
        this.carId = id;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long id) {
        this.carId = id;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
