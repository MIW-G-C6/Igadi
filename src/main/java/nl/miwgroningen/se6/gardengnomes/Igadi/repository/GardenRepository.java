package nl.miwgroningen.se6.gardengnomes.Igadi.repository;

import nl.miwgroningen.se6.gardengnomes.Igadi.model.Garden;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Patch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;


public interface GardenRepository extends JpaRepository<Garden, Integer> {

    void deleteById(int gardenId);

    @Query(value = "SELECT * FROM Garden g LEFT JOIN Gardenuser gu ON g.gardenId = gu.garden_gardenId " +
            "WHERE gu.user_userId = :userId",
            nativeQuery = true)
    List<Garden> findAllByUserId(@Param("userId") int userId);
}
