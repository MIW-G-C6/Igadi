package nl.miwgroningen.se6.gardengnomes.Igadi.repository;

import nl.miwgroningen.se6.gardengnomes.Igadi.testing.unittesting.model.PatchTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface PatchTaskRepository extends JpaRepository<PatchTask, Integer> {

    ArrayList<PatchTask> findAllBypatch_patchId(int patchId);
}