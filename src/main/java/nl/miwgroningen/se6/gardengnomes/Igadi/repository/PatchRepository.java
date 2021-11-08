package nl.miwgroningen.se6.gardengnomes.Igadi.repository;

import nl.miwgroningen.se6.gardengnomes.Igadi.model.Patch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface PatchRepository extends JpaRepository<Patch, Integer> {

    ArrayList<Patch> findAllBygarden_gardenId(int gardenId);

    Optional<Integer> findgarden_gardenIdById(int patchId);
}
