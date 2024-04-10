package leikrad.dev.lab7_2integrationcars.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    public Optional<Car> findByCarId(Long carId);

    public List<Car> findAll();
}