package nl.miwgroningen.se6.gardengnomes.Igadi.repository;

import nl.miwgroningen.se6.gardengnomes.Igadi.model.JoinGardenRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.ArrayList;

public interface JoinGardenRequestRepository  extends JpaRepository<JoinGardenRequest, Integer> {

    ArrayList<JoinGardenRequest> findAllByuser_userId(int userId);

    ArrayList<JoinGardenRequest> findAllBygarden_gardenId(int gardenId);
}
