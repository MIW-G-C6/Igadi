package nl.miwgroningen.se6.gardengnomes.Igadi.service;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenTaskDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.testing.unittesting.helpers.AuthorizationHelper;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.GardenTask;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.GardenTaskRepository;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.Converter.GardenTaskConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

@Service
public class GardenTaskService {

    private final GardenTaskRepository gardenTaskRepository;
    private final GardenService gardenService;
    private final AuthorizationHelper authorizationHelper;
    private final GardenTaskConverter gardenTaskConverter;

    public GardenTaskService(GardenTaskRepository gardenTaskRepository, GardenService gardenService,
                             AuthorizationHelper authorizationHelper, GardenTaskConverter gardenTaskConverter) {
        this.gardenTaskRepository = gardenTaskRepository;
        this.gardenService = gardenService;
        this.authorizationHelper = authorizationHelper;
        this.gardenTaskConverter= gardenTaskConverter;
    }

    public List<GardenTaskDTO> getAllGardenTasks() {
        return gardenTaskRepository.findAll().stream().map(gardenTaskConverter::convertToGardenTaskDTO).collect(Collectors.toList());
    }

    public List<GardenTaskDTO> getAllTasksByGardenId(int gardenId) {
        List<GardenTask> tasks = gardenTaskRepository.findAllBygarden_gardenId(gardenId);
        return tasks.stream().map(gardenTaskConverter::convertToGardenTaskDTO).collect(Collectors.toList());
    }

    public void saveGardenTask(GardenTaskDTO gardenTaskDTO) {
        gardenTaskRepository.save(gardenTaskConverter.convertFromGardenTaskDTO(gardenTaskDTO));
    }


    public void userSaveGardenTask(GardenTaskDTO gardenTaskDTO, int userId, int gardenId) {
        if (authorizationHelper.isUserGardenManager(userId, gardenId)) {
            saveGardenTask(gardenTaskDTO);
        } else {
            throw new SecurityException("You are not allowed to save this garden task.");
        }
    }
    public void userDeleteGardenTask(int userId, GardenTask gardenTask) {
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