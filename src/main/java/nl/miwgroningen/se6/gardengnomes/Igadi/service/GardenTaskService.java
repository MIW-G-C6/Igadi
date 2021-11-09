package nl.miwgroningen.se6.gardengnomes.Igadi.service;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenTaskDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.helpers.AuthorizationHelper;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Garden;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.GardenTask;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.PatchTask;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.GardenTaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

@Service
public class GardenTaskService {

    private GardenTaskRepository gardenTaskRepository;
    private GardenService gardenService;
    private AuthorizationHelper authorizationHelper;

    public GardenTaskService(GardenTaskRepository gardenTaskRepository, GardenService gardenService,
                             AuthorizationHelper authorizationHelper) {
        this.gardenTaskRepository = gardenTaskRepository;
        this.gardenService = gardenService;
        this.authorizationHelper = authorizationHelper;
    }

    public List<GardenTaskDTO> getAllGardenTasks() {
        return gardenTaskRepository.findAll().stream().map(this::convertToGardenTaskDTO).collect(Collectors.toList());
    }

    public List<GardenTaskDTO> getAllTasksByGardenId(int gardenId) {
        List<GardenTask> tasks = gardenTaskRepository.findAllBygarden_gardenId(gardenId);
        return tasks.stream().map(this::convertToGardenTaskDTO).collect(Collectors.toList());
    }

    public GardenTaskDTO convertToGardenTaskDTO(GardenTask gardenTask) {
        GardenTaskDTO gardenTaskDTO = new GardenTaskDTO();
        gardenTaskDTO.setTaskId(gardenTask.getTaskId());
        gardenTaskDTO.setTaskName(gardenTask.getTaskName());
        gardenTaskDTO.setTaskDescription(gardenTask.getTaskDescription());
        gardenTaskDTO.setDone(gardenTask.getIsDone());
        gardenTaskDTO.setGardenDTO(gardenService.convertToGardenDTO(gardenTask.getGarden()));
        return gardenTaskDTO;
    }

    public GardenTask convertFromGardenTaskDTO(GardenTaskDTO gardenTaskDTO) {
        GardenTask gardenTask = new GardenTask();
        gardenTask.setTaskId(gardenTaskDTO.getTaskId());
        gardenTask.setTaskName(gardenTaskDTO.getTaskName());
        gardenTask.setTaskDescription(gardenTaskDTO.getTaskDescription());
        gardenTask.setDone(gardenTaskDTO.isDone());
        gardenTask.setGarden(gardenService.getGardenById(gardenTaskDTO.getGardenDTO().getGardenId()));
        return gardenTask;
    }

    public void saveGardenTask(GardenTask gardenTask) {
        gardenTaskRepository.save(gardenTask);
    }

    public void deleteGardenTask(int userId, GardenTask gardenTask) {
        if (authorizationHelper.isUserGardenManager(userId, gardenTask.getGarden().getGardenId())) {
            gardenTaskRepository.delete(gardenTask);
        } else {
            throw new SecurityException("You are not allowed to delete this task.");
        }
    }

    public GardenTask getGardenTaskById(int gardenTaskId) {
        return gardenTaskRepository.getById(gardenTaskId);
    }
}