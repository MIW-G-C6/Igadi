package nl.miwgroningen.se6.gardengnomes.Igadi.repository;

import nl.miwgroningen.se6.gardengnomes.Igadi.testing.unittesting.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Modifying
    @Query(value = "DELETE FROM Task WHERE taskId NOT IN (SELECT task_taskId FROM GardenTask " +
            "UNION SELECT task_taskId FROM PatchTask)",
            nativeQuery = true)
    void deleteUnreferencedEntries();
}
