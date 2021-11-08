package nl.miwgroningen.se6.gardengnomes.Igadi.repository;

import nl.miwgroningen.se6.gardengnomes.Igadi.model.Patch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Optional;

public interface PatchRepository extends JpaRepository<Patch, Integer> {

    ArrayList<Patch> findAllBygarden_gardenId(int gardenId);

    @Query(value = "SELECT garden_gardenId FROM patch WHERE patchId = :patchId",
            nativeQuery = true)
    Optional<Integer> findGardenIdByPatchId(@Param("patchId") int patchId);
}
