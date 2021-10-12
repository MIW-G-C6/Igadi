package nl.miwgroningen.se6.gardengnomes.Igadi.service;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenTaskDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.PatchDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Garden;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.GardenTask;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Patch;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.GardenRepository;
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

    public GardenTaskService(GardenTaskRepository gardenTaskRepository, GardenService gardenService) {
        this.gardenTaskRepository = gardenTaskRepository;
        this.gardenService = gardenService;
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


}
