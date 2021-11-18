package nl.miwgroningen.se6.gardengnomes.Igadi.repository;

import nl.miwgroningen.se6.gardengnomes.Igadi.testing.unittesting.model.GardenTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface GardenTaskRepository extends JpaRepository<GardenTask, Integer> {

    ArrayList<GardenTask> findAllBygarden_gardenId(int gardenId);
}