package nl.miwgroningen.se6.gardengnomes.Igadi.repository;

import nl.miwgroningen.se6.gardengnomes.Igadi.testing.unittesting.model.Garden;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GardenRepository extends JpaRepository<Garden, Integer> {

    void deleteById(int gardenId);
}
