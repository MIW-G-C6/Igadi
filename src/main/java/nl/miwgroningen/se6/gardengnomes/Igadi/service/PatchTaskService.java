package nl.miwgroningen.se6.gardengnomes.Igadi.service;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.PatchTaskDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.helpers.AuthorizationHelper;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.GardenTask;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.PatchTask;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.PatchTaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

@Service
public class PatchTaskService {

    private PatchTaskRepository patchTaskRepository;
    private PatchService patchService;
    private AuthorizationHelper authorizationHelper;

    public PatchTaskService(PatchTaskRepository patchTaskRepository, PatchService patchService,
                            AuthorizationHelper authorizationHelper) {
        this.patchTaskRepository = patchTaskRepository;
        this.patchService = patchService;
        this.authorizationHelper = authorizationHelper;
    }

    public void savePatchTask(PatchTask patchTask) {
        patchTaskRepository.save(patchTask);
    }

    public List<PatchTaskDTO> getAllPatchTasks() {
        return patchTaskRepository.findAll().stream().map(this::convertToPatchTaskDTO).collect(Collectors.toList());
    }

    public List<PatchTaskDTO> getAllTasksByPatchId(int patchId) {
        List<PatchTask> tasks = patchTaskRepository.findAllBypatch_patchId(patchId);
        return tasks.stream().map(this::convertToPatchTaskDTO).collect(Collectors.toList());
    }

    public PatchTaskDTO convertToPatchTaskDTO(PatchTask patchTask) {
        PatchTaskDTO patchTaskDTO = new PatchTaskDTO();
        patchTaskDTO.setTaskId(patchTask.getTaskId());
        patchTaskDTO.setTaskName(patchTask.getTaskName());
        patchTaskDTO.setTaskDescription(patchTask.getTaskDescription());
        patchTaskDTO.setDone(patchTask.getIsDone());
        patchTaskDTO.setPatchDTO(patchService.convertToPatchDTO(patchTask.getPatch()));
        return patchTaskDTO;
    }

    public void deletePatchTask(int userId, PatchTask patchTask) {
        if (authorizationHelper.isUserGardenManager(userId, patchTask.getPatch().getGarden().getGardenId())) {
            patchTaskRepository.delete(patchTask);
        } else {
            throw new SecurityException("You are not allowed to delete this task.");
        }
    }

    public PatchTask getPatchTaskById(int patchTaskId) {
        return patchTaskRepository.getById(patchTaskId);
    }
}