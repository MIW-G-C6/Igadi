package nl.miwgroningen.se6.gardengnomes.Igadi.repository;

import nl.miwgroningen.se6.gardengnomes.Igadi.model.Garden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GardenRepository extends JpaRepository<Garden, Integer>{
}