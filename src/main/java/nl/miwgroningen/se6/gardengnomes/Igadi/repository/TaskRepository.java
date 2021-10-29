package nl.miwgroningen.se6.gardengnomes.Igadi.repository;

import nl.miwgroningen.se6.gardengnomes.Igadi.model.Task;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.GardenTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Modifying
    @Query(value = "DELETE FROM task WHERE taskId NOT IN (SELECT task_taskId FROM gardenTask " +
            "UNION SELECT task_taskId FROM patchTask)",
            nativeQuery = true)
    void deleteUnreferencedEntries();
}
