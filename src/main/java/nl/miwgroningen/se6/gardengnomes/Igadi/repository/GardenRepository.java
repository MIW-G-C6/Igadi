package nl.miwgroningen.se6.gardengnomes.Igadi.repository;

import nl.miwgroningen.se6.gardengnomes.Igadi.model.Garden;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Patch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;


public interface GardenRepository extends JpaRepository<Garden, Integer> {

//    ArrayList<Garden> findAllBygarden_gardenId(int gardenId);

    void deleteById(int gardenId);
}
