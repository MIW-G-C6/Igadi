package nl.miwgroningen.se6.gardengnomes.Igadi.repository;

import nl.miwgroningen.se6.gardengnomes.Igadi.model.Garden;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.GardenUser;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GardenUserRepository extends JpaRepository<GardenUser, Integer> {

    List<GardenUser> findAllByuser_userId(int userId);

    List<GardenUser> findAllBygarden_gardenId(int gardenId);

    List<GardenUser> findAllByGardenAndUser(Garden garden, User user);

    GardenUser findOneByGardenAndUser(Garden garden, User user);

    List<GardenUser> findAllByGardenAndUserAndRole(Garden garden, User user, String role);
}