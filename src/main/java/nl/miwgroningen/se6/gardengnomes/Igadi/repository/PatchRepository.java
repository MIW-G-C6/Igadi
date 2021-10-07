package nl.miwgroningen.se6.gardengnomes.Igadi.repository;

import nl.miwgroningen.se6.gardengnomes.Igadi.model.Patch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatchRepository extends JpaRepository<Patch, Integer> {
}
