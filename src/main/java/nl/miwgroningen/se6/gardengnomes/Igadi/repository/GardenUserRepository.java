package nl.miwgroningen.se6.gardengnomes.Igadi.repository;

import nl.miwgroningen.se6.gardengnomes.Igadi.model.GardenUser;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Patch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface GardenUserRepository extends JpaRepository<GardenUser, Integer> {

    ArrayList<GardenUser> findAllByuser_userId(int userId);

    ArrayList<GardenUser> findAllByuser_userIdAndgarden_gardenId(int userId, int gardenId);
}
