package leikrad.dev.homework1.data.city;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.lang.NonNull;

@Repository
public interface CityRepository extends JpaRepository<City, Long>{

    public Optional<City> findByCityId(Long cityId);

    @NonNull
    public List<City> findAll();

    public void deleteByCityId(Long cityID);
}
