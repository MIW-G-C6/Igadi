package nl.miwgroningen.se6.gardengnomes.Igadi.repository;

import nl.miwgroningen.se6.gardengnomes.Igadi.model.Garden;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.GardenTask;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.JoinGardenRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface JoinGardenRequestRepository  extends JpaRepository<JoinGardenRequest, Integer> {

    ArrayList<JoinGardenRequest> findAllByuser_userId(int userId);

    ArrayList<JoinGardenRequest> findAllBygarden_gardenId(int gardenId);
}
