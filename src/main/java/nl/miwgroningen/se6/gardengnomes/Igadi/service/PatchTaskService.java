package nl.miwgroningen.se6.gardengnomes.Igadi.service;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.PatchTaskDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.helpers.AuthorizationHelper;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.PatchTask;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.PatchTaskRepository;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.Converter.PatchTaskConverter;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

@Service
public class PatchTaskService {

    private final PatchTaskRepository patchTaskRepository;
    private final PatchService patchService;
    private final AuthorizationHelper authorizationHelper;
    private final PatchTaskConverter patchTaskConverter;

    public PatchTaskService(PatchTaskRepository patchTaskRepository, PatchService patchService,
                            AuthorizationHelper authorizationHelper, PatchTaskConverter patchTaskConverter) {
        this.patchTaskRepository = patchTaskRepository;
        this.patchService = patchService;
        this.authorizationHelper = authorizationHelper;
        this.patchTaskConverter = patchTaskConverter;
    }

    public void savePatchTask(PatchTaskDTO patchTaskDTO) {
        patchTaskRepository.save(patchTaskConverter.convertFromPatchTaskDTO(patchTaskDTO));
    }

    public void userSavePatchTask(PatchTaskDTO patchTaskDTO, int userId, int gardenId) {
        if (authorizationHelper.isUserGardenManager(userId, gardenId)) {
            savePatchTask(patchTaskDTO);
        } else {
            throw new SecurityException("You are not allowed to save this patch task.");
        }
    }

    public List<PatchTaskDTO> getAllPatchTasks() {
        return patchTaskRepository.findAll().stream().map(patchTaskConverter::convertToPatchTaskDTO)
                .collect(Collectors.toList());
    }

    public List<PatchTaskDTO> getAllTasksByPatchId(int patchId) {
        List<PatchTask> tasks = patchTaskRepository.findAllBypatch_patchId(patchId);
        return tasks.stream().map(patchTaskConverter::convertToPatchTaskDTO).collect(Collectors.toList());
    }

    public void userDeletePatchTask(int userId, PatchTask patchTask) {
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