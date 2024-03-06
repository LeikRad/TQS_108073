package leikrad.dev.lab3_2cars.data;

public class CarDTO {
    private Long carID;
    private String maker;
    private String model;

    public static CarDTO fromCarEntity(Car car) {
        return new CarDTO(car.getCarId(), car.getMaker(), car.getModel());
    }

    public Car toCarEntity() {
        return new Car(getMaker(), getModel(), getCarID());
    }

    public CarDTO() {
    }

    public CarDTO(Long carID, String maker, String model) {
        this.carID = carID;
        this.maker = maker;
        this.model = model;
    }

    public Long getCarID() {
        return carID;
    }

    public void setCarID(Long carID) {
        this.carID = carID;
    }

    public String getMaker() {
        return maker;
    }

    public void setMake(String maker) {
        this.maker = maker;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

}
